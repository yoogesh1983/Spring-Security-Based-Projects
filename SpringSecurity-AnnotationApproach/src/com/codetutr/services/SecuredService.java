package com.codetutr.services;

import org.springframework.stereotype.Service;

import com.codetutr.model.Profile;
import com.codetutr.model.annotation.AdminUser;
import com.codetutr.model.annotation.AuthenticatedUser;
import com.codetutr.model.annotation.DbaUser;
import com.codetutr.model.annotation.NormalUser;
import com.codetutr.model.annotation.UserWithEditPermission;

@Service
public class SecuredService {
	
	@DbaUser
	public String dbaOnly() {
		return "dba() method is called Successfully";
	}
	
	@AdminUser
	public String adminOnly() {
		return "admin() method is called Successfully";
	}
	
	@NormalUser
	public String userOnly() {
		return "user() method is called Successfully";
	}
	
	@AuthenticatedUser
	public String authenticatedOnly() {
		return "authenticated() method is called Successfully";
	}
	
	@UserWithEditPermission
	public String userWithEditPermission(Profile profile) {
		return "Congratulation Dear " + profile.getUsername() + "!! You have an Edit permission!";
	}
}
