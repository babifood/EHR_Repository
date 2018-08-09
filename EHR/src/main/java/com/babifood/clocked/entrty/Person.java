package com.babifood.clocked.entrty;

import org.springframework.stereotype.Component;

/**
 * 考勤人员信息
 * @author BABIFOOD
 *
 */
@Component
public class Person {
	private String p_id;
	//员工编号
	private String workNum;
	//用工名称
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
	// 科室
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
	private String daKaType;
	//入职日期
	private String inDate;
	//转正日期
	private String turnDate;
	//离职日期
	private String outDate;
	
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
	public String getDaKaType() {
		return daKaType;
	}
	public void setDaKaType(String daKaType) {
		this.daKaType = daKaType;
	}
	public String getTurnDate() {
		return turnDate;
	}
	public void setTurnDate(String turnDate) {
		this.turnDate = turnDate;
	}
	public String getOutDate() {
		return outDate;
	}
	public void setOutDate(String outDate) {
		this.outDate = outDate;
	}
	public String getInDate() {
		return inDate;
	}
	public void setInDate(String inDate) {
		this.inDate = inDate;
	}
	public String getP_id() {
		return p_id;
	}
	public void setP_id(String p_id) {
		this.p_id = p_id;
	}
	
	
}
