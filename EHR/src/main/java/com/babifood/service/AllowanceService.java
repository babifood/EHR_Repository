package com.babifood.service;

import java.io.OutputStream;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface AllowanceService {

	public Map<String, Object> findEmployAllowance(String year, String month, String pNumber);

	public Map<String, Object> importExcel(MultipartFile file, String type);

	public void exportExcel(OutputStream ouputStream, String type) throws Exception;

	public Map<String, Object> getPageAllowanceList(Integer page, Integer rows, String pNumber, String pName,
			String organzationName, String deptName, String officeName);

}
