package com.aaronlewis.experienceservice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ExperienceNotFoundException extends RuntimeException {

	public ExperienceNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}