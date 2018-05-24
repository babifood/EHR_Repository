package com.babifood.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.babifood.dao.CertificatenDao;
import com.babifood.entity.Certificaten;
import com.babifood.service.CertificatenService;
@Service
public class CertificatenServiceImpl implements CertificatenService {
	@Autowired
	CertificatenDao certificatenDao;
	@Override
	public List<Map<String, Object>> loadCertificaten(String c_p_number,String c_p_name) {
		// TODO Auto-generated method stub
		return certificatenDao.loadCertificaten(c_p_number,c_p_name);
	}
	@Override
	public Integer removeCertificaten(String c_id) {
		// TODO Auto-generated method stub
		return certificatenDao.removeCertificaten(c_id);
	}
	@Override
	public Integer saveCertificaten(Certificaten certificaten) {
		// TODO Auto-generated method stub
		return certificatenDao.saveCertificaten(certificaten);
	}

}
