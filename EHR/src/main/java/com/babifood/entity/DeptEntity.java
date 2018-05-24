package com.babifood.entity;

import java.util.Date;

public class DeptEntity {

	private String id;//主键
	
	private String deptName;//部门名称
	
	private String deptCode;//部门代码
	
	private String pCode;//父类代码
	
	private String type;//部门类型
	
	private String source_type;//数据来源类型
	
	private String remark;//备注
	
	private Date createTime;//创建时间
	
	private Date updateTime;//更新时间
	
	public DeptEntity() {
		super();
	}

	public DeptEntity(String id, String deptName, String deptCode, String pCode, String type, String source_type, String remark,
			Date createTime, Date updateTime) {
		super();
		this.id = id;
		this.deptName = deptName;
		this.deptCode = deptCode;
		this.pCode = pCode;
		this.type = type;
		this.source_type = source_type;
		this.remark = remark;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getpCode() {
		return pCode;
	}

	public void setpCode(String pCode) {
		this.pCode = pCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSource_type() {
		return source_type;
	}

	public void setSource_type(String source_type) {
		this.source_type = source_type;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
}
