package com.babifood.service;

import java.io.OutputStream;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface PerformanceService {

	public Map<String, Object> getPagePerformances(Integer page, Integer rows, String pNumber, String pName,
			String organzationName, String deptName, String officeName);

	public Map<String, Object> exportPerformances(OutputStream ouputStream, String type, String year, String month);

	public Map<String, Object> importExcel(MultipartFile file, String type);

	public Map<String, Object> savePerformanceScore(String year, String month, String pNumber, String score, String salary);


}
