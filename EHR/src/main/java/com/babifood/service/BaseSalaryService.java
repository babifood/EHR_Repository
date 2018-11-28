package com.babifood.service;

import java.io.OutputStream;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.babifood.entity.BaseSalaryEntity;

public interface BaseSalaryService {

	public Map<String, Object> getPageBaseSalary(Integer page, Integer rows, String pNumber, String pName);

	public Map<String, Object> saveBaseSalary(BaseSalaryEntity baseSalary);

	public Map<String, Object> removeBaseSalary(Integer id, String pNumber);

	public Map<String, Object> getBaseSalaryRecord(Integer page, Integer rows, String pNumber);

	public Map<String, Object> importExcel(MultipartFile file);

	public void exportExcel(OutputStream ouputStream, String type);

}
