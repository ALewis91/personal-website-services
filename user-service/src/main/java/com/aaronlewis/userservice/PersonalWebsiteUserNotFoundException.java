package com.aaronlewis.userservice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PersonalWebsiteUserNotFoundException extends RuntimeException {

	public PersonalWebsiteUserNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
