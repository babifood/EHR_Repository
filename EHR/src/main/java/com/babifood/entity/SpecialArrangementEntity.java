package com.babifood.entity;

public class SpecialArrangementEntity {

	private String id;//主键
	
	private String date;//日期
	
	private String arrangementId;//排班id
	
	private String isAttend;//是否考勤
	
	private String startTime;//上班时间
	
	private String endTime;//下班时间
	
	private String remark;//备注

	public SpecialArrangementEntity() {
		super();
	}

	public SpecialArrangementEntity(String id, String date, String arrangementId, String isAttend, String startTime,
			String endTime, String remark) {
		super();
		this.id = id;
		this.date = date;
		this.arrangementId = arrangementId;
		this.isAttend = isAttend;
		this.startTime = startTime;
		this.endTime = endTime;
		this.remark = remark;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getArrangementId() {
		return arrangementId;
	}

	public void setArrangementId(String arrangementId) {
		this.arrangementId = arrangementId;
	}

	public String getIsAttend() {
		return isAttend;
	}

	public void setIsAttend(String isAttend) {
		this.isAttend = isAttend;
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
