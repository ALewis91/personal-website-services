package com.aaronlewis.experienceservice;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExperienceRepository extends JpaRepository<Experience, Long>{

//	@Query("SELECT exp FROM Experience WHERE ")
//	List<Experience> findAllUserExperiencesOrderedByDate();
	
	List<Experience> findByCreatorId(Long creatorId);
}
