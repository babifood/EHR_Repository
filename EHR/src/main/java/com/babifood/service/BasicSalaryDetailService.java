package com.babifood.service;

import java.io.OutputStream;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface BasicSalaryDetailService {

	public Map<String, Object> getPageSalaryDetails(Integer page, Integer row, String pNumber, String pName,
			String companyCode, String organzationName, String deptName, String officeName, String groupName);

	public Map<String, Object> importExcel(MultipartFile file) throws Exception;

	public void exportExcel(OutputStream ouputStream) throws Exception;

}
