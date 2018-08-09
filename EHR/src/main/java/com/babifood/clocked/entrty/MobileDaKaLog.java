package com.babifood.clocked.entrty;

import java.io.Serializable;

/**
 * 移动打卡实体
 * @author BABIFOOD
 *
 */
public class MobileDaKaLog implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2671628303865849631L;
	private String workNum;
	// 打卡时间
	private String clockTime;
	// 打卡日期字符
	private String clockDate;
	// 打卡地点类型
	private String locationTypeId;
	public String getWorkNum() {
		return workNum;
	}
	public void setWorkNum(String workNum) {
		this.workNum = workNum;
	}
	public String getClockTime() {
		return clockTime;
	}
	public void setClockTime(String clockTime) {
		this.clockTime = clockTime;
	}
	public String getClockDate() {
		return clockDate;
	}
	public void setClockDate(String clockDate) {
		this.clockDate = clockDate;
	}
	public String getLocationTypeId() {
		return locationTypeId;
	}
	public void setLocationTypeId(String locationTypeId) {
		this.locationTypeId = locationTypeId;
	}
	public String getLocation() {
		if("7031085782734490657".equals(locationTypeId)){
			return "门店";
		}
		if("1603086988708757755".equals(locationTypeId)){
			return "公司";
		}
		return "";
	}
}
