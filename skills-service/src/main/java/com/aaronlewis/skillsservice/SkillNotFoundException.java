package com.aaronlewis.skillsservice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.NOT_FOUND)
public class SkillNotFoundException extends RuntimeException {

	public SkillNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
