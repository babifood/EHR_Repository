package com.babifood.entity;

import org.springframework.stereotype.Component;

@Component
public class PositionEntity {
	private int position_id;
	private String position_name;
	private int joblevel_id;
	
	public PositionEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getposition_id() {
		return position_id;
	}
	public void setposition_id(int position_id) {
		this.position_id = position_id;
	}
	public String getposition_name() {
		return position_name;
	}
	public void setposition_name(String position_name) {
		this.position_name = position_name;
	}
	public int getjoblevel_id() {
		return joblevel_id;
	}
	public void setjoblevel_id(int joblevel_id) {
		this.joblevel_id = joblevel_id;
	}
	
}
