package com.babifood.entity;

import org.springframework.stereotype.Component;

//获得证书
@Component
public class PersonCertificate {
	private Integer c_id;
	private String c_p_id;
	private String c_name;
	private String c_organization;
	private String c_begin_date;
	private String c_end_date;
	private String c_desc;
	public PersonCertificate() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Integer getC_id() {
		return c_id;
	}
	public void setC_id(Integer c_id) {
		this.c_id = c_id;
	}
	public String getC_p_id() {
		return c_p_id;
	}
	public void setC_p_id(String c_p_id) {
		this.c_p_id = c_p_id;
	}
	public String getC_name() {
		return c_name;
	}
	public void setC_name(String c_name) {
		this.c_name = c_name;
	}
	public String getC_organization() {
		return c_organization;
	}
	public void setC_organization(String c_organization) {
		this.c_organization = c_organization;
	}
	public String getC_begin_date() {
		return c_begin_date;
	}
	public void setC_begin_date(String c_begin_date) {
		this.c_begin_date = c_begin_date;
	}
	public String getC_end_date() {
		return c_end_date;
	}
	public void setC_end_date(String c_end_date) {
		this.c_end_date = c_end_date;
	}
	public String getC_desc() {
		return c_desc;
	}
	public void setC_desc(String c_desc) {
		this.c_desc = c_desc;
	}
	
}
