package com.babifood.clocked.entrty;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.babifood.clocked.rule.WorkHourRule;
import com.babifood.utils.UtilDateTime;
@Component
public class ClockedResultBases {
	// 年度
	private Integer year;
	// 月份
	private Integer month;
	// 工号
	private String workNum;
	// 姓名
	private String userName;
	// 公司代码
	private String companyCode;
	// 公司
	private String company;
	// 单位机构代码
	private String organCode;
	// 单位机构
	private String organ;
	// 部门代码
	private String deptCode;
	// 部门
	private String dept;
	// 科室代码
	private String officeCode;
	// 科室代码
	private String office;
	// 班组代码
	private String groupCode;
	// 班组
	private String groupName;
	// 岗位代码
	private String postCode;
	// 岗位
	private String post;
	// 考勤方式
	private String checkingType;
	// 排班类别
	private String paiBanType;
	// 日期
	private Date checkingDate;
	// 星期
	private String week;
	// 标准上班时间
	private String beginTime;
	// 标准下班时间
	private String endTime;	
	// 标准工作时长
	private Double standWorkLength;
	// 打卡起始时间
	private Date checkingBeginTime;
	// 打卡结束时间
	private Date checkingEndTime;
	// 打卡原始时长
	private Double originalCheckingLength;
	// 实际工作时长
	private Double actualWorkLength;		
	// 迟到
	private Integer chiDao;
	// 早退
	private Integer zaoTui;
	// 旷工
	private Double kuangGong;
	// 年假
	private Double nianJia;
	// 调休
	private Double tiaoXiu;
	// 事假
	private Double shiJia;
	// 病假
	private Double bingJia;
	// 培训假
	private Double peiXunJia;
	// 婚假
	private Double hunJia;
	// 产假
	private Double chanJia;
	// 陪产假
	private Double peiChanJia;
	// 丧假
	private Double sangJia;
	// 其它请假
	private Double otherQingJia;		
	// 缺勤小时数
	private Double queQin;
	// 请假小时数
	private Double qingJia;
	// 异动小时数
	private Double yiDong;
	// 加班小时数
	private Double jiaBan;
	// 出差小时数
	private Double chuCha;
	// 餐补个数
	private Integer canBu;
	
	
	// 业务开始时间
	private Date eventBeginTime;
	// 业务结束时间
	private Date eventEndTime;
	// 考勤标志
	private Integer clockFlag;
	
	//入离职标志
	private Integer inOutJob;
	// 打卡地点
	private String daKaLocation;
	
	//标注上班时间yyyy-MM-dd HH:mm:ss
	private Date standBeginTime;
	//标注下班时间yyyy-MM-dd HH:mm:ss
	private Date standEndTime;
	// 中间休息时段
//	Date xiuBeginTime = null;
//
//	Date xiuEndTime = null;
	// 最终开始计时时间
	private Date finalBeginTime;
	// 最终结束计时时间
	private Date finalEndTime;	
	
	public Date getFinalBeginTime() {
		finalBeginTime = getCheckingBeginTime()==null?getEventBeginTime():getCheckingBeginTime();
		if (getCheckingBeginTime() != null && getEventBeginTime() != null) {
			if (getCheckingBeginTime().after(getEventBeginTime())) {
				finalBeginTime = getEventBeginTime();
			}
		}
		return finalBeginTime;
	}
	public void setFinalBeginTime(Date finalBeginTime) {
		this.finalBeginTime = finalBeginTime;
	}
	public Date getFinalEndTime() {
		finalEndTime = getCheckingEndTime()==null?getEventEndTime():getCheckingEndTime();
		if (getCheckingEndTime() != null && getEventEndTime() != null) {
			if (getCheckingEndTime().after(getEventEndTime())) {
				finalEndTime = getEventEndTime();
			}
		}
		return finalEndTime;
	}
	public void setFinalEndTime(Date finalEndTime) {
		this.finalEndTime = finalEndTime;
	}
	public Date getStandBeginTime()  {
		if(standBeginTime==null){
			standBeginTime = WorkHourRule.getStandBeginTime(this.getCheckingDate(), this.getBeginTime());
		}
		return standBeginTime;
	}
	public void setStandBeginTime(Date standBeginTime) {
		this.standBeginTime = standBeginTime;
	}
	public Date getStandEndTime(){
		if (standEndTime == null) {
			standEndTime = WorkHourRule.getStandEndTime(this.getCheckingDate(), this.getEndTime(),this.getBeginTime());
		}
		return standEndTime;
	}
	public void setStandEndTime(Date standEndTime) {
		this.standEndTime = standEndTime;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	public String getWorkNum() {
		return workNum;
	}
	public void setWorkNum(String workNum) {
		this.workNum = workNum;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getOrganCode() {
		return organCode;
	}
	public void setOrganCode(String organCode) {
		this.organCode = organCode;
	}
	public String getOrgan() {
		return organ;
	}
	public void setOrgan(String organ) {
		this.organ = organ;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getOfficeCode() {
		return officeCode;
	}
	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}
	public String getOffice() {
		return office;
	}
	public void setOffice(String office) {
		this.office = office;
	}
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public String getCheckingType() {
		return checkingType;
	}
	public void setCheckingType(String checkingType) {
		this.checkingType = checkingType;
	}
	public String getPaiBanType() {
		return paiBanType;
	}
	public void setPaiBanType(String paiBanType) {
		this.paiBanType = paiBanType;
	}
	public Date getCheckingDate() {
		return checkingDate;
	}
	public void setCheckingDate(Date checkingDate) {
		this.checkingDate = checkingDate;
	}
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
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
	public Double getStandWorkLength() {
		return standWorkLength;
	}
	public void setStandWorkLength(Double standWorkLength) {
		this.standWorkLength = standWorkLength;
	}
	public Date getCheckingBeginTime() {
		return checkingBeginTime;
	}
	public void setCheckingBeginTime(Date checkingBeginTime) {
		this.checkingBeginTime = checkingBeginTime;
	}
	public Date getCheckingEndTime() {
		return checkingEndTime;
	}
	public void setCheckingEndTime(Date checkingEndTime) {
		this.checkingEndTime = checkingEndTime;
	}
	public Double getOriginalCheckingLength() {
		return originalCheckingLength;
	}
	public void setOriginalCheckingLength(Double originalCheckingLength) {
		this.originalCheckingLength = originalCheckingLength;
	}
	public Double getActualWorkLength() {
		return actualWorkLength;
	}
	public void setActualWorkLength(Double actualWorkLength) {
		this.actualWorkLength = actualWorkLength;
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
	public Double getKuangGong() {
		return kuangGong;
	}
	public void setKuangGong(Double kuangGong) {
		this.kuangGong = kuangGong;
	}
	public Double getNianJia() {
		return nianJia;
	}
	public void setNianJia(Double nianJia) {
		this.nianJia = nianJia;
	}
	public Double getTiaoXiu() {
		return tiaoXiu;
	}
	public void setTiaoXiu(Double tiaoXiu) {
		this.tiaoXiu = tiaoXiu;
	}
	public Double getShiJia() {
		return shiJia;
	}
	public void setShiJia(Double shiJia) {
		this.shiJia = shiJia;
	}
	public Double getBingJia() {
		return bingJia;
	}
	public void setBingJia(Double bingJia) {
		this.bingJia = bingJia;
	}
	public Double getPeiXunJia() {
		return peiXunJia;
	}
	public void setPeiXunJia(Double peiXunJia) {
		this.peiXunJia = peiXunJia;
	}
	public Double getHunJia() {
		return hunJia;
	}
	public void setHunJia(Double hunJia) {
		this.hunJia = hunJia;
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
	public Double getSangJia() {
		return sangJia;
	}
	public void setSangJia(Double sangJia) {
		this.sangJia = sangJia;
	}
	public Double getOtherQingJia() {
		return otherQingJia;
	}
	public void setOtherQingJia(Double otherQingJia) {
		this.otherQingJia = otherQingJia;
	}
	public Double getQueQin() {
		return queQin;
	}
	public void setQueQin(Double queQin) {
		this.queQin = queQin;
	}
	public Double getQingJia() {
		return qingJia;
	}
	public void setQingJia(Double qingJia) {
		this.qingJia = qingJia;
	}
	public Double getYiDong() {
		return yiDong;
	}
	public void setYiDong(Double yiDong) {
		this.yiDong = yiDong;
	}
	public Double getJiaBan() {
		return jiaBan;
	}
	public void setJiaBan(Double jiaBan) {
		this.jiaBan = jiaBan;
	}
	public Double getChuCha() {
		return chuCha;
	}
	public void setChuCha(Double chuCha) {
		this.chuCha = chuCha;
	}
	public Integer getCanBu() {
		return canBu;
	}
	public void setCanBu(Integer canBu) {
		this.canBu = canBu;
	}
	public Date getEventBeginTime() {
		return eventBeginTime;
	}
	public void setEventBeginTime(Date eventBeginTime) {
		this.eventBeginTime = eventBeginTime;
	}
	public Date getEventEndTime() {
		return eventEndTime;
	}
	public void setEventEndTime(Date eventEndTime) {
		this.eventEndTime = eventEndTime;
	}
	public Integer getClockFlag() {
		return clockFlag;
	}
	public void setClockFlag(Integer clockFlag) {
		this.clockFlag = clockFlag;
	}
	public String getDaKaLocation() {
		return daKaLocation;
	}
	public void setDaKaLocation(String daKaLocation) {
		this.daKaLocation = daKaLocation;
	}
	// 考勤标准的结束时间是否需要延时1天
	public boolean isDelayOneDay() {
		if (getStandEndTime() == null || getStandBeginTime() == null) {
			return false;
		}
		return UtilDateTime.getDaysBetween(getStandBeginTime(), getStandEndTime()) == 1;
	}
	public Integer getInOutJob() {
		return inOutJob;
	}
	public void setInOutJob(Integer inOutJob) {
		this.inOutJob = inOutJob;
	}
	
//	public Date getXiuBeginTime() throws Exception {
//		return xiuBeginTime;
//	}
//	public void setXiuBeginTime(Date xiuBeginTime) {
//		this.xiuBeginTime = xiuBeginTime;
//	}
//	public Date getXiuEndTime() {
//		return xiuEndTime;
//	}
//	public void setXiuEndTime(Date xiuEndTime) {
//		this.xiuEndTime = xiuEndTime;
//	}	
//	private void checkXiuXiTime() throws Exception {
//		SimpleDateFormat dftime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String theWorkBeginTimeStr = this.getBeginTime();
//		String theWorkEndTimeStr = this.getEndTime();
//		String dateString = this.getCheckingDate().toString();
//		//int standWorkTime[] = UtilDateTime.getTimeInterval(standBeginTime, standEndTime);
//		// 标准工作时长计算》8小时，应该有中间休息时段，如果没有，抛出异常
//		// if (standWorkTime[1] >= 6) {
//		if (theWorkBeginTimeStr.indexOf("8:30") != -1 && theWorkEndTimeStr.indexOf("17:30") != -1) {
//			xiuBeginTime = dftime.parse(dateString+" 12:00:00");
//			xiuEndTime = dftime.parse(dateString + " 13:00:00.0");
//		} else if (theWorkBeginTimeStr.indexOf("9:00") != -1 && theWorkEndTimeStr.indexOf("18:00") != -1) {
//			xiuBeginTime = dftime.parse(dateString + " 12:00:00.0");
//			xiuEndTime = dftime.parse(dateString + " 13:00:00.0");
//		} else if (theWorkBeginTimeStr.indexOf("9:00") != -1 && theWorkEndTimeStr.indexOf("16:00") != -1) {
//			xiuBeginTime = Timestamp.valueOf(dateString + " 12:00:00.0");
//			xiuEndTime = Timestamp.valueOf(dateString + " 13:00:00.0");
//		} 
////		if (xiuBeginTime == null || xiuEndTime == null) {
////			// throw new IllegalArgumentException("上/下班作业时间不支持,[" +
////			// theWorkBeginTimeStr + "-" + theWorkEndTimeStr +
////			// "],无法获取中间休息时段数据!!!");
////			System.out.println("上/下班作业时间不支持,[" + theWorkBeginTimeStr + "-" + theWorkEndTimeStr + "],无法获取中间休息时段数据!!!");
////		}
//		// }
//	}
}
