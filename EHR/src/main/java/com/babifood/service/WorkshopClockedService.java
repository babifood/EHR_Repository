package com.babifood.service;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface WorkshopClockedService {

	List<Map<String, Object>> loadWorkshopClocked(String workNumber, String userName);

	Map<String, Object> exportDormitoryCosts(OutputStream ouputStream, String type);

	Map<String, Object> importExcel(MultipartFile file, String type);
	
}
