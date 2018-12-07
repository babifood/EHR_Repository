package com.babifood.service;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface WorkshopClockedService {

	List<Map<String, Object>> loadWorkshopClocked(String workNumber, String userName,String comp,String organ,String dept);

	Map<String, Object> exportDormitoryCosts(OutputStream ouputStream, String type,String WorkNumber,String UserName,String comp,String organ,String dept);

	Map<String, Object> importExcel(MultipartFile file, String type);
	
}
