package com.aaronlewis.skillsservice;

import java.net.URI;
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
public class SkillController {
	
	Logger logger = LoggerFactory.getLogger(SkillController.class);
	
	@Autowired
	private SkillRepository repository;
	
	@Autowired
	private Environment environment;

	@GetMapping("/skills-service/user/{creatorId}")
	public Skills retrieveSkills(@PathVariable Long creatorId) {
		
		logger.info("retrieveSkill called for user with id {}", creatorId);
		
		List<Skill> skill = null;
		skill = repository.findByCreatorId(creatorId);
		String port = environment.getProperty("local.server.port");
		String host = environment.getProperty("HOSTNAME");
		Skills skills = new Skills();
		skills.setSkills(skill);
		skills.setEnvironment(host + ':' + port);
		return skills;
	}
	
	@GetMapping("/skills-service/user/{creatorId}/skill/{skillId}")
	public Skill retrieveSkill(@PathVariable Long creatorId, @PathVariable Long skillId) {
		
		logger.info("retrieveSkill called for user with id {} and skill id {}", 
				creatorId, skillId);
		
		Optional<Skill> optionalSkill = repository.findById(skillId);
		
		if (optionalSkill.isEmpty() || optionalSkill.get().getCreatorId() != creatorId) {
			throw new SkillNotFoundException("creatorId: " + creatorId + " skillId: " + skillId);
		}
		
		return optionalSkill.get();
	}
	
	@PostMapping("/skills-service/user/{creatorId}")
	public ResponseEntity<Object> saveSkill(@PathVariable Long creatorId, @RequestBody Skill skill) {
		logger.info("attempted to create skill of user with id {}", creatorId);
		
		Skills skills = retrieveSkills(creatorId);
		Skill updatedSkill = skills.getSkill(skill.getSkillName());
		if (updatedSkill != null) {
			updatedSkill.setValue(skill.getValue());
		} else {
			updatedSkill = skill;
			skill.setCreatorId(creatorId);
		}
		
		Skill savedSkill = repository.save(updatedSkill);
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/skills/{id}")
				.buildAndExpand(savedSkill.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@PostMapping("/skills-service/user/{creatorId}/skills/{skillId}")
	public ResponseEntity<Object> updateSkill(
			@PathVariable Long creatorId, 
			@PathVariable Long skillId,
			@RequestBody Skill skill) {
		logger.info("attempted to update skill of user with id {} and skill id {}", 
				creatorId, skillId);
		
		Skill updatedSkill = repository.getById(skillId);
		if (updatedSkill.getCreatorId() != creatorId) {
			throw new SkillNotFoundException("creatorId: " + creatorId + " skillId: " + skillId);
		}
		updatedSkill.setSkillName(skill.getSkillName());
		updatedSkill.setValue(skill.getValue());
		repository.save(updatedSkill);
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.build()
				.toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	
	@DeleteMapping("/skills-service/user/{creatorId}/skills/{skillId}")
	public ResponseEntity<Object> deleteSkill(
			@PathVariable Long creatorId, 
			@PathVariable Long skillId) {
		logger.info("attempted to delete skill of user with id {} and skill id {} ", creatorId, skillId);
		
		Optional<Skill> skillToDelete = repository.findById(skillId);
		if (skillToDelete.isPresent()) {
			repository.delete(skillToDelete.get());
			return new ResponseEntity<Object>(HttpStatus.OK);

		} else {
			throw new SkillNotFoundException("creatorId: " + creatorId + " skillId: " + skillId);
		}
	}
}
