package com.babifood.service;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import com.babifood.entity.DeptEntity;

public interface DeptPageService {

	Map<String,Object> findOrganization(String id);

	Map<String, Object> addDept(DeptEntity deptEntity);

	Map<String, Object> updateDept(DeptEntity deptEntity);

	Map<String, Object> deleteDept(String id);

	Map<String, Object> findDeptByDeptCode(String id);

	List<Map<String, Object>> findAll();

	void exportExcel(OutputStream ouputStream,String type) throws Exception;

	Map<String, Object> queryCountByDeptCode(String deptCode);
	
}
