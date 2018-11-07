package com.babifood.entity;

import org.springframework.stereotype.Component;

@Component
public class PostEntity {
	private int post_id;
	private String post_name;
	private int position_id;
	private int post_project_id;
	
	public int getPost_id() {
		return post_id;
	}
	public void setPost_id(int post_id) {
		this.post_id = post_id;
	}
	public String getPost_name() {
		return post_name;
	}
	public void setPost_name(String post_name) {
		this.post_name = post_name;
	}
	public int getPosition_id() {
		return position_id;
	}
	public void setPosition_id(int position_id) {
		this.position_id = position_id;
	}
	public int getPost_project_id() {
		return post_project_id;
	}
	public void setPost_project_id(int post_project_id) {
		this.post_project_id = post_project_id;
	}
	
}
