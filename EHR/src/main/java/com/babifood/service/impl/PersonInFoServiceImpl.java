package com.babifood.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.babifood.dao.PersonInFoDao;
import com.babifood.entity.PersonBasrcEntity;
import com.babifood.service.PersonInFoService;
import com.babifood.utils.IdGen;
@Service
public class PersonInFoServiceImpl implements PersonInFoService {
	@Autowired
	PersonInFoDao personInFoDao;
	@Override
	public List<Map<String, Object>> loadPersonInFo(String search_p_number,String search_p_name) {
		// TODO Auto-generated method stub
		return personInFoDao.loadPersonInFo(search_p_number,search_p_name);
	}
	@Override
	public List<Map<String, Object>> loadEducation(String e_p_id) {
		// TODO Auto-generated method stub
		return personInFoDao.loadEducation(e_p_id);
	}
	@Override
	public Integer savePersonInfo(PersonBasrcEntity personInFo) {
		// TODO Auto-generated method stub
		if(personInFo.getP_id().equals("")||personInFo.getP_id()==null){
			personInFo.setP_id(IdGen.uuid());
		}
		personInFo.setP_create_date(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		return personInFoDao.savePersonInfo(personInFo);
	}
	@Override
	public Integer removePersonInFo(String p_id) {
		// TODO Auto-generated method stub
		return personInFoDao.removePersonInFo(p_id);
	}
	@Override
	public List<Map<String, Object>> loadCultivateFront(String c_p_id) {
		// TODO Auto-generated method stub
		return personInFoDao.loadCultivateFront(c_p_id);
	}
	@Override
	public List<Map<String, Object>> loadCultivateLater(String c_p_id) {
		// TODO Auto-generated method stub
		return personInFoDao.loadCultivateLater(c_p_id);
	}
	@Override
	public List<Map<String, Object>> loadWorkFront(String w_p_id) {
		// TODO Auto-generated method stub
		return personInFoDao.loadWorkFront(w_p_id);
	}
	@Override
	public List<Map<String, Object>> loadWorkLater(String w_p_id) {
		// TODO Auto-generated method stub
		return personInFoDao.loadWorkLater(w_p_id);
	}
	@Override
	public List<Map<String, Object>> loadCertificate(String c_p_id) {
		// TODO Auto-generated method stub
		return personInFoDao.loadCertificate(c_p_id);
	}
	@Override
	public List<Map<String, Object>> loadFamily(String f_p_id) {
		// TODO Auto-generated method stub
		return personInFoDao.loadFamily(f_p_id);
	}
	@Override
	public Object getPersonFoPid(String p_id) {
		// TODO Auto-generated method stub
		return personInFoDao.getPersonFoPid(p_id);
	}
	@Override
	public List<Map<String, Object>> loadComboboxCompanyData() {
		// TODO Auto-generated method stub
		return personInFoDao.loadComboboxCompanyData();
	}
	@Override
	public Integer getPersonCount() {
		return personInFoDao.getPersonCount();
	}
	@Override
	public List<Map<String, Object>> findPagePersonInfo(int index, int threadCount) {
		return personInFoDao.findPagePersonInfo(index * threadCount ,threadCount);
		
	}
	@Override
	public Object getPersonByPnumber(String pNumber) {
		return personInFoDao.getPersonByPnumber(pNumber);
	}

}
