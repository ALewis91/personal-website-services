package com.aaronlewis.profileservice;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long>{
	Optional<Profile> findByCreatorId(Long creatorId);
}
