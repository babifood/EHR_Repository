package com.babifood.entity;

import org.springframework.stereotype.Component;

//家庭背景
@Component
public class PersonFamily {
	private Integer f_id;
	private String f_p_id;
	private String f_relation;
	private String f_name;
	private String f_date;
	private String f_company;
	private String f_duty;
	private String f_desc;
	public PersonFamily() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Integer getF_id() {
		return f_id;
	}
	public void setF_id(Integer f_id) {
		this.f_id = f_id;
	}
	public String getF_p_id() {
		return f_p_id;
	}
	public void setF_p_id(String f_p_id) {
		this.f_p_id = f_p_id;
	}
	public String getF_relation() {
		return f_relation;
	}
	public void setF_relation(String f_relation) {
		this.f_relation = f_relation;
	}
	public String getF_name() {
		return f_name;
	}
	public void setF_name(String f_name) {
		this.f_name = f_name;
	}
	public String getF_date() {
		return f_date;
	}
	public void setF_date(String f_date) {
		this.f_date = f_date;
	}
	public String getF_company() {
		return f_company;
	}
	public void setF_company(String f_company) {
		this.f_company = f_company;
	}
	public String getF_duty() {
		return f_duty;
	}
	public void setF_duty(String f_duty) {
		this.f_duty = f_duty;
	}
	public String getF_desc() {
		return f_desc;
	}
	public void setF_desc(String f_desc) {
		this.f_desc = f_desc;
	}
	
}
