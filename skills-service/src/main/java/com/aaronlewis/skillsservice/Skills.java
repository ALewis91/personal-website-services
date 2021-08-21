package com.aaronlewis.skillsservice;

import java.util.List;

public class Skills {
	private List<Skill> skills;
	private String environment;
	
	Skills() {}

	public List<Skill> getSkills() {
		return skills;
	}

	public void setSkills(List<Skill> skills) {
		this.skills = skills;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}
	
	public Skill getSkill(String skillName) {
		for (Skill skill : skills) {
			if (skillName.compareToIgnoreCase(skill.getSkillName()) == 0) {
				return skill;
			}
		}
		return null;
	}
	
}
