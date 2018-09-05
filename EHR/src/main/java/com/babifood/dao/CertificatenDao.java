package com.babifood.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.babifood.entity.Certificaten;

@Repository
public interface CertificatenDao {

	List<Map<String, Object>> loadCertificaten(String c_p_number,String c_p_name);

	Integer removeCertificaten(String c_id);

	void saveCertificaten(Certificaten certificaten);

	void saveimportExcelCertificaten(List<Object[]> certificatenParam);
	
}
