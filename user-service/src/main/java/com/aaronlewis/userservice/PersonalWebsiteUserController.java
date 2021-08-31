package com.aaronlewis.userservice;

import java.net.URI;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@RestController
public class PersonalWebsiteUserController {
	
	Logger logger = LoggerFactory.getLogger(PersonalWebsiteUserController.class);
	
	@Autowired
	private PersonalWebsiteUserRepository repository;

	@GetMapping("/user-service/login")
	public Login login(
			@RequestHeader(value="email") String email,
			@RequestHeader(value="password") String password
			) {
		
		logger.info("retrieveId called with email {} and password {}", email, password);
		
		Long id = null;
		Optional<PersonalWebsiteUser> optionalUser = repository.findByEmail(email);
		
		if (optionalUser.isPresent()) {
			if (optionalUser.get().getPassword().compareTo(password) == 0) {
				id = optionalUser.get().getId();
			} else {
				throw new PersonalWebsiteUserBadCredentialsException("Password: " + password + " is incorrect.");
			}
		} else {
			throw new PersonalWebsiteUserNotFoundException("Email: " + email + " not found.");
		}
		logger.info("User was authenticated, returning id {}", id);
		return new Login(id);
	}
	
	@GetMapping("/user-service/{userId}")
	public PersonalWebsiteUser retrieveUser( @PathVariable Long userId) {
		
		logger.info("retrieveUser called with userId {}", userId);
		
		Optional<PersonalWebsiteUser> optionalUser = repository.findById(userId);
		if(optionalUser.isEmpty()) {
			throw new PersonalWebsiteUserNotFoundException("id: " + userId);
		}
		
		logger.info("User was found, returning user with id {}", userId);
		return optionalUser.get();
	}
	
	@PostMapping("/user-service/")
	public ResponseEntity<Object> saveUser(@RequestBody PersonalWebsiteUser user) {
		logger.info("attempted to save user with email {}", user.getEmail());
		
		Optional<PersonalWebsiteUser> optionalUser = repository.findByEmail(user.getEmail());
		if (optionalUser.isPresent()) {
			throw new PersonalWebsiteUserAlreadyExistsException("An account exists for email: " + user.getEmail());
		}
		
		PersonalWebsiteUser savedUser = repository.save(user);
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedUser.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	} 
}
