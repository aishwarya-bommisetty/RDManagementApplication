package com.epam.rdmanagement.model;

public class UserAddModel extends UserProfileModel {
	private String password;
	private int roleId;

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
