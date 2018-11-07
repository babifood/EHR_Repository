package com.babifood.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.babifood.entity.PersonBasrcEntity;
@Repository
public interface PersonInFoDao {
	public List<Map<String, Object>> loadPersonInFo(String search_p_number,String search_p_name);

	public List<Map<String, Object>> loadEducation(String e_p_id);

	public void savePersonInfo(PersonBasrcEntity personInFo);

	public void removePersonInFo(String p_id);

	public List<Map<String, Object>> loadCultivateFront(String c_p_id);

	public List<Map<String, Object>> loadCultivateLater(String c_p_id);

	public List<Map<String, Object>> loadWorkFront(String w_p_id);

	public List<Map<String, Object>> loadWorkLater(String w_p_id);

	public List<Map<String, Object>> loadCertificate(String c_p_id);

	public List<Map<String, Object>> loadFamily(String f_p_id);

	public Object getPersonFoPid(String p_id);

	public List<Map<String, Object>> loadComboboxCompanyData();
	
	public List<Map<String, Object>> findPersonListByIds(String[] ids);

	public Integer getPersonCount();

	public List<Map<String, Object>> findPagePersonInfo(int startIndex, int pageSize);

	public Object getPersonByPnumber(String pNumber);
	
	
	public List<Map<String, Object>> loadEHRWorkNumInFo(String companyId, String organizationId);

	public List<Map<String, Object>> loadPersonlimit(String search_p_number, String search_p_name, Integer limit);
}
