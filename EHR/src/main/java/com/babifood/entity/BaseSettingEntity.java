package com.babifood.entity;

public class BaseSettingEntity {

	private Integer id;//主键
	
	private String pNumber; //员工工号
	
	private String workType; //工作类型  0-计时    1-计件
	
	private String workPlace; //工作地区
	
	private String ismeal;//是否有餐补

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getpNumber() {
		return pNumber;
	}

	public void setpNumber(String pNumber) {
		this.pNumber = pNumber;
	}

	public String getWorkType() {
		return workType;
	}

	public void setWorkType(String workType) {
		this.workType = workType;
	}

	public String getWorkPlace() {
		return workPlace;
	}

	public void setWorkPlace(String workPlace) {
		this.workPlace = workPlace;
	}

	public String getIsmeal() {
		return ismeal;
	}

	public void setIsmeal(String ismeal) {
		this.ismeal = ismeal;
	}
	
}
