package com.babifood.entity;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class UserRoleEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String role_id;
	private String role_name;
	
	public UserRoleEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getRole_id() {
		return role_id;
	}
	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}
	public String getRole_name() {
		return role_name;
	}
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}
	
}
