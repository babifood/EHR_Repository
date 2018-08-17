package com.babifood.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
		String[] codes = new String[5];
		codes[0] = personInFo.getP_company_id()==null?"":personInFo.getP_company_id();
		codes[1] = personInFo.getP_organization_id()==null?"":personInFo.getP_organization_id();
		codes[2] = personInFo.getP_department_id()==null?"":personInFo.getP_department_id();
		codes[3] = personInFo.getP_section_office_id()==null?"":personInFo.getP_section_office_id();
		codes[4] = personInFo.getP_group_id()==null?"":personInFo.getP_group_id();
		String p_this_dept_code = codes[0];
		for(int i=0;i<codes.length;i++){
			if(p_this_dept_code.length()<codes[i].length()){
				p_this_dept_code=codes[i];
			}
		}
		if(personInFo.getP_id().equals("")||personInFo.getP_id()==null){
			personInFo.setP_id(IdGen.uuid());
		}
		personInFo.setP_create_date(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		personInFo.setP_this_dept_code(p_this_dept_code);
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
	@Override
	public List<Map<String, Object>> loadOaWorkNumInFo(String workNum, String userName) {
		// TODO Auto-generated method stub
		return personInFoDao.loadOaWorkNumInFo(workNum, userName);
	}
	@Override
	public Object getRandomYxWorkNum() {
		// TODO Auto-generated method stub
	   	int random=new Random().nextInt(90000)+10000;//为变量赋随机值100-999;
	   	String newWorkNum = "YX"+random;
	   	List<Map<String, Object>> list = personInFoDao.loadEHRWorkNumInFo();
	   	int size = list==null?0:list.size();
	   	for(int i=0;i<size;i++){
	   		if(newWorkNum.equals(list.get(i).get("p_number").toString())){
	   			i=0;
	   			random=random+1;
	   			newWorkNum = "YX"+random;
	   		}
	   	}
		return newWorkNum;
	}
	
}
