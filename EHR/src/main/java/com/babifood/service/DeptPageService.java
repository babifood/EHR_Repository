package com.babifood.service;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import com.babifood.entity.DeptEntity;

public interface DeptPageService {

	public Map<String,Object> findOrganization(String id);

	public Map<String, Object> addDept(DeptEntity deptEntity);

	public Map<String, Object> updateDept(DeptEntity deptEntity);

	public Map<String, Object> deleteDept(String id);

	public Map<String, Object> findDeptByDeptCode(String id);

	public List<Map<String, Object>> findAll();

	public void exportExcel(OutputStream ouputStream,String type) throws Exception;

	public Map<String, Object> queryCountByDeptCode(String deptCode);

}
