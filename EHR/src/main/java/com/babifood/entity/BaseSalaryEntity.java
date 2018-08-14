package com.babifood.entity;

public class BaseSalaryEntity {

	private Integer id;// 主键

	private String pNumber;// 员工工号

	private String baseSalary;// 基本工资

	private String fixedOverTimeSalary;// 固定加班工资

	private String postSalary;// 岗位工资

	private String callSubsidies;// 话费补贴

	private String companySalary;// 司龄工资

	private String singelMeal;// 单个餐补

	private String performanceSalary;// 绩效工资
	
	private String stay;//住宿补贴

	private String workType;// 工作类型

	private String createTime;// 创建时间

	private String useTime;// 使用时间

	private String isDelete;// 逻辑删除 0-有效数据 1-删除数据

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

	public String getBaseSalary() {
		return baseSalary;
	}

	public void setBaseSalary(String baseSalary) {
		this.baseSalary = baseSalary;
	}

	public String getFixedOverTimeSalary() {
		return fixedOverTimeSalary;
	}

	public void setFixedOverTimeSalary(String fixedOverTimeSalary) {
		this.fixedOverTimeSalary = fixedOverTimeSalary;
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

	public String getSingelMeal() {
		return singelMeal;
	}

	public void setSingelMeal(String singelMeal) {
		this.singelMeal = singelMeal;
	}

	public String getPerformanceSalary() {
		return performanceSalary;
	}

	public void setPerformanceSalary(String performanceSalary) {
		this.performanceSalary = performanceSalary;
	}

	public String getStay() {
		return stay;
	}

	public void setStay(String stay) {
		this.stay = stay;
	}

	public String getWorkType() {
		return workType;
	}

	public void setWorkType(String workType) {
		this.workType = workType;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUseTime() {
		return useTime;
	}

	public void setUseTime(String useTime) {
		this.useTime = useTime;
	}

	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

}
