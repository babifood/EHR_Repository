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

	private Double attendanceHours;// 应出勤小时候

	private Double absenceHours;// 缺勤小时数

	private Double baseSalary;// 基本工资

	private Double fixedOvertimeSalary;// 固定加班工资

	private Double postSalary;// 岗位工资

	private Double callSubsidies;// 话费补贴

	private Double companySalary;// 司龄工资

	private Double overSalary;// 加班工资

	private Double riceStick;// 饭贴

	private Double highTem;// 高温津贴

	private Double lowTem;// 低温津贴

	private Double morningShift;// 早班津贴

	private Double nightShift;// 夜班津贴

	private Double stay;// 驻外/住宿津贴

	private Double otherAllowance;// 其他津贴

	private Double security;// 安全奖金

	private Double performanceBonus;// 绩效奖金

	private Double compensatory;// 礼金/补偿金

	private Double otherBonus;// 其他奖金

	private Double addOther;// 加其他

	private Double mealDeduction;// 餐费扣款

	private Double dormDeduction;// 住宿扣款

	private Double beforeDeduction;// 其他扣款（税前）

	private Double insurance;// 社保扣款

	private Double providentFund;// 公积金扣款

	private Double afterDeduction;// 其他扣款（税后）

	private Double laterAndLeaveDeduction;// 迟到和早退扣款

	private Double completionDeduction;// 旷工扣款
	
	private Double yearDeduction;// 年假

	private Double relaxation;// 调休

	private Double thingDeduction;// 事假

	private Double sickDeduction;// 病假

	private Double trainDeduction;// 培训假

	private Double parentalDeduction;// 产假

	private Double marriageDeduction;// 婚假

	private Double companionParentalDeduction;// 陪产假

	private Double funeralDeduction;// 丧假

	private Double onboarding;// 月中入职、离职导致缺勤

	private Double totalDeduction;// 应扣合计

	private Double wagePayable;// 应发工资

	private Double personalTax;// 代缴税金

	private Double realWages;// 实发工资
	
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

	public Double getAttendanceHours() {
		return attendanceHours;
	}

	public void setAttendanceHours(Double attendanceHours) {
		this.attendanceHours = attendanceHours;
	}

	public Double getAbsenceHours() {
		return absenceHours;
	}

	public void setAbsenceHours(Double absenceHours) {
		this.absenceHours = absenceHours;
	}

	public Double getBaseSalary() {
		return baseSalary;
	}

	public void setBaseSalary(Double baseSalary) {
		this.baseSalary = baseSalary;
	}

	public Double getFixedOvertimeSalary() {
		return fixedOvertimeSalary;
	}

	public void setFixedOvertimeSalary(Double fixedOvertimeSalary) {
		this.fixedOvertimeSalary = fixedOvertimeSalary;
	}

	public Double getPostSalary() {
		return postSalary;
	}

	public void setPostSalary(Double postSalary) {
		this.postSalary = postSalary;
	}

	public Double getCallSubsidies() {
		return callSubsidies;
	}

	public void setCallSubsidies(Double callSubsidies) {
		this.callSubsidies = callSubsidies;
	}

	public Double getCompanySalary() {
		return companySalary;
	}

	public void setCompanySalary(Double companySalary) {
		this.companySalary = companySalary;
	}

	public Double getOverSalary() {
		return overSalary;
	}

	public void setOverSalary(Double overSalary) {
		this.overSalary = overSalary;
	}

	public Double getRiceStick() {
		return riceStick;
	}

	public void setRiceStick(Double riceStick) {
		this.riceStick = riceStick;
	}

	public Double getHighTem() {
		return highTem;
	}

	public void setHighTem(Double highTem) {
		this.highTem = highTem;
	}

	public Double getLowTem() {
		return lowTem;
	}

	public void setLowTem(Double lowTem) {
		this.lowTem = lowTem;
	}

	public Double getMorningShift() {
		return morningShift;
	}

	public void setMorningShift(Double morningShift) {
		this.morningShift = morningShift;
	}

	public Double getNightShift() {
		return nightShift;
	}

	public void setNightShift(Double nightShift) {
		this.nightShift = nightShift;
	}

	public Double getStay() {
		return stay;
	}

	public void setStay(Double stay) {
		this.stay = stay;
	}

	public Double getOtherAllowance() {
		return otherAllowance;
	}

	public void setOtherAllowance(Double otherAllowance) {
		this.otherAllowance = otherAllowance;
	}

	public Double getSecurity() {
		return security;
	}

	public void setSecurity(Double security) {
		this.security = security;
	}

	public Double getPerformanceBonus() {
		return performanceBonus;
	}

	public void setPerformanceBonus(Double performanceBonus) {
		this.performanceBonus = performanceBonus;
	}

	public Double getCompensatory() {
		return compensatory;
	}

	public void setCompensatory(Double compensatory) {
		this.compensatory = compensatory;
	}

	public Double getOtherBonus() {
		return otherBonus;
	}

	public void setOtherBonus(Double otherBonus) {
		this.otherBonus = otherBonus;
	}

	public Double getAddOther() {
		return addOther;
	}

	public void setAddOther(Double addOther) {
		this.addOther = addOther;
	}

	public Double getMealDeduction() {
		return mealDeduction;
	}

	public void setMealDeduction(Double mealDeduction) {
		this.mealDeduction = mealDeduction;
	}

	public Double getDormDeduction() {
		return dormDeduction;
	}

	public void setDormDeduction(Double dormDeduction) {
		this.dormDeduction = dormDeduction;
	}

	public Double getBeforeDeduction() {
		return beforeDeduction;
	}

	public void setBeforeDeduction(Double beforeDeduction) {
		this.beforeDeduction = beforeDeduction;
	}

	public Double getInsurance() {
		return insurance;
	}

	public void setInsurance(Double insurance) {
		this.insurance = insurance;
	}

	public Double getProvidentFund() {
		return providentFund;
	}

	public void setProvidentFund(Double providentFund) {
		this.providentFund = providentFund;
	}

	public Double getAfterDeduction() {
		return afterDeduction;
	}

	public void setAfterDeduction(Double afterDeduction) {
		this.afterDeduction = afterDeduction;
	}

	public Double getLaterAndLeaveDeduction() {
		return laterAndLeaveDeduction;
	}

	public void setLaterAndLeaveDeduction(Double laterAndLeaveDeduction) {
		this.laterAndLeaveDeduction = laterAndLeaveDeduction;
	}

	public Double getCompletionDeduction() {
		return completionDeduction;
	}

	public void setCompletionDeduction(Double completionDeduction) {
		this.completionDeduction = completionDeduction;
	}

	public Double getYearDeduction() {
		return yearDeduction;
	}

	public void setYearDeduction(Double yearDeduction) {
		this.yearDeduction = yearDeduction;
	}

	public Double getRelaxation() {
		return relaxation;
	}

	public void setRelaxation(Double relaxation) {
		this.relaxation = relaxation;
	}

	public Double getThingDeduction() {
		return thingDeduction;
	}

	public void setThingDeduction(Double thingDeduction) {
		this.thingDeduction = thingDeduction;
	}

	public Double getSickDeduction() {
		return sickDeduction;
	}

	public void setSickDeduction(Double sickDeduction) {
		this.sickDeduction = sickDeduction;
	}

	public Double getTrainDeduction() {
		return trainDeduction;
	}

	public void setTrainDeduction(Double trainDeduction) {
		this.trainDeduction = trainDeduction;
	}

	public Double getParentalDeduction() {
		return parentalDeduction;
	}

	public void setParentalDeduction(Double parentalDeduction) {
		this.parentalDeduction = parentalDeduction;
	}

	public Double getMarriageDeduction() {
		return marriageDeduction;
	}

	public void setMarriageDeduction(Double marriageDeduction) {
		this.marriageDeduction = marriageDeduction;
	}

	public Double getCompanionParentalDeduction() {
		return companionParentalDeduction;
	}

	public void setCompanionParentalDeduction(Double companionParentalDeduction) {
		this.companionParentalDeduction = companionParentalDeduction;
	}

	public Double getFuneralDeduction() {
		return funeralDeduction;
	}

	public void setFuneralDeduction(Double funeralDeduction) {
		this.funeralDeduction = funeralDeduction;
	}

	public Double getOnboarding() {
		return onboarding;
	}

	public void setOnboarding(Double onboarding) {
		this.onboarding = onboarding;
	}

	public Double getTotalDeduction() {
		return totalDeduction;
	}

	public void setTotalDeduction(Double totalDeduction) {
		this.totalDeduction = totalDeduction;
	}

	public Double getWagePayable() {
		return wagePayable;
	}

	public void setWagePayable(Double wagePayable) {
		this.wagePayable = wagePayable;
	}

	public Double getPersonalTax() {
		return personalTax;
	}

	public void setPersonalTax(Double personalTax) {
		this.personalTax = personalTax;
	}

	public Double getRealWages() {
		return realWages;
	}

	public void setRealWages(Double realWages) {
		this.realWages = realWages;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
