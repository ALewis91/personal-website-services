package com.aaronlewis.userservice;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalWebsiteUserRepository extends JpaRepository<PersonalWebsiteUser, Long>{
	public Optional<PersonalWebsiteUser> findByEmail(String email);
}
