package com.babifood.dao;

import java.util.List;
import java.util.Map;

import com.babifood.entity.DeptEntity;

public interface DeptPageDao {
	
	public List<Map<String,Object>> findOrganizeList(String deptCode);
	
	public Map<String,Object> findOrganizeByDeptCode(String deptCode);

	public void insertDept(DeptEntity deptEntity) throws Exception;

	public Integer updateDept(DeptEntity deptEntity);

	public void deleteDept(String id);

	public Map<String, Object> findDeptByDeptCode(String id);

	public List<Map<String, Object>> findAllDepts();

	public List<Map<String, Object>> findAll();

	public Map<String, Object> queryCountByDeptCode(String deptCode);

	public List<Map<String, Object>> findDeptsByDeptCodes(String[] deptCodeList);

}
