package com.babifood.entity;

import org.springframework.stereotype.Component;
//工作经历-入职前
@Component
public class PersonWorkFront {
	private Integer w_id;
	private String w_p_id;
	private String w_begin_date;
	private String w_end_date;
	private String w_company_name;
	private String w_type;
	private String w_post_name;
	private String w_prove;
	private String w_prove_post;
	private String w_prove_phone;
	private String w_demission_desc;
	private String w_desc;
	public PersonWorkFront() {
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
	public String getW_type() {
		return w_type;
	}
	public void setW_type(String w_type) {
		this.w_type = w_type;
	}
	public String getW_post_name() {
		return w_post_name;
	}
	public void setW_post_name(String w_post_name) {
		this.w_post_name = w_post_name;
	}
	public String getW_prove() {
		return w_prove;
	}
	public void setW_prove(String w_prove) {
		this.w_prove = w_prove;
	}
	public String getW_prove_post() {
		return w_prove_post;
	}
	public void setW_prove_post(String w_prove_post) {
		this.w_prove_post = w_prove_post;
	}
	public String getW_prove_phone() {
		return w_prove_phone;
	}
	public void setW_prove_phone(String w_prove_phone) {
		this.w_prove_phone = w_prove_phone;
	}
	public String getW_demission_desc() {
		return w_demission_desc;
	}
	public void setW_demission_desc(String w_demission_desc) {
		this.w_demission_desc = w_demission_desc;
	}
	public String getW_desc() {
		return w_desc;
	}
	public void setW_desc(String w_desc) {
		this.w_desc = w_desc;
	}
	
}
