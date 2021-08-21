package com.aaronlewis.profileservice;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Profile {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(unique=true)
	private Long creatorId;
	
	private String firstName;
	private String lastName;

	private String linkedInUrl;
	private String gitHubUrl;
	private String email;
	private String skypeUrl;
	private String phone;
	
	private String resumeUrl;

	private String street;
	private String city;
	private String state;
	private String zipcode;

	private String environment;
	
	Profile() {}

	public Profile(Long id, Long creatorId, String firstName, String lastName, String linkedInUrl, String gitHubUrl,
			String email, String skypeUrl, String phone, String resumeUrl, String street, String city, String state,
			String zipcode, String environment) {
		super();
		this.id = id;
		this.creatorId = creatorId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.linkedInUrl = linkedInUrl;
		this.gitHubUrl = gitHubUrl;
		this.email = email;
		this.skypeUrl = skypeUrl;
		this.phone = phone;
		this.resumeUrl = resumeUrl;
		this.street = street;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
		this.environment = environment;
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLinkedInUrl() {
		return linkedInUrl;
	}

	public void setLinkedInUrl(String linkedInUrl) {
		this.linkedInUrl = linkedInUrl;
	}

	public String getGitHubUrl() {
		return gitHubUrl;
	}

	public void setGitHubUrl(String gitHubUrl) {
		this.gitHubUrl = gitHubUrl;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSkypeUrl() {
		return skypeUrl;
	}

	public void setSkypeUrl(String skypeUrl) {
		this.skypeUrl = skypeUrl;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getResumeUrl() {
		return resumeUrl;
	}

	public void setResumeUrl(String resumeUrl) {
		this.resumeUrl = resumeUrl;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}
	
}