package com.yoogesh.common.RestHelper.Exception;

public enum ErrorType 
{
	PWD_LOCKED,
	INVALID_ID_PWD,
	STALE_PROFILE,
	PENDING_REGISTRATION_EXPIRED,
	PENDING_REGISTRATION_VALID,
	PROFILE_DISABLED,
	MFA_LOCKED,
	MFA_BLOCKED,
	USERNAME_IN_USE,
	//EMAIL_IN_USE, // FUTURE USE
	REG_INVALID_PRODUCT,
	REG_INVALID_PRODUCTDATA,
	REG_PRODUCT_ALREADYREGISTERED,
	REG_PRODUCT_NOTALLOWEDONPROFILE,
	PROFILE_NOTELIGIBLE_FOR_BILLPAY,
	PROFILE_COULD_NOT_BE_CREATED,
	LAST_LOGIN_DATE_COULD_NOT_UPDATE,
	DUMMY_FORM_COULD_NOT_BE_CREATED,
	PROFILE_NOT_FOUND,
	PROFILE_COULD_NOT_UPDATED,
	OTP_COULD_NOT_BE_SAVED,
	EMAIL_COULD_NOT_SENT,
	RECAPTCHA_NOT_VERIFIED,
	AUDIT_LOG_COULD_NOT_SAVED,
	USERNAME_NOT_ELIGIBLE_TO_LAUNCH_SECURE_MAIL
	// MAX_CHANNELS


}
