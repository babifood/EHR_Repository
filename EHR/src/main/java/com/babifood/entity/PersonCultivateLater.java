package com.babifood.entity;

import org.springframework.stereotype.Component;
//培训经历-入职后
@Component
public class PersonCultivateLater {
	private Integer c_id;
	private String c_p_id;
	private String c_begin_date;
	private String c_end_date;
	private String c_training_institution;
	private String c_training_course;
	private String c_training_add;
	private String c_certificate;
	private String c_training_type;
	private String c_training_begin_date;
	private String c_training_end_date;
	private Double c_training_cost;
	private String c_desc;
	
	public PersonCultivateLater() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PersonCultivateLater(Integer c_id, String c_p_id, String c_begin_date, String c_end_date,
			String c_training_institution, String c_training_course, String c_training_add, String c_certificate,
			String c_training_type, String c_training_begin_date, String c_training_end_date, Double c_training_cost,
			String c_desc) {
		super();
		this.c_id = c_id;
		this.c_p_id = c_p_id;
		this.c_begin_date = c_begin_date;
		this.c_end_date = c_end_date;
		this.c_training_institution = c_training_institution;
		this.c_training_course = c_training_course;
		this.c_training_add = c_training_add;
		this.c_certificate = c_certificate;
		this.c_training_type = c_training_type;
		this.c_training_begin_date = c_training_begin_date;
		this.c_training_end_date = c_training_end_date;
		this.c_training_cost = c_training_cost;
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

	public String getC_training_institution() {
		return c_training_institution;
	}

	public void setC_training_institution(String c_training_institution) {
		this.c_training_institution = c_training_institution;
	}

	public String getC_training_course() {
		return c_training_course;
	}

	public void setC_training_course(String c_training_course) {
		this.c_training_course = c_training_course;
	}

	public String getC_training_add() {
		return c_training_add;
	}

	public void setC_training_add(String c_training_add) {
		this.c_training_add = c_training_add;
	}

	public String getC_certificate() {
		return c_certificate;
	}

	public void setC_certificate(String c_certificate) {
		this.c_certificate = c_certificate;
	}

	public String getC_training_type() {
		return c_training_type;
	}

	public void setC_training_type(String c_training_type) {
		this.c_training_type = c_training_type;
	}

	public String getC_training_begin_date() {
		return c_training_begin_date;
	}

	public void setC_training_begin_date(String c_training_begin_date) {
		this.c_training_begin_date = c_training_begin_date;
	}

	public String getC_training_end_date() {
		return c_training_end_date;
	}

	public void setC_training_end_date(String c_training_end_date) {
		this.c_training_end_date = c_training_end_date;
	}

	public Double getC_training_cost() {
		return c_training_cost;
	}

	public void setC_training_cost(Double c_training_cost) {
		this.c_training_cost = c_training_cost;
	}

	public String getC_desc() {
		return c_desc;
	}

	public void setC_desc(String c_desc) {
		this.c_desc = c_desc;
	}
	
}
