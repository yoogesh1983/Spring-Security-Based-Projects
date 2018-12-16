package com.yoogesh.common.web.security.form;

/**
 * A form using RetypePassword constraint
 * should implement this interface
 *  
 * @author Sanjay Patel
 */
public interface RetypePasswordForm {

	String getPassword();
	String getRetypePassword();
	
}
