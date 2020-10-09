package com.codetutr.controller.Security;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codetutr.model.Profile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api")
public class ApiController {
	
	@Operation(summary = "Get thing", tags="User", responses = {
		      @ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
		      @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
		      @ApiResponse(responseCode = "401", description = "Authentication Failure", content = @Content(schema = @Schema(hidden = true))) })
	@PostMapping(value="/user", produces = MediaType.APPLICATION_JSON_VALUE)
	public Profile saveUser(HttpServletResponse response, @RequestBody Profile profile) {
	Profile newProfile =  new Profile();
	newProfile.setFirstName("Yoogesh");
	newProfile.setLastName("Sharma");
	return newProfile;
	}
	
}
