package com.babifood.entity;

public class SalaryDetailEntity {

	private Integer id;// 主键

	private String pNumber;// 员工工号

	private String year;// 年

	private String month;// 月

	private String companyCode;// 公司编号

	private String companyName;// 公司名称

	private String organizationCode;// 单位机构编号

	private String organizationName;// 单位机构名称

	private String deptCode;// 部门编号

	private String deptName;// 部门名称

	private String officeCode;// 科室编号

	private String officeName;// 科室名称

	private String groupCode;// 班组编号

	private String groupName;// 班组名称

	private String post;// 岗位

	private String postName;// 岗位名称

	private String attendanceHours;// 应出勤小时候

	private String absenceHours;// 缺勤小时数

	private String baseSalary;// 基本工资

	private String fixedOvertimeSalary;// 固定加班工资

	private String postSalary;// 岗位工资

	private String callSubsidies;// 话费补贴

	private String companySalary;// 司龄工资

	private String overSalary;// 加班工资

	private String riceStick;// 饭贴

	private String highTem;// 高温津贴

	private String lowTem;// 低温津贴

	private String morningShift;// 早班津贴

	private String nightShift;// 夜班津贴

	private String stay;// 驻外/住宿津贴

	private String otherAllowance;// 其他津贴

	private String security;// 安全奖金

	private String performanceBonus;// 绩效奖金

	private String compensatory;// 礼金/补偿金

	private String otherBonus;// 其他奖金

	private String addOther;// 加其他

	private String mealDeduction;// 餐费扣款

	private String dormDeduction;// 住宿扣款

	private String beforeDeduction;// 其他扣款（税前）

	private String insurance;// 社保扣款

	private String providentFund;// 公积金扣款

	private String afterDeduction;// 其他扣款（税后）

	private String laterAndLeaveDeduction;// 迟到和早退扣款

	private String completionDeduction;// 旷工扣款
	
	private String yearDeduction;// 年假

	private String relaxation;// 调休

	private String thingDeduction;// 事假

	private String sickDeduction;// 病假

	private String trainDeduction;// 培训假

	private String parentalDeduction;// 产假

	private String marriageDeduction;// 婚假

	private String companionParentalDeduction;// 陪产假

	private String funeralDeduction;// 丧假

	private String onboarding;// 月中入职、离职导致缺勤

	private String totalDeduction;// 应扣合计

	private String wagePayable;// 应发工资

	private String personalTax;// 代缴税金

	private String realWages;// 实发工资
	
	private String status;//计算薪资状态  1-计算   2-核算  3-归档

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

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getOfficeCode() {
		return officeCode;
	}

	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
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

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	public String getAttendanceHours() {
		return attendanceHours;
	}

	public void setAttendanceHours(String attendanceHours) {
		this.attendanceHours = attendanceHours;
	}

	public String getAbsenceHours() {
		return absenceHours;
	}

	public void setAbsenceHours(String absenceHours) {
		this.absenceHours = absenceHours;
	}

	public String getBaseSalary() {
		return baseSalary;
	}

	public void setBaseSalary(String baseSalary) {
		this.baseSalary = baseSalary;
	}

	public String getFixedOvertimeSalary() {
		return fixedOvertimeSalary;
	}

	public void setFixedOvertimeSalary(String fixedOvertimeSalary) {
		this.fixedOvertimeSalary = fixedOvertimeSalary;
	}

	public String getPostSalary() {
		return postSalary;
	}

	public void setPostSalary(String postSalary) {
		this.postSalary = postSalary;
	}

	public String getCallSubsidies() {
		return callSubsidies;
	}

	public void setCallSubsidies(String callSubsidies) {
		this.callSubsidies = callSubsidies;
	}

	public String getCompanySalary() {
		return companySalary;
	}

	public void setCompanySalary(String companySalary) {
		this.companySalary = companySalary;
	}

	public String getOverSalary() {
		return overSalary;
	}

	public void setOverSalary(String overSalary) {
		this.overSalary = overSalary;
	}

	public String getRiceStick() {
		return riceStick;
	}

	public void setRiceStick(String riceStick) {
		this.riceStick = riceStick;
	}

	public String getHighTem() {
		return highTem;
	}

	public void setHighTem(String highTem) {
		this.highTem = highTem;
	}

	public String getLowTem() {
		return lowTem;
	}

	public void setLowTem(String lowTem) {
		this.lowTem = lowTem;
	}

	public String getMorningShift() {
		return morningShift;
	}

	public void setMorningShift(String morningShift) {
		this.morningShift = morningShift;
	}

	public String getNightShift() {
		return nightShift;
	}

	public void setNightShift(String nightShift) {
		this.nightShift = nightShift;
	}

	public String getStay() {
		return stay;
	}

	public void setStay(String stay) {
		this.stay = stay;
	}

	public String getOtherAllowance() {
		return otherAllowance;
	}

	public void setOtherAllowance(String otherAllowance) {
		this.otherAllowance = otherAllowance;
	}

	public String getSecurity() {
		return security;
	}

	public void setSecurity(String security) {
		this.security = security;
	}

	public String getPerformanceBonus() {
		return performanceBonus;
	}

	public void setPerformanceBonus(String performanceBonus) {
		this.performanceBonus = performanceBonus;
	}

	public String getCompensatory() {
		return compensatory;
	}

	public void setCompensatory(String compensatory) {
		this.compensatory = compensatory;
	}

	public String getOtherBonus() {
		return otherBonus;
	}

	public void setOtherBonus(String otherBonus) {
		this.otherBonus = otherBonus;
	}

	public String getAddOther() {
		return addOther;
	}

	public void setAddOther(String addOther) {
		this.addOther = addOther;
	}

	public String getMealDeduction() {
		return mealDeduction;
	}

	public void setMealDeduction(String mealDeduction) {
		this.mealDeduction = mealDeduction;
	}

	public String getDormDeduction() {
		return dormDeduction;
	}

	public void setDormDeduction(String dormDeduction) {
		this.dormDeduction = dormDeduction;
	}

	public String getBeforeDeduction() {
		return beforeDeduction;
	}

	public void setBeforeDeduction(String beforeDeduction) {
		this.beforeDeduction = beforeDeduction;
	}

	public String getInsurance() {
		return insurance;
	}

	public void setInsurance(String insurance) {
		this.insurance = insurance;
	}

	public String getProvidentFund() {
		return providentFund;
	}

	public void setProvidentFund(String providentFund) {
		this.providentFund = providentFund;
	}

	public String getAfterDeduction() {
		return afterDeduction;
	}

	public void setAfterDeduction(String afterDeduction) {
		this.afterDeduction = afterDeduction;
	}

	public String getLaterAndLeaveDeduction() {
		return laterAndLeaveDeduction;
	}

	public void setLaterAndLeaveDeduction(String laterAndLeaveDeduction) {
		this.laterAndLeaveDeduction = laterAndLeaveDeduction;
	}

	public String getCompletionDeduction() {
		return completionDeduction;
	}

	public void setCompletionDeduction(String completionDeduction) {
		this.completionDeduction = completionDeduction;
	}

	public String getYearDeduction() {
		return yearDeduction;
	}

	public void setYearDeduction(String yearDeduction) {
		this.yearDeduction = yearDeduction;
	}

	public String getRelaxation() {
		return relaxation;
	}

	public void setRelaxation(String relaxation) {
		this.relaxation = relaxation;
	}

	public String getThingDeduction() {
		return thingDeduction;
	}

	public void setThingDeduction(String thingDeduction) {
		this.thingDeduction = thingDeduction;
	}

	public String getSickDeduction() {
		return sickDeduction;
	}

	public void setSickDeduction(String sickDeduction) {
		this.sickDeduction = sickDeduction;
	}

	public String getTrainDeduction() {
		return trainDeduction;
	}

	public void setTrainDeduction(String trainDeduction) {
		this.trainDeduction = trainDeduction;
	}

	public String getParentalDeduction() {
		return parentalDeduction;
	}

	public void setParentalDeduction(String parentalDeduction) {
		this.parentalDeduction = parentalDeduction;
	}

	public String getMarriageDeduction() {
		return marriageDeduction;
	}

	public void setMarriageDeduction(String marriageDeduction) {
		this.marriageDeduction = marriageDeduction;
	}

	public String getCompanionParentalDeduction() {
		return companionParentalDeduction;
	}

	public void setCompanionParentalDeduction(String companionParentalDeduction) {
		this.companionParentalDeduction = companionParentalDeduction;
	}

	public String getFuneralDeduction() {
		return funeralDeduction;
	}

	public void setFuneralDeduction(String funeralDeduction) {
		this.funeralDeduction = funeralDeduction;
	}

	public String getOnboarding() {
		return onboarding;
	}

	public void setOnboarding(String onboarding) {
		this.onboarding = onboarding;
	}

	public String getTotalDeduction() {
		return totalDeduction;
	}

	public void setTotalDeduction(String totalDeduction) {
		this.totalDeduction = totalDeduction;
	}

	public String getWagePayable() {
		return wagePayable;
	}

	public void setWagePayable(String wagePayable) {
		this.wagePayable = wagePayable;
	}

	public String getPersonalTax() {
		return personalTax;
	}

	public void setPersonalTax(String personalTax) {
		this.personalTax = personalTax;
	}

	public String getRealWages() {
		return realWages;
	}

	public void setRealWages(String realWages) {
		this.realWages = realWages;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
