package com.aaronlewis.userservice;

public class Login {
	
	private Long userId;

	public Login() {}
	
	public Login(Long userId) {
		super();
		this.userId = userId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
}
