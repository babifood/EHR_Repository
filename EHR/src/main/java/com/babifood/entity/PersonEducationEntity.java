package com.babifood.entity;

import org.springframework.stereotype.Component;
//教育背景
@Component
public class PersonEducationEntity {
	private Integer e_id;
	private String e_p_id;
	private String e_begin_date;
	private String e_end_date;
	private String e_organization_name;
	private String e_specialty;
	private String e_xueli;
	private String e_xuewei;
	private String e_property;
	private String e_desc;
	
	public PersonEducationEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PersonEducationEntity(Integer e_id, String e_p_id, String e_begin_date, String e_end_date,
			String e_organization_name, String e_specialty, String e_xueli, String e_xuewei, String e_property,
			String e_desc) {
		super();
		this.e_id = e_id;
		this.e_p_id = e_p_id;
		this.e_begin_date = e_begin_date;
		this.e_end_date = e_end_date;
		this.e_organization_name = e_organization_name;
		this.e_specialty = e_specialty;
		this.e_xueli = e_xueli;
		this.e_xuewei = e_xuewei;
		this.e_property = e_property;
		this.e_desc = e_desc;
	}
	public Integer getE_id() {
		return e_id;
	}
	public void setE_id(Integer e_id) {
		this.e_id = e_id;
	}
	public String getE_p_id() {
		return e_p_id;
	}
	public void setE_p_id(String e_p_id) {
		this.e_p_id = e_p_id;
	}
	public String getE_begin_date() {
		return e_begin_date;
	}
	public void setE_begin_date(String e_begin_date) {
		this.e_begin_date = e_begin_date;
	}
	public String getE_end_date() {
		return e_end_date;
	}
	public void setE_end_date(String e_end_date) {
		this.e_end_date = e_end_date;
	}
	public String getE_organization_name() {
		return e_organization_name;
	}
	public void setE_organization_name(String e_organization_name) {
		this.e_organization_name = e_organization_name;
	}
	public String getE_specialty() {
		return e_specialty;
	}
	public void setE_specialty(String e_specialty) {
		this.e_specialty = e_specialty;
	}
	public String getE_xueli() {
		return e_xueli;
	}
	public void setE_xueli(String e_xueli) {
		this.e_xueli = e_xueli;
	}
	public String getE_xuewei() {
		return e_xuewei;
	}
	public void setE_xuewei(String e_xuewei) {
		this.e_xuewei = e_xuewei;
	}
	public String getE_property() {
		return e_property;
	}
	public void setE_property(String e_property) {
		this.e_property = e_property;
	}
	public String getE_desc() {
		return e_desc;
	}
	public void setE_desc(String e_desc) {
		this.e_desc = e_desc;
	}
	
}
