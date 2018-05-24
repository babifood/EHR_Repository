package com.babifood.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.babifood.entity.Certificaten;
import com.babifood.service.CertificatenService;
//证件管理
@Controller
public class CertificatenControl {
	@Autowired
	CertificatenService certificatenService;
	@ResponseBody
	@RequestMapping("/loadCertificaten")
	public Map<String,Object> loadCertificatenAll(String c_p_number,String c_p_name){
		Map<String,Object> map =new HashMap<String,Object>();
		List<Map<String, Object>> list = certificatenService.loadCertificaten(c_p_number,c_p_name);
		map.put("total", list.size());
		map.put("rows", list);
		return map;
	}
	@ResponseBody
	@RequestMapping("/removeCertificaten")
	public Map<String,Object> removeUser(String c_id){
		Map<String,Object> map =new HashMap<String,Object>();
		int rows = certificatenService.removeCertificaten(c_id);
		if(rows>0){
			map.put("status", "success");
		}else{
			map.put("status", "error");
		}
		return map;
	}
	@ResponseBody
	@RequestMapping("/saveCertificaten")
	public Map<String,Object> saveCertificaten(@RequestBody Certificaten certificaten){
		Map<String,Object> map =new HashMap<String,Object>();
		int rows = certificatenService.saveCertificaten(certificaten);
		if(rows>0){
			map.put("status", "success");
		}else{
			map.put("status", "error");
		}
		return map;
		
	}
}
