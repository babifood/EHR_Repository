package com.babifood.entity;

import org.springframework.stereotype.Component;

@Component
public class Certificaten {
	private Integer c_id;
	private String c_p_id;
	private String c_p_number;
	private String c_p_name;
	private String c_certificate_name;
	private String c_organization;
	private String c_certificate_number;
	private String c_begin_date;
	private String c_end_date;
	private String c_desc;
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
	public String getC_p_number() {
		return c_p_number;
	}
	public void setC_p_number(String c_p_number) {
		this.c_p_number = c_p_number;
	}
	public String getC_p_name() {
		return c_p_name;
	}
	public void setC_p_name(String c_p_name) {
		this.c_p_name = c_p_name;
	}
	public String getC_certificate_name() {
		return c_certificate_name;
	}
	public void setC_certificate_name(String c_certificate_name) {
		this.c_certificate_name = c_certificate_name;
	}
	public String getC_organization() {
		return c_organization;
	}
	public void setC_organization(String c_organization) {
		this.c_organization = c_organization;
	}
	public String getC_certificate_number() {
		return c_certificate_number;
	}
	public void setC_certificate_number(String c_certificate_number) {
		this.c_certificate_number = c_certificate_number;
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
