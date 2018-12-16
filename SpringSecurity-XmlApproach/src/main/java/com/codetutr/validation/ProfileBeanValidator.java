package com.codetutr.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.codetutr.model.Profile;
import com.codetutr.validationHelper.ValidationHelper;

@Component
public class ProfileBeanValidator implements Validator
{

	@Override
	public boolean supports(Class<?> formType) 
	{
		boolean supported = false;
		
		if(Profile.class.equals(formType))
		{
			supported = true;
		}
		
		return supported;
	}
	
	

	@Override
	public void validate(Object formBean, Errors errors) 
	{
		// make a validation logic here
		Profile profile = (Profile)formBean;
		
		String username = profile.getUsername();
		if(!ValidationHelper.isValidRequired(username))
		{
	       errors.reject("validation.username.required");
		}
		
		String password = profile.getPassword();
		if(!ValidationHelper.isValidRequired(password))
		{
	       errors.reject("validation.password.required");
		}
		
		String firstName = profile.getFirstName();
		if(!ValidationHelper.isValidRequired(firstName))
		{
	       errors.reject("validation.firstName.required");
		}
		
		String lastName = profile.getLastName();
		if(!ValidationHelper.isValidRequired(lastName))
		{
	       errors.reject("validation.lastName.required");
		}
	}

}
