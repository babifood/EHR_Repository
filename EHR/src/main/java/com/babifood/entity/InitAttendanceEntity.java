package com.babifood.entity;

public class InitAttendanceEntity {

	private Integer id;//主键
	
	private String year;//年
	
	private String month;//月
	
	private String date;//日期
	
	private String pNumber;//员工工号
	
	private String pName;//员工姓名
	
	private String companyCode;//公司编号
	
	private String organizationCode;//单位机构编号
	
	private String deptCode;//部门编号
	
	private String officeCode;//科室编号
	
	private String groupCode;//班组编号
	
	private String post;//岗位
	
	private String punchType;//打卡类型
	
	private String arrangementType;//排班类别（大小周制or5.5天工作制or6天工作制）
	
	private String weekDay;//星期
	
	private String normalStartTime;//标准上班时间
	
	private String normalEndTime;//标准下班时间
	
	private String normalTime;//标准工作时长
	
	private String punchStartTime;//打卡开始时间
	
	private String punchEndTime;//打卡结束时间
	
	private String originalTime;//打卡原始时长（考勤机起止时间与当日标准上班起止时间交集时间）

	private String workTime;//工作时长（当日标准上班时长-标准上下班班时间内请假时长-旷工时长）
	
	private String late;//迟到
	
	private String leave;//早退
	
	private String completion;//旷工
	
	private String absenceHours;//缺勤小时数
	
	private String vacationHours;//请假小时数
	
	private String yearVacation;//年假
	
	private String relaxation;//调休
	
	private String thingVacation;//事假
	
	private String sickVacation;//病假
	
	private String trainVacation;//培训假
	
	private String parentalVacation;//产假
	
	private String marriageVacation;//婚假
	
	private String companionParentalVacation;//陪产假
	
	private String funeralVacation;//丧假
	
	private String unusualHours;//异动小时数
	
	private String overtimeHours;//加班小时数
	
	private String travelHours;//出差小时数
	
	private String mealSupplement;//餐补
	
	private String createTime;//创建时间
	
	private String updateTime;//修改时间
	
	private String isAttend;//是否考勤  1-是  0-否

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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getpNumber() {
		return pNumber;
	}

	public void setpNumber(String pNumber) {
		this.pNumber = pNumber;
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getOfficeCode() {
		return officeCode;
	}

	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getPunchType() {
		return punchType;
	}

	public void setPunchType(String punchType) {
		this.punchType = punchType;
	}

	public String getArrangementType() {
		return arrangementType;
	}

	public void setArrangementType(String arrangementType) {
		this.arrangementType = arrangementType;
	}

	public String getWeekDay() {
		return weekDay;
	}

	public void setWeekDay(String weekDay) {
		this.weekDay = weekDay;
	}

	public String getNormalStartTime() {
		return normalStartTime;
	}

	public void setNormalStartTime(String normalStartTime) {
		this.normalStartTime = normalStartTime;
	}

	public String getNormalEndTime() {
		return normalEndTime;
	}

	public void setNormalEndTime(String normalEndTime) {
		this.normalEndTime = normalEndTime;
	}

	public String getNormalTime() {
		return normalTime;
	}

	public void setNormalTime(String normalTime) {
		this.normalTime = normalTime;
	}

	public String getPunchStartTime() {
		return punchStartTime;
	}

	public void setPunchStartTime(String punchStartTime) {
		this.punchStartTime = punchStartTime;
	}

	public String getPunchEndTime() {
		return punchEndTime;
	}

	public void setPunchEndTime(String punchEndTime) {
		this.punchEndTime = punchEndTime;
	}

	public String getOriginalTime() {
		return originalTime;
	}

	public void setOriginalTime(String originalTime) {
		this.originalTime = originalTime;
	}

	public String getWorkTime() {
		return workTime;
	}

	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}

	public String getLate() {
		return late;
	}

	public void setLate(String late) {
		this.late = late;
	}

	public String getLeave() {
		return leave;
	}

	public void setLeave(String leave) {
		this.leave = leave;
	}

	public String getCompletion() {
		return completion;
	}

	public void setCompletion(String completion) {
		this.completion = completion;
	}

	public String getAbsenceHours() {
		return absenceHours;
	}

	public void setAbsenceHours(String absenceHours) {
		this.absenceHours = absenceHours;
	}

	public String getVacationHours() {
		return vacationHours;
	}

	public void setVacationHours(String vacationHours) {
		this.vacationHours = vacationHours;
	}

	public String getYearVacation() {
		return yearVacation;
	}

	public void setYearVacation(String yearVacation) {
		this.yearVacation = yearVacation;
	}

	public String getRelaxation() {
		return relaxation;
	}

	public void setRelaxation(String relaxation) {
		this.relaxation = relaxation;
	}

	public String getThingVacation() {
		return thingVacation;
	}

	public void setThingVacation(String thingVacation) {
		this.thingVacation = thingVacation;
	}

	public String getSickVacation() {
		return sickVacation;
	}

	public void setSickVacation(String sickVacation) {
		this.sickVacation = sickVacation;
	}

	public String getTrainVacation() {
		return trainVacation;
	}

	public void setTrainVacation(String trainVacation) {
		this.trainVacation = trainVacation;
	}

	public String getParentalVacation() {
		return parentalVacation;
	}

	public void setParentalVacation(String parentalVacation) {
		this.parentalVacation = parentalVacation;
	}

	public String getMarriageVacation() {
		return marriageVacation;
	}

	public void setMarriageVacation(String marriageVacation) {
		this.marriageVacation = marriageVacation;
	}

	public String getCompanionParentalVacation() {
		return companionParentalVacation;
	}

	public void setCompanionParentalVacation(String companionParentalVacation) {
		this.companionParentalVacation = companionParentalVacation;
	}

	public String getFuneralVacation() {
		return funeralVacation;
	}

	public void setFuneralVacation(String funeralVacation) {
		this.funeralVacation = funeralVacation;
	}

	public String getUnusualHours() {
		return unusualHours;
	}

	public void setUnusualHours(String unusualHours) {
		this.unusualHours = unusualHours;
	}

	public String getOvertimeHours() {
		return overtimeHours;
	}

	public void setOvertimeHours(String overtimeHours) {
		this.overtimeHours = overtimeHours;
	}

	public String getTravelHours() {
		return travelHours;
	}

	public void setTravelHours(String travelHours) {
		this.travelHours = travelHours;
	}

	public String getMealSupplement() {
		return mealSupplement;
	}

	public void setMealSupplement(String mealSupplement) {
		this.mealSupplement = mealSupplement;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getIsAttend() {
		return isAttend;
	}

	public void setIsAttend(String isAttend) {
		this.isAttend = isAttend;
	}
	
}
