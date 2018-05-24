package com.babifood.entity;

import org.springframework.stereotype.Component;

@Component
public class PostEntity {
	private int post_id;
	private String post_name;
	private int position_id;
	
	public PostEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getpost_id() {
		return post_id;
	}
	public void setpost_id(int post_id) {
		this.post_id = post_id;
	}
	public String getpost_name() {
		return post_name;
	}
	public void setpost_name(String post_name) {
		this.post_name = post_name;
	}
	public int getposition_id() {
		return position_id;
	}
	public void setposition_id(int position_id) {
		this.position_id = position_id;
	}
	
}
