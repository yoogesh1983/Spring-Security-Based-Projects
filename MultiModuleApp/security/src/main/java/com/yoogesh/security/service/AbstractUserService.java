package com.yoogesh.security.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import com.yoogesh.common.mail.MailSender;
import com.yoogesh.common.web.LemonProperties;
import com.yoogesh.common.web.LemonProperties.Admin;
import com.yoogesh.common.web.LemonUtils;
import com.yoogesh.common.web.Exception.MultiErrorException;
import com.yoogesh.common.web.security.annotation.Password;
import com.yoogesh.common.web.security.annotation.UserEditPermission;
import com.yoogesh.common.web.security.entity.AbstractUser;
import com.yoogesh.common.web.security.entity.AbstractUserRepository;
import com.yoogesh.common.web.security.entity.AbstractUser.Role;
import com.yoogesh.common.web.security.entity.AbstractUser.SignUpValidation;
import com.yoogesh.common.web.security.form.ChangePasswordForm;

/**
 * The Lemon Service class
 * 
 * @author Sanjay Patel
 *
 * @param <U>	The User class
 * @param <ID>	The Primary key type of User class 
 */
@Validated
@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
public abstract class AbstractUserService
	<U extends AbstractUser<U,ID>, ID extends Serializable> {

    private static final Log log = LogFactory.getLog(AbstractUserService.class);
    
	private LemonProperties properties;
	private PasswordEncoder passwordEncoder;
    private MailSender mailSender;
	private AbstractUserRepository<U, ID> userRepository;
	private UserDetailsService userDetailsService;

	@Autowired
	public void createUserService(LemonProperties properties,
			PasswordEncoder passwordEncoder,
			MailSender mailSender,
			AbstractUserRepository<U, ID> userRepository,
			UserDetailsService userDetailsService) {
		
		this.properties = properties;
		this.passwordEncoder = passwordEncoder;
		this.mailSender = mailSender;
		this.userRepository = userRepository;
		this.userDetailsService = userDetailsService;
		
		log.info("Created");
	}

    
	/**
	 * Creates the initial Admin user, if not found.
	 * Override this method if needed.
	 */
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
    public void onStartup() {
    	
		try {
			
			// Check if the user already exists
			userDetailsService
				.loadUserByUsername(properties.getAdmin().getUsername());
			
		} catch (UsernameNotFoundException e) {
			
			// Doesn't exist. So, create it.
	    	U user = createAdminUser();
	    	userRepository.save(user);			
		}
	}


	/**
	 * Creates the initial Admin user.
	 * Override this if needed.
	 */
    protected U createAdminUser() {
		
    	// fetch data about the user to be created
    	Admin initialAdmin = properties.getAdmin();
    	
    	log.info("Creating the first Dba user: " + initialAdmin.getUsername());

    	// create the user
    	U user = newUser();
		user.setUsername(initialAdmin.getUsername());
		user.setPassword(passwordEncoder.encode(
			properties.getAdmin().getPassword()));
		
		user.getRoles().add(Role.ADMIN);
		user.getRoles().add(Role.DBA);
		
		return user;
	}

    
	/**
	 * Creates a new user object. Must be overridden in the
	 * subclass, like this:
	 * 
	 * <pre>
	 * protected User newUser() {
	 *    return new User();
	 * }
	 * </pre>
	 */
    public abstract U newUser();


	/**
	 * Returns the context data to be sent to the client,
	 * i.e. <code>reCaptchaSiteKey</code> and all the properties
	 * prefixed with <code>lemon.shared</code>.
	 * 
	 * To send custom properties, put those in your application
	 * properties in the format <em>lemon.shared.fooBar</em>.
	 * 
	 * Override this method if needed.
	 */
	public Map<String, Object> getContext() {
		
		// make the context
		Map<String, Object> context = new HashMap<String, Object>(2);
		context.put("reCaptchaSiteKey", properties.getRecaptcha().getSitekey());
		context.put("shared", properties.getShared());
		
		log.debug("Returning context: " + context);

		return context;		
	}
	
	
	/**
	 * Signs up a user.
	 * 
	 * @param user	data fed by the user
	 */
	@PreAuthorize("isAnonymous()")
	@Validated(SignUpValidation.class)
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void signup(@Valid U user) {
		
		log.debug("Signing up user: " + user);

		initUser(user); // sets right all fields of the user
		userRepository.save(user);
		
		// if successfully committed
		LemonUtils.afterCommit(() -> {
		
			LemonUtils.logIn(user); // log the user in
			sendVerificationMail(user); // send verification mail
			log.debug("Signed up user: " + user);
		});
	}
	
	
	/**
	 * Initializes the user based on the input data
	 * 
	 * @param user
	 */
	protected void initUser(U user) {
		
		log.debug("Initializing user: " + user);

		user.setPassword(passwordEncoder.encode(user.getPassword())); // encode the password
		makeUnverified(user); // make the user unverified
	}

	
	/**
	 * Makes a user unverified
	 * @param user
	 */
	protected void makeUnverified(U user) {
		user.getRoles().add(Role.UNVERIFIED);
		user.setVerificationCode(LemonUtils.uid());
	}
	
	
	/***
	 * Makes a user verified
	 * @param user
	 */
	protected void makeVerified(U user) {
		user.getRoles().remove(Role.UNVERIFIED);
		user.setVerificationCode(null);
	}
	
	
	/**
	 * Sends verification mail to a unverified user.
	 * 
	 * @param user
	 */
	protected void sendVerificationMail(final U user) {
		try {
			
			log.debug("Sending verification mail to: " + user);

			// make the link
			String verifyLink = properties.getApplicationUrl()
				+ "/users/" + user.getVerificationCode() + "/verify";
			
			// send the mail
			mailSender.send(user.getEmail(),
				LemonUtils.getMessage("com.naturalprogrammer.spring.verifySubject"),
				LemonUtils.getMessage(
					"com.naturalprogrammer.spring.verifyEmail",	verifyLink));
			
			log.debug("Verification mail to " + user.getEmail() + " queued.");
			
		} catch (MessagingException e) {
			// In case of exception, just log the error and keep silent
			log.error(ExceptionUtils.getStackTrace(e));
		}
	}	

	
	/**
	 * Resends verification mail to the user.
	 * 
	 * @param user
	 */
	@UserEditPermission
	public void resendVerificationMail(U user) {

		// The user must exist
		LemonUtils.check("id", user != null,
				"com.naturalprogrammer.spring.userNotFound").go();
		
		// must be unverified
		LemonUtils.check(user.getRoles().contains(Role.UNVERIFIED),
				"com.naturalprogrammer.spring.alreadyVerified").go();	

		// send the verification mail
		sendVerificationMail(user);
	}

	
	/**
	 * Fetches a user by email
	 * 
	 * @param email
	 * @return the decorated user object
	 */
	public U fetchUserByEmail(@Valid @Email @NotBlank String email) {
		
		log.debug("Fetching user by email: " + email);

		// fetch the user
		U user = userRepository.findByEmail(email)
			.orElseThrow(MultiErrorException.supplier("email",
				"com.naturalprogrammer.spring.userNotFound"));

		// decorate the user, and hide confidential fields
		user.decorate().hideConfidentialFields();
		
		log.debug("Returning user: " + user);		

		return user;
	}

	
	/**
	 * Returns a non-null, decorated user for the client.
	 * 
	 * @param user
	 * @return
	 */
	public U processUser(U user) {
		
		log.debug("Fetching user: " + user);

		// ensure that the user exists
		LemonUtils.check("id", user != null,
			"com.naturalprogrammer.spring.userNotFound").go();
		
		// decorate the user, and hide confidential fields
		user.decorate().hideConfidentialFields();
		
		return user;
	}
	
	
	/**
	 * Verifies the email id of current-user
	 *  
	 * @param verificationCode
	 */
	@PreAuthorize("isAuthenticated()")
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void verifyUser(@Valid @NotBlank String verificationCode) {
		
		log.debug("Verifying user ...");

		// get the current-user from the session
		U currentUser = LemonUtils.getUser();
		
		// fetch a fresh copy from the database
		U user = userRepository.findOne(currentUser.getId());
		
		// ensure that he is unverified
		LemonUtils.check(user.getRoles().contains(Role.UNVERIFIED),
				"com.naturalprogrammer.spring.alreadyVerified").go();	
		
		// ensure that the verification code of the user matches with the given one
		LemonUtils.check(verificationCode.equals(user.getVerificationCode()),
				"com.naturalprogrammer.spring.wrong.verificationCode").go();
		
		makeVerified(user); // make him verified
		userRepository.save(user);
		
		// after successful commit,
		LemonUtils.afterCommit(() -> {
			
			// Re-login the user, so that the UNVERIFIED role is removed
			LemonUtils.logIn(user);
			
			log.debug("Re-logged-in the user for removing UNVERIFIED role.");		
		});
		
		log.debug("Verified user: " + user);		
	}

	
	/**
	 * Forgot password.
	 * 
	 * @param email	the email of the user who forgot his password
	 */
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void forgotPassword(@Valid @Email @NotBlank String email) {
		
		log.debug("Processing forgot password for email: " + email);
		
		// fetch the user record from database
		U user = userRepository.findByEmail(email)
				.orElseThrow(MultiErrorException.supplier(
					"com.naturalprogrammer.spring.userNotFound"));

		// set a forgot password code
		user.setForgotPasswordCode(LemonUtils.uid());
		userRepository.save(user);

		// after successful commit, mail him a link to reset his password
		LemonUtils.afterCommit(() -> mailForgotPasswordLink(user));
	}
	
	
	/**
	 * Forgot password.
	 * 
	 * @param email	the email of the user who forgot his password
	 */
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void forgotPassword(U user) {
		
		log.debug("Processing forgot password for user: " + user);
		
		// set a forgot password code
		user.setForgotPasswordCode(LemonUtils.uid());
		userRepository.save(user);

		// after successful commit, mail him a link to reset his password
		LemonUtils.afterCommit(() -> mailForgotPasswordLink(user));
	}
	
	
	/**
	 * Mails the forgot password link.
	 * 
	 * @param user
	 */
	protected void mailForgotPasswordLink(U user) {
		
		try {

			log.debug("Mailing forgot password link to user: " + user);

			// make the link
			String forgotPasswordLink =	properties.getApplicationUrl()
				    + "/users/" + user.getForgotPasswordCode()
					+ "/reset-password";
			
			// send the mail
			mailSender.send(user.getEmail(),
					LemonUtils.getMessage("com.naturalprogrammer.spring.forgotPasswordSubject"),
					LemonUtils.getMessage("com.naturalprogrammer.spring.forgotPasswordEmail",
						forgotPasswordLink));
			
			log.debug("Forgot password link mail queued.");
			
		} catch (MessagingException e) {
			// In case of exception, just log the error and keep silent			
			log.error(ExceptionUtils.getStackTrace(e));
		}
	}

	
	/**
	 * Resets the password.
	 * 
	 * @param forgotPasswordCode
	 * @param newPassword
	 */
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void resetPassword(@Valid @NotBlank String forgotPasswordCode,
							  @Valid @Password String newPassword) {
		
		log.debug("Resetting password ...");

		// fetch the user
		U user = userRepository
			.findByForgotPasswordCode(forgotPasswordCode)
			.orElseThrow(MultiErrorException.supplier(
				"com.naturalprogrammer.spring.invalidLink"));
		
		// sets the password
		user.setPassword(passwordEncoder.encode(newPassword));
		user.setForgotPasswordCode(null);
		
		userRepository.save(user);
		
		log.debug("Password reset.");	
		
	}

	
	/**
	 * Updates a user with the given data.
	 * 
	 * @param user
	 * @param updatedUser
	 */
	@UserEditPermission
	@Validated(AbstractUser.UpdateValidation.class)
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void updateUser(U user, @Valid U updatedUser) {
		
		log.debug("Updating user: " + user);

		// checks
		LemonUtils.validateVersion(user, updatedUser);

		// delegates to updateUserFields
		updateUserFields(user, updatedUser, LemonUtils.getUser());
		userRepository.save(user);
		
		log.debug("Updated user: " + user);		
	}
	
	
	/**
	 * Changes the password.
	 * 
	 * @param user
	 * @param changePasswordForm
	 */
	@UserEditPermission
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void changePassword(U user, @Valid ChangePasswordForm changePasswordForm) {
		
		log.debug("Changing password for user: " + user);

		// checks
		LemonUtils.check("id", user != null,
			"com.naturalprogrammer.spring.userNotFound").go();
		LemonUtils.check("changePasswordForm.oldPassword",
			passwordEncoder.matches(changePasswordForm.getOldPassword(),
									user.getPassword()),
			"com.naturalprogrammer.spring.wrong.password").go();
		
		// sets the password
		user.setPassword(passwordEncoder.encode(changePasswordForm.getPassword()));
		userRepository.save(user);
		
		// after successful commit
		LemonUtils.afterCommit(() -> {

			U currentUser = LemonUtils.getUser();
			
			if (currentUser.equals(user)) { // if current-user's password changed,
				
				log.debug("Logging out ...");
				LemonUtils.logOut(); // log him out
			}
		});
		
		log.debug("Changed password for user: " + user);
	}


	/**
	 * Updates the fields of the users. Override this if you have more fields.
	 * 
	 * @param user
	 * @param updatedUser
	 * @param currentUser
	 */
	protected void updateUserFields(U user, U updatedUser, U currentUser) {

		log.debug("Updating user fields for user: " + user);

		// User is already decorated while checking the 'edit' permission
		// So, user.isRolesEditable() below would work
		if (user.isRolesEditable()) { 
			
			log.debug("Updating roles for user: " + user);

			// update the roles
			
			Set<String> roles = user.getRoles();
			
			if (updatedUser.isUnverified()) {
				
				if (!user.hasRole(Role.UNVERIFIED)) {

					makeUnverified(user); // make user unverified
					LemonUtils.afterCommit(() -> sendVerificationMail(user)); // send a verification mail to the user
				}
			} else {
				
				if (user.hasRole(Role.UNVERIFIED))
					makeVerified(user); // make user verified
			}
			
			if (updatedUser.isAdmin())
				roles.add(Role.ADMIN);
			else
				roles.remove(Role.ADMIN);
			
			if (updatedUser.isBlocked())
				roles.add(Role.BLOCKED);
			else
				roles.remove(Role.BLOCKED);
		}
	}

	
	/**
	 * Gets the current-user to be sent to a client.
	 * 
	 * @return
	 */
	public U userForClient() {
		
		// delegates
		return userForClient(LemonUtils.getUser());
	}

	
	/**
	 * Gets the current-user to be sent to a client.
	 * Override this if you have more fields.
	 * 
	 * @param currentUser
	 */
	protected U userForClient(U currentUser) {
		
		if (currentUser == null)
			return null;
		
		U user = newUser();
		user.setIdForClient(currentUser.getId());
		user.setUsername(currentUser.getUsername());
		user.setRoles(currentUser.getRoles());
		user.decorate(currentUser);
		
		log.debug("Returning user for client: " + user);
		
		return user;
	}
	

	/**
	 * Requests for email change.
	 * 
	 * @param user
	 * @param updatedUser
	 */
	@UserEditPermission
	@Validated(AbstractUser.ChangeEmailValidation.class)
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void requestEmailChange(U user, @Valid U updatedUser) {
		
		log.debug("Requesting email change: " + user);

		// checks
		LemonUtils.check("id", user != null,
				"com.naturalprogrammer.spring.userNotFound").go();
		LemonUtils.check("updatedUser.password",
			passwordEncoder.matches(updatedUser.getPassword(),
									LemonUtils.getUser().getPassword()),
			"com.naturalprogrammer.spring.wrong.password").go();

		// preserves the new email id
		user.setNewEmail(updatedUser.getNewEmail());
		user.setChangeEmailCode(LemonUtils.uid());
		userRepository.save(user);
		
		// after successful commit, mails a link to the user
		LemonUtils.afterCommit(() -> mailChangeEmailLink(user));
		
		log.debug("Requested email change: " + user);		
	}

	
	/**
	 * Mails the change-email verification link to the user.
	 * 
	 * @param user
	 */
	protected void mailChangeEmailLink(U user) {
		
		try {
			
			log.debug("Mailing change email link to user: " + user);

			// make the link
			String changeEmailLink = properties.getApplicationUrl()
				    + "/users/" + user.getChangeEmailCode()
					+ "/change-email";
			
			// mail it
			mailSender.send(user.getEmail(),
					LemonUtils.getMessage(
					"com.naturalprogrammer.spring.changeEmailSubject"),
					LemonUtils.getMessage(
					"com.naturalprogrammer.spring.changeEmailEmail",
					 changeEmailLink));
			
			log.debug("Change email link mail queued.");
			
		} catch (MessagingException e) {
			// In case of exception, just log the error and keep silent			
			log.error(ExceptionUtils.getStackTrace(e));
		}
	}


	/**
	 * Change the email.
	 * 
	 * @param changeEmailCode
	 */
	@PreAuthorize("isAuthenticated()")
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void changeEmail(@Valid @NotBlank String changeEmailCode) {
		
		log.debug("Changing email of current user ...");

		// fetch the current-user
		U currentUser = LemonUtils.getUser();
		U user = userRepository.findOne(currentUser.getId());
		
		// checks
		
		LemonUtils.check(changeEmailCode.equals(user.getChangeEmailCode()),
				"com.naturalprogrammer.spring.wrong.changeEmailCode").go();
		
		// Ensure that the email would be unique 
		LemonUtils.check(
				!userRepository.findByEmail(user.getNewEmail()).isPresent(),
				"com.naturalprogrammer.spring.duplicate.email").go();	
		
		// update the fields
		user.setEmail(user.getNewEmail());
		user.setNewEmail(null);
		user.setChangeEmailCode(null);
		
		// make the user verified if he is not
		if (user.hasRole(Role.UNVERIFIED))
			makeVerified(user);
		
		userRepository.save(user);
		
		// logout after successful commit
		LemonUtils.afterCommit(LemonUtils::logOut);
		
		log.debug("Changed email of user: " + user);		
	}


	@UserEditPermission
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public Map<String, String> createApiKey(U user) {
		
		log.debug("Creating API Key for user: " + user);

		// checks
		LemonUtils.check("id", user != null,
			"com.naturalprogrammer.spring.userNotFound").go();
		
		// set API Key
		String key = LemonUtils.uid();
		user.setApiKey(passwordEncoder.encode(key));
		userRepository.save(user);

		log.debug("Created token for user: " + user);	
		return LemonUtils.mapOf("apiKey", key);
	}


	@UserEditPermission
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void removeApiKey(U user) {
		
		log.debug("Removing API key for user: " + user);

		// checks
		LemonUtils.check("id", user != null,
			"com.naturalprogrammer.spring.userNotFound").go();
		
		// remove the token
		user.setApiKey(null);
		userRepository.save(user);

		log.debug("Removed API key for user: " + user);	
	}

	abstract public ID parseId(String id);
}
