package com.babifood.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.babifood.entity.PersonBasrcEntity;
import com.babifood.service.PersonInFoService;
@Controller
public class PersonInFoControl {
	@Autowired
	PersonInFoService personInFoService;
	//主列表人员档案信息
	@ResponseBody
	@RequestMapping("/loadPersonInFo")
	public Map<String,Object> loadPersonInFo(String searchKey,String searchVal){
		Map<String,Object> map =new HashMap<String,Object>();
		List<Map<String, Object>> list = personInFoService.loadPersonInFo(searchKey,searchVal);
		map.put("total", list.size());
		map.put("rows", list);
		return map;
	}
	@ResponseBody
	@RequestMapping("/getPersonFoPid")
	public PersonBasrcEntity getPersonFoPid(String p_number){
		PersonBasrcEntity personBasrc = (PersonBasrcEntity) personInFoService.getPersonFoPid(p_number);
		return personBasrc;	
	}
	//教育背景
	@ResponseBody
	@RequestMapping("/loadEducation")
	public Map<String,Object> loadEducation(String e_p_id){
		Map<String,Object> map =new HashMap<String,Object>();
		List<Map<String, Object>> list = personInFoService.loadEducation(e_p_id);
		map.put("total", list.size());
		map.put("rows", list);
		return map;
	}
	//培训经历-入职前的
	@ResponseBody
	@RequestMapping("/loadCultivateFront")
	public Map<String,Object> loadCultivateFront(String c_p_id){
		Map<String,Object> map =new HashMap<String,Object>();
		List<Map<String, Object>> list = personInFoService.loadCultivateFront(c_p_id);
		map.put("total", list.size());
		map.put("rows", list);
		return map;
	}
	//培训经历-入职后的
	@ResponseBody
	@RequestMapping("/loadCultivateLater")
	public Map<String,Object> loadCultivateLater(String c_p_id){
		Map<String,Object> map =new HashMap<String,Object>();
		List<Map<String, Object>> list = personInFoService.loadCultivateLater(c_p_id);
		map.put("total", list.size());
		map.put("rows", list);
		return map;
	}
	//工作经历-入职前的
	@ResponseBody
	@RequestMapping("/loadWorkFront")
	public Map<String,Object> loadWorkFront(String w_p_id){
		Map<String,Object> map =new HashMap<String,Object>();
		List<Map<String, Object>> list = personInFoService.loadWorkFront(w_p_id);
		map.put("total", list.size());
		map.put("rows", list);
		return map;
	}	
	//工作经历-入职后的
	@ResponseBody
	@RequestMapping("/loadWorkLater")
	public Map<String,Object> loadWorkLater(String w_p_id){
		Map<String,Object> map =new HashMap<String,Object>();
		List<Map<String, Object>> list = personInFoService.loadWorkLater(w_p_id);
		map.put("total", list.size());
		map.put("rows", list);
		return map;
	}
	//获得的证书
	@ResponseBody
	@RequestMapping("/loadCertificate")
	public Map<String,Object> loadCertificate(String c_p_id){
		Map<String,Object> map =new HashMap<String,Object>();
		List<Map<String, Object>> list = personInFoService.loadCertificate(c_p_id);
		map.put("total", list.size());
		map.put("rows", list);
		return map;
	}
	//家庭背景
	@ResponseBody
	@RequestMapping("/loadFamily")
	public Map<String,Object> loadFamily(String f_p_id){
		Map<String,Object> map =new HashMap<String,Object>();
		List<Map<String, Object>> list = personInFoService.loadFamily(f_p_id);
		map.put("total", list.size());
		map.put("rows", list);
		return map;
	}			
	@ResponseBody
	@RequestMapping("/savePersonInFo")
	public Map<String,Object> savePersonInFo(@RequestBody PersonBasrcEntity personInFo){
		Map<String,Object> map =new HashMap<String,Object>();
		int rows = personInFoService.savePersonInfo(personInFo);
		if(rows>0){
			map.put("status", "success");
		}else{
			map.put("status", "error");
		}
		return map;
	}
	@ResponseBody
	@RequestMapping("/removePersonInFo")
	public Map<String,Object> removePersonInFo(String p_number){
		Map<String,Object> map =new HashMap<String,Object>();
		int rows = personInFoService.removePersonInFo(p_number);
		if(rows>0){
			map.put("status", "success");
		}else{
			map.put("status", "error");
		}
		return map;
	}
	/**
	 * 加载公司信息下拉选择数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/loadComboboxCompanyData")
	public List<Map<String, Object>> loadComboboxCompanyData(){
		return personInFoService.loadComboboxCompanyData();
	}

	@ResponseBody
	@RequestMapping("/getPersonByPnumber")
	public PersonBasrcEntity getPersonByPnumber(String pNumber) {
		return (PersonBasrcEntity) personInFoService.getPersonByPnumber(pNumber);
	}
	//加载OA同步员工工号
	@ResponseBody
	@RequestMapping("/loadOaSyncWorkNum")
	public Map<String,Object> loadOaWorkNum(String workNum,String userName){
		Map<String,Object> map =new HashMap<String,Object>();
		List<Map<String, Object>> list = personInFoService.loadOaWorkNumInFo(workNum, userName);
		map.put("total", list.size());
		map.put("rows", list);
		return map;
		
	}
	@ResponseBody
	@RequestMapping("/getEhrWorkNum")
	public Object getEhrWorkNum(String companyId,String organizationId){
		return personInFoService.getRandomYxWorkNum(companyId,organizationId);
	}
	@RequestMapping("/loadPersonlimit")
	public Map<String,Object> loadPersonlimit(String search_p_number,String search_p_name, Integer limit){
		Map<String,Object> map =new HashMap<String,Object>();
		List<Map<String, Object>> list = personInFoService.loadPersonlimit(search_p_number,search_p_name, limit);
		map.put("total", list.size());
		map.put("rows", list);
		return map;
	}
}
