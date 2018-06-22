package com.babifood.entity;

public class ArrangementBaseTimeEntity {

	private Integer id;
	
	private String arrangementName;
	
	private String startTime;
	
	private String endTime;
	
	private String remark;

	public ArrangementBaseTimeEntity(Integer id, String arrangementName, String startTime, String endTime, String remark) {
		super();
		this.id = id;
		this.arrangementName = arrangementName;
		this.startTime = startTime;
		this.endTime = endTime;
		this.remark = remark;
	}

	public ArrangementBaseTimeEntity() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getArrangementName() {
		return arrangementName;
	}

	public void setArrangementName(String arrangementName) {
		this.arrangementName = arrangementName;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
