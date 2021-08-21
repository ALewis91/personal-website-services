package com.aaronlewis.skillsservice;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Long> {
	
	public List<Skill> findByCreatorId(Long creatorId);

}
