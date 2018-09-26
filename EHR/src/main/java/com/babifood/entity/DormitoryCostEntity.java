package com.babifood.entity;

public class DormitoryCostEntity {

	private Integer id;//主键
	
	private String year;//年
	
	private String month;//月
	
	private String pNumber;//员工工号
	
	private String dormFee;//住宿费用
	
	private String electricityFee;//电费
	
	private String dormBonus;//宿舍奖励
	
	private String dormDeduction;//住宿扣款

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getpNumber() {
		return pNumber;
	}

	public void setpNumber(String pNumber) {
		this.pNumber = pNumber;
	}

	public String getDormFee() {
		return dormFee;
	}

	public void setDormFee(String dormFee) {
		this.dormFee = dormFee;
	}

	public String getElectricityFee() {
		return electricityFee;
	}

	public void setElectricityFee(String electricityFee) {
		this.electricityFee = electricityFee;
	}

	public String getDormBonus() {
		return dormBonus;
	}

	public void setDormBonus(String dormBonus) {
		this.dormBonus = dormBonus;
	}

	public String getDormDeduction() {
		return dormDeduction;
	}

	public void setDormDeduction(String dormDeduction) {
		this.dormDeduction = dormDeduction;
	}
	
}
