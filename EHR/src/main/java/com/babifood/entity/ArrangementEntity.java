package com.babifood.entity;

public class ArrangementEntity {

	private Integer id;//主键
	
	private String date;//日期
	
	private Integer baseTimeId;//基础时间ID
	
	private String startTime;//开始时间
	
	private String endTime;//结束时间
	
	private String attendance;//考勤类型  大小周、1.5休等
	
	private String isAttend;//是否考勤
	
	private String targetType;//考勤优先级 1-公司 2-中心 3-部门 4-科室 5-组 6-人 10-人员
	
	private String targetId;//目标ID
	
	private String targetName;//机构/员工名称
	
	private String creatorId;//创建用户id
	
	private String creatorName;//创建用户名称
	
	private String remark;//备注

	public ArrangementEntity() {
	}

	public ArrangementEntity(Integer id, String date, Integer baseTimeId, String startTime, String endTime,
			String attendance, String isAttend, String targetType, String targetId, String targetName, String creatorId,
			String creatorName, String remark) {
		super();
		this.id = id;
		this.date = date;
		this.baseTimeId = baseTimeId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.attendance = attendance;
		this.isAttend = isAttend;
		this.targetType = targetType;
		this.targetId = targetId;
		this.targetName = targetName;
		this.creatorId = creatorId;
		this.creatorName = creatorName;
		this.remark = remark;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Integer getBaseTimeId() {
		return baseTimeId;
	}

	public void setBaseTimeId(Integer baseTimeId) {
		this.baseTimeId = baseTimeId;
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

	public String getAttendance() {
		return attendance;
	}

	public void setAttendance(String attendance) {
		this.attendance = attendance;
	}

	public String getIsAttend() {
		return isAttend;
	}

	public void setIsAttend(String isAttend) {
		this.isAttend = isAttend;
	}

	public String getTargetType() {
		return targetType;
	}

	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
