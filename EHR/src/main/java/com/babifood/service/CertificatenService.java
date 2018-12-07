package com.babifood.service;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.babifood.entity.Certificaten;

@Service
public interface CertificatenService {

	List<Map<String, Object>> loadCertificaten(String c_p_number,String c_p_name,String zj_name,String zj_code,String orga);

	Integer removeCertificaten(String c_id);

	Integer saveCertificaten(Certificaten certificaten);

	Map<String, Object> importExcel(MultipartFile file, String type);

	Map<String, Object> exportDormitoryCosts(OutputStream ouputStream, String type);

}
