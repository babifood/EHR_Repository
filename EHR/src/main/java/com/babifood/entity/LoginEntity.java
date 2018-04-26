package com.babifood.entity;

import org.springframework.stereotype.Component;

@Component
public class LoginEntity {
	private Integer user_id;
	private String user_name;
	private String password;
	private String show_name;
	private String e_mail;
	private String phone;
	private String state;
	
	
	public LoginEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LoginEntity(Integer user_id, String user_name, String password,String show_name,String e_mail, String phone, String status) {
		super();
		this.user_id = user_id;
		this.user_name = user_name;
		this.password = password;
		this.show_name = show_name;
		this.e_mail = e_mail;
		this.phone = phone;
		this.state = status;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getE_mail() {
		return e_mail;
	}
	public void setE_mail(String e_mail) {
		this.e_mail = e_mail;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getShow_name() {
		return show_name;
	}
	public void setShow_name(String show_name) {
		this.show_name = show_name;
	}
	@Override
	public String toString() {
		return "LoginEntity [user_id=" + user_id + ", user_name=" + user_name + ", password=" + password + ", e_mail="
				+ e_mail + ", phone=" + phone + ", state=" + state + "]";
	}
	
}
