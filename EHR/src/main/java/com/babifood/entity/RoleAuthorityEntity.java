package com.babifood.entity;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class RoleAuthorityEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1633813746125786214L;
	private String authority_id;
	private String authority_name;
	private String authority_code;
	public String getAuthority_id() {
		return authority_id;
	}
	public void setAuthority_id(String authority_id) {
		this.authority_id = authority_id;
	}
	public String getAuthority_name() {
		return authority_name;
	}
	public void setAuthority_name(String authority_name) {
		this.authority_name = authority_name;
	}
	public String getAuthority_code() {
		return authority_code;
	}
	public void setAuthority_code(String authority_code) {
		this.authority_code = authority_code;
	}
	
}
