package com.babifood.clocked.entrty;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
/**
 * 推送给OA考勤数据实体
 * @author BABIFOOD
 *
 */
public class PushOaDataEntrty {
	//	姓名
	private String userName;
	//	工号
	private String workNum;
	//	部门
	private String deptCode;
	//	单位机构
	private String organCode;
	//	日期
	private String checkingDate;
	//	星期
	private String week;
	//	年假
	private Double nianJia;
	//	缺勤小时数
	private Double queQin;
	//	婚假
	private Double hunJia;
	//	请假小时数
	private Double qingJia;
	//	迟到
	private Integer chiDao;
	//	早退
	private Integer zaoTui;
	//	旷工
	private Integer kuangGongCiShu;
	//	丧假
	private Double sangJia;
	//	餐补个数
	private Integer canBu;
	//	病假
	private Double bingJia;
	//	异动小时数
	private Double yiDong;
	//	打卡起始时间
	private String checkingBeginTime;
	//	打卡结束时间
	private String checkingEndTime;
	//	其它请假
	private Double otherQingJia;
	//	加班小时数
	private Double jiaBan;
	//	标准下班时间
	private String beginTime;
	//	标准上班时间
	private String endTime;
	//	出差小时数
	private Double chuCha;
	//	事假
	private Double shiJia;
	//	年度
	private String year;
	//	月份
	private String month;
	//	考勤标志
	private Integer clockFlag;
	//	打卡原始时长
	private Double originalCheckingLength;
	//	业务开始时间
	private String eventBeginTime;
	//	工作时长
	private Double actualWorkLength;
	//	业务结束时间
	private String eventEndTime;
	//	培训假
	private Double peiXunJia;
	//	产假
	private Double chanJia;
	//	陪产假
	private Double peiChanJia;
	//	调休
	private Double tiaoXiu;
	//	打卡地点
	private String daKaLocation;
	//	考勤方式
	private String checkingType;
	//	标准工作时长
	private Double standWorkLength;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getWorkNum() {
		return workNum;
	}
	public void setWorkNum(String workNum) {
		this.workNum = workNum;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getOrganCode() {
		return organCode;
	}
	public void setOrganCode(String organCode) {
		this.organCode = organCode;
	}
	public String getCheckingDate() {
		return checkingDate;
	}
	public void setCheckingDate(String checkingDate) {
		this.checkingDate = checkingDate;
	}
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	public Double getNianJia() {
		return nianJia;
	}
	public void setNianJia(Double nianJia) {
		this.nianJia = nianJia;
	}
	public Double getQueQin() {
		return queQin;
	}
	public void setQueQin(Double queQin) {
		this.queQin = queQin;
	}
	public Double getHunJia() {
		return hunJia;
	}
	public void setHunJia(Double hunJia) {
		this.hunJia = hunJia;
	}
	public Double getQingJia() {
		return qingJia;
	}
	public void setQingJia(Double qingJia) {
		this.qingJia = qingJia;
	}
	public Integer getChiDao() {
		return chiDao;
	}
	public void setChiDao(Integer chiDao) {
		this.chiDao = chiDao;
	}
	public Integer getZaoTui() {
		return zaoTui;
	}
	public void setZaoTui(Integer zaoTui) {
		this.zaoTui = zaoTui;
	}
	public Integer getKuangGongCiShu() {
		return kuangGongCiShu;
	}
	public void setKuangGongCiShu(Integer kuangGongCiShu) {
		this.kuangGongCiShu = kuangGongCiShu;
	}
	public Double getSangJia() {
		return sangJia;
	}
	public void setSangJia(Double sangJia) {
		this.sangJia = sangJia;
	}
	public Integer getCanBu() {
		return canBu;
	}
	public void setCanBu(Integer canBu) {
		this.canBu = canBu;
	}
	public Double getBingJia() {
		return bingJia;
	}
	public void setBingJia(Double bingJia) {
		this.bingJia = bingJia;
	}
	public Double getYiDong() {
		return yiDong;
	}
	public void setYiDong(Double yiDong) {
		this.yiDong = yiDong;
	}
	public String getCheckingBeginTime() {
		return checkingBeginTime;
	}
	public void setCheckingBeginTime(String checkingBeginTime) {
		this.checkingBeginTime = checkingBeginTime;
	}
	public String getCheckingEndTime() {
		return checkingEndTime;
	}
	public void setCheckingEndTime(String checkingEndTime) {
		this.checkingEndTime = checkingEndTime;
	}
	public Double getOtherQingJia() {
		return otherQingJia;
	}
	public void setOtherQingJia(Double otherQingJia) {
		this.otherQingJia = otherQingJia;
	}
	public Double getJiaBan() {
		return jiaBan;
	}
	public void setJiaBan(Double jiaBan) {
		this.jiaBan = jiaBan;
	}
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
	public Double getChuCha() {
		return chuCha;
	}
	public void setChuCha(Double chuCha) {
		this.chuCha = chuCha;
	}
	public Double getShiJia() {
		return shiJia;
	}
	public void setShiJia(Double shiJia) {
		this.shiJia = shiJia;
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
	public Integer getClockFlag() {
		return clockFlag;
	}
	public void setClockFlag(Integer clockFlag) {
		this.clockFlag = clockFlag;
	}
	public Double getOriginalCheckingLength() {
		return originalCheckingLength;
	}
	public void setOriginalCheckingLength(Double originalCheckingLength) {
		this.originalCheckingLength = originalCheckingLength;
	}
	public String getEventBeginTime() {
		return eventBeginTime;
	}
	public void setEventBeginTime(String eventBeginTime) {
		this.eventBeginTime = eventBeginTime;
	}
	public Double getActualWorkLength() {
		return actualWorkLength;
	}
	public void setActualWorkLength(Double actualWorkLength) {
		this.actualWorkLength = actualWorkLength;
	}
	public String getEventEndTime() {
		return eventEndTime;
	}
	public void setEventEndTime(String eventEndTime) {
		this.eventEndTime = eventEndTime;
	}
	public Double getPeiXunJia() {
		return peiXunJia;
	}
	public void setPeiXunJia(Double peiXunJia) {
		this.peiXunJia = peiXunJia;
	}
	public Double getChanJia() {
		return chanJia;
	}
	public void setChanJia(Double chanJia) {
		this.chanJia = chanJia;
	}
	public Double getPeiChanJia() {
		return peiChanJia;
	}
	public void setPeiChanJia(Double peiChanJia) {
		this.peiChanJia = peiChanJia;
	}
	public Double getTiaoXiu() {
		return tiaoXiu;
	}
	public void setTiaoXiu(Double tiaoXiu) {
		this.tiaoXiu = tiaoXiu;
	}
	public String getDaKaLocation() {
		return daKaLocation;
	}
	public void setDaKaLocation(String daKaLocation) {
		this.daKaLocation = daKaLocation;
	}
	public String getCheckingType() {
		return checkingType;
	}
	public void setCheckingType(String checkingType) {
		this.checkingType = checkingType;
	}
	public Double getStandWorkLength() {
		return standWorkLength;
	}
	public void setStandWorkLength(Double standWorkLength) {
		this.standWorkLength = standWorkLength;
	}
	@Override
	public String toString() {
		return "PushOaDataEntrty [userName=" + userName + ", workNum=" + workNum + ", deptCode=" + deptCode
				+ ", organCode=" + organCode + ", checkingDate=" + checkingDate + ", week=" + week + ", nianJia="
				+ nianJia + ", queQin=" + queQin + ", hunJia=" + hunJia + ", qingJia=" + qingJia + ", chiDao=" + chiDao
				+ ", zaoTui=" + zaoTui + ", kuangGongCiShu=" + kuangGongCiShu + ", sangJia=" + sangJia + ", canBu="
				+ canBu + ", bingJia=" + bingJia + ", yiDong=" + yiDong + ", checkingBeginTime=" + checkingBeginTime
				+ ", checkingEndTime=" + checkingEndTime + ", otherQingJia=" + otherQingJia + ", jiaBan=" + jiaBan
				+ ", beginTime=" + beginTime + ", endTime=" + endTime + ", chuCha=" + chuCha + ", shiJia=" + shiJia
				+ ", year=" + year + ", month=" + month + ", clockFlag=" + clockFlag + ", originalCheckingLength="
				+ originalCheckingLength + ", eventBeginTime=" + eventBeginTime + ", actualWorkLength="
				+ actualWorkLength + ", eventEndTime=" + eventEndTime + ", peiXunJia=" + peiXunJia + ", chanJia="
				+ chanJia + ", peiChanJia=" + peiChanJia + ", tiaoXiu=" + tiaoXiu + ", daKaLocation=" + daKaLocation
				+ ", checkingType=" + checkingType + ", standWorkLength=" + standWorkLength + "]";
	}
	public PushOaDataEntrty(String userName, String workNum, String deptCode, String organCode, String checkingDate,
			String week, Double nianJia, Double queQin, Double hunJia, Double qingJia, Integer chiDao, Integer zaoTui,
			Integer kuangGongCiShu, Double sangJia, Integer canBu, Double bingJia, Double yiDong,
			String checkingBeginTime, String checkingEndTime, Double otherQingJia, Double jiaBan, String beginTime,
			String endTime, Double chuCha, Double shiJia, String year, String month, Integer clockFlag,
			Double originalCheckingLength, String eventBeginTime, Double actualWorkLength, String eventEndTime,
			Double peiXunJia, Double chanJia, Double peiChanJia, Double tiaoXiu, String daKaLocation,
			String checkingType, Double standWorkLength) {
		super();
		this.userName = userName;
		this.workNum = workNum;
		this.deptCode = deptCode;
		this.organCode = organCode;
		this.checkingDate = checkingDate;
		this.week = week;
		this.nianJia = nianJia;
		this.queQin = queQin;
		this.hunJia = hunJia;
		this.qingJia = qingJia;
		this.chiDao = chiDao;
		this.zaoTui = zaoTui;
		this.kuangGongCiShu = kuangGongCiShu;
		this.sangJia = sangJia;
		this.canBu = canBu;
		this.bingJia = bingJia;
		this.yiDong = yiDong;
		this.checkingBeginTime = checkingBeginTime;
		this.checkingEndTime = checkingEndTime;
		this.otherQingJia = otherQingJia;
		this.jiaBan = jiaBan;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.chuCha = chuCha;
		this.shiJia = shiJia;
		this.year = year;
		this.month = month;
		this.clockFlag = clockFlag;
		this.originalCheckingLength = originalCheckingLength;
		this.eventBeginTime = eventBeginTime;
		this.actualWorkLength = actualWorkLength;
		this.eventEndTime = eventEndTime;
		this.peiXunJia = peiXunJia;
		this.chanJia = chanJia;
		this.peiChanJia = peiChanJia;
		this.tiaoXiu = tiaoXiu;
		this.daKaLocation = daKaLocation;
		this.checkingType = checkingType;
		this.standWorkLength = standWorkLength;
	}
	public PushOaDataEntrty() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
