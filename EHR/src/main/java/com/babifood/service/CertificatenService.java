package com.babifood.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.babifood.entity.Certificaten;

@Service
public interface CertificatenService {

	List<Map<String, Object>> loadCertificaten(String c_p_number,String c_p_name);

	Integer removeCertificaten(String c_id);

	Integer saveCertificaten(Certificaten certificaten);

}
