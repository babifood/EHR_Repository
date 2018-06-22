package com.babifood.clocked.entrty;

import org.springframework.stereotype.Component;

@Component
public class ClockedResultBase {
		// 年度
		private Integer year;
		// 月份
		private Integer month;
		// 工号
		private String workNum;
		// 姓名
		private String memberID;
		// 公司
		private String company;
		// 单位机构
		private String organ;
		// 部门
		private String dept;
		// 科室
		private String office;
		// 班组
		private String group;
		// 岗位
		private String post;
		// 考勤方式
		private String daKaType;
		// 排班类别
		private String paiBanType;
		// 日期
		private java.sql.Date clockDate;
		// 星期
		private String week;
		// 标准上班时间
		private String beginTime;
		// 标准下班时间
		private String endTime;	
		// 标准工作时长
		private Double standWorkTimeLength;
		// 打卡起始时间
		private java.sql.Timestamp clockBeginTime;
		// 打卡结束时间
		private java.sql.Timestamp clockEndTime;
		// 打卡原始时长
		private Double originalCheckingLength;
		// 实际工作时长
		private Double actualWorkLength;		
		// 迟到
		private Integer chiDao;
		// 早退
		private Integer zaoTui;
		// 旷工
		private Integer kuangGong;
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
		private java.sql.Timestamp eventBeginTime;
		// 业务结束时间
		private java.sql.Timestamp eventEndTime;
		// 打卡地点
		private String daKaLocation;
		// 考勤标志
		private Integer clockFlag;
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
		public String getMemberID() {
			return memberID;
		}
		public void setMemberID(String memberID) {
			this.memberID = memberID;
		}
		public String getCompany() {
			return company;
		}
		public void setCompany(String company) {
			this.company = company;
		}
		public String getOrgan() {
			return organ;
		}
		public void setOrgan(String organ) {
			this.organ = organ;
		}
		public String getDept() {
			return dept;
		}
		public void setDept(String dept) {
			this.dept = dept;
		}
		public String getOffice() {
			return office;
		}
		public void setOffice(String office) {
			this.office = office;
		}
		public String getGroup() {
			return group;
		}
		public void setGroup(String group) {
			this.group = group;
		}
		public String getPost() {
			return post;
		}
		public void setPost(String post) {
			this.post = post;
		}
		public String getDaKaType() {
			return daKaType;
		}
		public void setDaKaType(String daKaType) {
			this.daKaType = daKaType;
		}
		public String getPaiBanType() {
			return paiBanType;
		}
		public void setPaiBanType(String paiBanType) {
			this.paiBanType = paiBanType;
		}
		public java.sql.Date getClockDate() {
			return clockDate;
		}
		public void setClockDate(java.sql.Date clockDate) {
			this.clockDate = clockDate;
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
		public Double getStandWorkTimeLength() {
			return standWorkTimeLength;
		}
		public void setStandWorkTimeLength(Double standWorkTimeLength) {
			this.standWorkTimeLength = standWorkTimeLength;
		}
		public java.sql.Timestamp getClockBeginTime() {
			return clockBeginTime;
		}
		public void setClockBeginTime(java.sql.Timestamp clockBeginTime) {
			this.clockBeginTime = clockBeginTime;
		}
		public java.sql.Timestamp getClockEndTime() {
			return clockEndTime;
		}
		public void setClockEndTime(java.sql.Timestamp clockEndTime) {
			this.clockEndTime = clockEndTime;
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
		public Integer getKuangGong() {
			return kuangGong;
		}
		public void setKuangGong(Integer kuangGong) {
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
		public java.sql.Timestamp getEventBeginTime() {
			return eventBeginTime;
		}
		public void setEventBeginTime(java.sql.Timestamp eventBeginTime) {
			this.eventBeginTime = eventBeginTime;
		}
		public java.sql.Timestamp getEventEndTime() {
			return eventEndTime;
		}
		public void setEventEndTime(java.sql.Timestamp eventEndTime) {
			this.eventEndTime = eventEndTime;
		}
		public String getDaKaLocation() {
			return daKaLocation;
		}
		public void setDaKaLocation(String daKaLocation) {
			this.daKaLocation = daKaLocation;
		}
		public Integer getClockFlag() {
			return clockFlag;
		}
		public void setClockFlag(Integer clockFlag) {
			this.clockFlag = clockFlag;
		}
		
}
