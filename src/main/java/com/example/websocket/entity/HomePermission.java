package com.example.websocket.entity;

import com.example.websocket.entity.device.AccessType;

/**
 * Control the access type for a plant for the granted users
 */
public class HomePermission {

	private String login;
	private AccessType accessType;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public AccessType getAccessType() {
		return accessType;
	}

	public void setAccessType(AccessType accessType) {
		this.accessType = accessType;
	}

}