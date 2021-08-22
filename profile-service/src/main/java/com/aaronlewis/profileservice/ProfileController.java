package com.aaronlewis.profileservice;

import java.net.URI;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;


@RestController
public class ProfileController {
	
	Logger logger = LoggerFactory.getLogger(ProfileController.class);
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private ProfileRepository repository;

	@GetMapping("/profile-service/user/{userId}")
	@RateLimiter(name="retrieveProfile")
	@Retry(name="retrieveProfile")
	public Profile retrieveProfile(@PathVariable Long userId) {
		
		logger.info("retrieveProfile called with {}", userId);
		
		Profile profile = null;
		Optional<Profile> profileOptional = repository.findByCreatorId(userId);
		if (profileOptional.isPresent()) {
			profile = profileOptional.get();
		} else {
			throw new ProfileNotFoundException("Profile of user with ID: " + userId + " not found.");
		}
		
		String port = environment.getProperty("local.server.port");
		String host = environment.getProperty("HOSTNAME");
		profile.setEnvironment(host + ':' + port);
		return profile;
	}
	
	@PostMapping("/profile-service/user/{userId}")
	public ResponseEntity<Object> saveProfile(@PathVariable Long userId, @RequestBody Profile profile) {
		logger.info("attempted to save profile to user with id {}", userId);
		
		Optional<Profile> optionalProfile = repository.findByCreatorId(userId);
		if (optionalProfile.isPresent()) {
			profile.setId(optionalProfile.get().getId());
		}
		profile.setCreatorId(userId);
		
		Profile savedProfile = repository.save(profile);
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path(savedProfile.getId() + "")
				.buildAndExpand(savedProfile.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/profile-service/user/{userId}")
	public ResponseEntity<Object> deleteProfile(@PathVariable Long userId) {
		logger.info("attempted to save profile to user with id {}", userId);
		
		Optional<Profile> profileToDelete = repository.findByCreatorId(userId);
		
		if (profileToDelete.isPresent()) {
			repository.delete(profileToDelete.get());
			return new ResponseEntity<Object>(HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
		}
	}
}
