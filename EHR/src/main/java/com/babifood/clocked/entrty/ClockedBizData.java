package com.babifood.clocked.entrty;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class ClockedBizData {
	// 工号
	private String workNum;
	// 年度
	private int year;
	// 月度
	private int month;
	// 日期
	private Date createTime;
	// 业务单据日期
	private Date billDate;
	// 业务单据号
	private String billNum;
	// 业务单据类型
	private String billType;
	// 时长
	private Double timeLength;
	// 开始时间
	private Date beginTime;
	// 结束时间
	private Date endTime;
	// 是否有效
	private String validFlag;
	//
	private String bizFlag1;
	
	public String getBizFlag1() {
		return bizFlag1;
	}
	public void setBizFlag1(String bizFlag1) {
		this.bizFlag1 = bizFlag1;
	}
	public String getWorkNum() {
		return workNum;
	}
	public void setWorkNum(String workNum) {
		this.workNum = workNum;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Date getBillDate() {
		return billDate;
	}
	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}
	public String getBillNum() {
		return billNum;
	}
	public void setBillNum(String billNum) {
		this.billNum = billNum;
	}
	public String getBillType() {
		return billType;
	}
	public void setBillType(String billType) {
		this.billType = billType;
	}
	public Double getTimeLength() {
		return timeLength;
	}
	public void setTimeLength(Double timeLength) {
		this.timeLength = timeLength;
	}
	
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getValidFlag() {
		return validFlag;
	}
	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
	}
		
}
