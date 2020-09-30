package com.codetutr.controller.Security;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codetutr.model.Profile;

@RestController
@RequestMapping("/api")
public class ApiController {
	
	
	@PostMapping(value="/user", produces = MediaType.APPLICATION_JSON_VALUE)
	public Profile saveUser(HttpServletResponse response, @RequestBody Profile profile) {
	Profile newProfile =  new Profile();
	newProfile.setFirstName("Yoogesh");
	newProfile.setLastName("Sharma");
	return newProfile;
	}
	
}
