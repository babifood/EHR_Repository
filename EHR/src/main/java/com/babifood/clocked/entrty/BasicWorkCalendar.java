package com.babifood.clocked.entrty;

import org.springframework.stereotype.Component;

/**
 * 基础排班日实体
 * @author BABIFOOD
 *
 */
@Component
public class BasicWorkCalendar {
	private String target_id;
	private String target_type;
	private String arrangement_name;
	private String start_time;
	private String end_time;
	private String arrangement_type;
	public String getTarget_id() {
		return target_id;
	}
	public void setTarget_id(String target_id) {
		this.target_id = target_id;
	}
	public String getTarget_type() {
		return target_type;
	}
	public void setTarget_type(String target_type) {
		this.target_type = target_type;
	}
	public String getArrangement_name() {
		return arrangement_name;
	}
	public void setArrangement_name(String arrangement_name) {
		this.arrangement_name = arrangement_name;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public String getArrangement_type() {
		return arrangement_type;
	}
	public void setArrangement_type(String arrangement_type) {
		this.arrangement_type = arrangement_type;
	}
	
}
