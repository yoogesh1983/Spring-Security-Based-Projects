package com.codetutr.controller.Security;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codetutr.model.Profile;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
public class ApiController {
	
	
	@PostMapping(value="/user", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="Save the user", notes="This url is used to save the user", response=User.class )
	public Profile saveUser(HttpServletResponse response, @RequestBody Profile profile) {
		return new Profile();
	}
	

}
