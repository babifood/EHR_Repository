package com.babifood.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.babifood.entity.PersonBasrcEntity;
@Service
public interface PersonInFoService {
	public List<Map<String, Object>> loadPersonInFo(String searchKey,String searchVal);

	public List<Map<String, Object>> loadEducation(String e_p_id);

	public Integer savePersonInfo(PersonBasrcEntity personInFo);

	public Integer removePersonInFo(String p_id);

	public List<Map<String, Object>> loadCultivateFront(String c_p_id);

	public List<Map<String, Object>> loadCultivateLater(String c_p_id);

	public List<Map<String, Object>> loadWorkFront(String w_p_id);

	public List<Map<String, Object>> loadWorkLater(String w_p_id);

	public List<Map<String, Object>> loadCertificate(String c_p_id);

	public List<Map<String, Object>> loadFamily(String f_p_id);

	public Object getPersonFoPid(String p_id);

	public List<Map<String, Object>> loadComboboxCompanyData();
	
	public Integer getPersonCount();

	public List<Map<String, Object>> findPagePersonInfo(int index, int threadCount);

	public Object getPersonByPnumber(String pNumber);
	
	public List<Map<String, Object>> loadOaWorkNumInFo(String workNum,String userName);
	
	public Object getRandomYxWorkNum(String companyId, String organizationId);

	public List<Map<String, Object>> loadPersonlimit(String search_p_number, String search_p_name, Integer limit);

}
