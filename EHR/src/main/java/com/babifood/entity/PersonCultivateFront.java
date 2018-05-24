package com.babifood.entity;

import org.springframework.stereotype.Component;

//培训经历-入职前
@Component
public class PersonCultivateFront {
	private Integer c_id;
	private String c_p_id;
	private String c_begin_date;
	private String c_end_date;
	private String c_education;
	private String c_training_content;
	private String c_certificate;
	private String c_desc;
	
	public PersonCultivateFront() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PersonCultivateFront(Integer c_id, String c_p_id, String c_begin_date, String c_end_date, String c_education,
			String c_training_content, String c_certificate, String c_desc) {
		super();
		this.c_id = c_id;
		this.c_p_id = c_p_id;
		this.c_begin_date = c_begin_date;
		this.c_end_date = c_end_date;
		this.c_education = c_education;
		this.c_training_content = c_training_content;
		this.c_certificate = c_certificate;
		this.c_desc = c_desc;
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
	public String getC_education() {
		return c_education;
	}
	public void setC_education(String c_education) {
		this.c_education = c_education;
	}
	public String getC_training_content() {
		return c_training_content;
	}
	public void setC_training_content(String c_training_content) {
		this.c_training_content = c_training_content;
	}
	public String getC_certificate() {
		return c_certificate;
	}
	public void setC_certificate(String c_certificate) {
		this.c_certificate = c_certificate;
	}
	public String getC_desc() {
		return c_desc;
	}
	public void setC_desc(String c_desc) {
		this.c_desc = c_desc;
	}
	
	
}
