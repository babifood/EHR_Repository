package com.babifood.clocked.entrty;

import org.springframework.stereotype.Component;

/**
 * 法定节假日实体类
 * @author BABIFOOD
 *
 */
@Component
public class Holiday {
	// 年度
	private Integer year;
	// 开始日期
	private String beginDate;
	// 结束日期
	private String endDate;
	// 天数
	private Double days;
	// 备注
	private String remark;
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public double getDays() {
		return days;
	}
	public void setDays(double days) {
		this.days = days;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}	
	
}
