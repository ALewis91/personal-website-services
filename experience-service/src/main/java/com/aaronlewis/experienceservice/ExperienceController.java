package com.aaronlewis.experienceservice;

import java.net.URI;
import java.sql.Date;
import java.util.Collections;
import java.util.List;
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


@RestController
public class ExperienceController {
	
	Logger logger = LoggerFactory.getLogger(ExperienceController.class);
	
	@Autowired
	ExperienceRepository repository;
	
	@Autowired
	Environment environment;
	
	@GetMapping("/experience-service/user/{creatorId}")
	public Experiences retrieveExperiences(@PathVariable Long creatorId) {
		
		logger.info("retrieveExperiences called for creatorId {}", creatorId);
		
		List<Experience> experienceList = null;
		experienceList = repository.findByCreatorId(creatorId);
		Collections.sort(experienceList, (a, b) -> a.getStartDate().compareTo(b.getStartDate()));
		String port = environment.getProperty("local.server.port");
		String host = environment.getProperty("HOSTNAME");
		Experiences experiences = new Experiences();
		experiences.setExperiences(experienceList);
		experiences.setEnvironment(host + ":" + port);
		
		return experiences;
	}
	
	@GetMapping("/experience-service/user/{creatorId}/experience/{experienceId}")
	public Experience retrieveExperience(
			@PathVariable Long creatorId, 
			@PathVariable Long experienceId) {
		
		logger.info("retrieveExperience called for creatorId {} and experienceId {}", 
				creatorId,
				experienceId);
		
		Optional<Experience> optionalExperience = repository.findById(experienceId);
		
		if (optionalExperience.isEmpty() || optionalExperience.get().getCreatorId() != creatorId) {
			throw new ExperienceNotFoundException(
					"creatorId: " + creatorId + " experienceId: " + experienceId);
		}
		
		return optionalExperience.get();
	}
	
	@PostMapping("/experience-service/user/{creatorId}")
	public ResponseEntity<Object> saveExperience(
			@PathVariable Long creatorId, 
			@RequestBody Experience experience) {
		logger.info("attempted to create exprience of user with id {}", creatorId);
		
		experience.setCreatorId(creatorId);
		experience.setStartDate(formatSqlDate(experience.getStartDate().toString()));
		experience.setEndDate(formatSqlDate(experience.getEndDate().toString()));
		
		Experience savedExperience = repository.save(experience);
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/experience/{id}")
				.buildAndExpand(savedExperience.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@PostMapping("/experience-service/user/{creatorId}/experience/{experienceId}")
	public ResponseEntity<Object> updateExperience(
			@PathVariable Long creatorId, 
			@PathVariable Long experienceId,
			@RequestBody Experience experience) {
		logger.info("attempted to update exprience of user with id {} and experienceId {}", 
				creatorId,
				experienceId);
		
		logger.info("requested exp {}" + experience);
		
		Experience toBeUpdatedExperience = repository.getById(experienceId);
		if (toBeUpdatedExperience.getCreatorId() != creatorId) {
			throw new ExperienceNotFoundException(
					"creatorId: " + creatorId + " experienceId: " + experienceId);
		}
		
		Experience updatedExperience = experience;
		updatedExperience.setCreatorId(creatorId);
		updatedExperience.setId(experienceId);
		updatedExperience.setStartDate(formatSqlDate(updatedExperience.getStartDate().toString()));
		updatedExperience.setEndDate(formatSqlDate(updatedExperience.getEndDate().toString()));
		repository.save(updatedExperience);
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.build().toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	private Date formatSqlDate(String strDate) {
		int updatedDay = Integer.valueOf(strDate.split("-")[2])+1;
		String formattedDate = new String(strDate.substring(0,8) + updatedDay);
		return Date.valueOf(formattedDate);
	}
	
	@DeleteMapping("/experience-service/user/{creatorId}/experience/{experienceId}")
	public ResponseEntity<Object> deleteSkill(
			@PathVariable Long creatorId, 
			@PathVariable Long experienceId) {
		logger.info("attempted to delete skill of user with id {} and experience id {} ", 
				creatorId, 
				experienceId);
		
		Optional<Experience> experienceToDelete = repository.findById(experienceId);
		
		if (experienceToDelete.isPresent() && experienceToDelete.get().getCreatorId() == creatorId) {
			repository.delete(experienceToDelete.get());
			return new ResponseEntity<Object>(HttpStatus.OK);

		} else {
			throw new ExperienceNotFoundException(
					"creatorId: " + creatorId + " experienceId: " + experienceId);
		}
	}
	
}
