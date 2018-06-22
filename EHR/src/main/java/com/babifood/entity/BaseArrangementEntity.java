package com.babifood.entity;

public class BaseArrangementEntity {

	private Integer id;
	
	private String arrangementName;
	
	private String arrangementType;
	
	private String startTime;
	
	private String endTime;
	
	private String remark;

	public BaseArrangementEntity(Integer id, String arrangementName, String arrangementType, String startTime,
			String endTime, String remark) {
		super();
		this.id = id;
		this.arrangementName = arrangementName;
		this.arrangementType = arrangementType;
		this.startTime = startTime;
		this.endTime = endTime;
		this.remark = remark;
	}

	public BaseArrangementEntity() {
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

	public String getArrangementType() {
		return arrangementType;
	}

	public void setArrangementType(String arrangementType) {
		this.arrangementType = arrangementType;
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
