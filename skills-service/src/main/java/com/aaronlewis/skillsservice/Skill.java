package com.aaronlewis.skillsservice;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Skill {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Long creatorId;
	
	private String skillName;
	private int value;
	
	public Skill() {}

	public Skill(Long creatorId, String skillName, int value, String environment) {
		super();
		this.creatorId = creatorId;
		this.skillName = skillName;
		this.value = value;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public String getSkillName() {
		return skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
}
