package com.aaronlewis.experienceservice;

import java.util.List;

public class Experiences {
	
	private List<Experience> experiences;
	private String environment;
	
	Experiences() {}

	public Experiences(List<Experience> experiences, String environment) {
		super();
		this.experiences = experiences;
		this.environment = environment;
	}

	public List<Experience> getExperiences() {
		return experiences;
	}

	public void setExperiences(List<Experience> experiences) {
		this.experiences = experiences;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}
	
}
