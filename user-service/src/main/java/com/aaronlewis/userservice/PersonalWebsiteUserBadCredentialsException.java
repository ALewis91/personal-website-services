package com.aaronlewis.userservice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PersonalWebsiteUserBadCredentialsException extends RuntimeException {

	public PersonalWebsiteUserBadCredentialsException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
