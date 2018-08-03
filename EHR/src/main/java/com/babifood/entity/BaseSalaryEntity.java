package com.babifood.entity;

public class BaseSalaryEntity {

	private Integer id;//主键
	
	private String pNumber;//员工工号
	
	private Double baseSalary;//基本工资
	
	private Double fixedOverTimeSalary;//固定加班工资
	
	private Double postSalary;//岗位工资
	
	private Double callSubsidies;//话费补贴
	
	private Double companySalary;//司龄工资
	
	private Double singelMeal;//单个餐补
	
	private Double performanceSalary;//绩效工资
	
	private String createTime;//创建时间
	
	private String useTime;//使用时间
	
	private String isDelete;//逻辑删除  0-有效数据   1-删除数据

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

	public Double getBaseSalary() {
		return baseSalary;
	}

	public void setBaseSalary(Double baseSalary) {
		this.baseSalary = baseSalary;
	}

	public Double getFixedOverTimeSalary() {
		return fixedOverTimeSalary;
	}

	public void setFixedOverTimeSalary(Double fixedOverTimeSalary) {
		this.fixedOverTimeSalary = fixedOverTimeSalary;
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

	public Double getSingelMeal() {
		return singelMeal;
	}

	public void setSingelMeal(Double singelMeal) {
		this.singelMeal = singelMeal;
	}

	public Double getPerformanceSalary() {
		return performanceSalary;
	}

	public void setPerformanceSalary(Double performanceSalary) {
		this.performanceSalary = performanceSalary;
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
