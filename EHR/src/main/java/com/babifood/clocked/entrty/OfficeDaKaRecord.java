package com.babifood.clocked.entrty;

import java.util.Date;

/***
 * 办公室打卡记录
 * @author BABIFOOD
 *
 */
public class OfficeDaKaRecord extends Person{
	private String beginTime;
	private String endTime;
	private Date clockedDate;
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Date getClockedDate() {
		return clockedDate;
	}
	public void setClockedDate(Date clockedDate) {
		this.clockedDate = clockedDate;
	}
	
}
