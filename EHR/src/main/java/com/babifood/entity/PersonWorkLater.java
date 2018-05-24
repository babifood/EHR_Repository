package com.babifood.entity;

import org.springframework.stereotype.Component;
//工作经历-入职后
@Component
public class PersonWorkLater {
	private Integer w_id;
	private String w_p_id;
	private String w_begin_date;
	private String w_end_date;
	private String w_company_name;
	private String w_post_name;
	private String w_desc;
	
	public PersonWorkLater() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Integer getW_id() {
		return w_id;
	}
	public void setW_id(Integer w_id) {
		this.w_id = w_id;
	}
	public String getW_p_id() {
		return w_p_id;
	}
	public void setW_p_id(String w_p_id) {
		this.w_p_id = w_p_id;
	}
	public String getW_begin_date() {
		return w_begin_date;
	}
	public void setW_begin_date(String w_begin_date) {
		this.w_begin_date = w_begin_date;
	}
	public String getW_end_date() {
		return w_end_date;
	}
	public void setW_end_date(String w_end_date) {
		this.w_end_date = w_end_date;
	}
	public String getW_company_name() {
		return w_company_name;
	}
	public void setW_company_name(String w_company_name) {
		this.w_company_name = w_company_name;
	}
	public String getW_post_name() {
		return w_post_name;
	}
	public void setW_post_name(String w_post_name) {
		this.w_post_name = w_post_name;
	}
	public String getW_desc() {
		return w_desc;
	}
	public void setW_desc(String w_desc) {
		this.w_desc = w_desc;
	}
	
}
