package com.babifood.control;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.babifood.entity.BaseArrangementEntity;
import com.babifood.entity.SpecialArrangementEntity;
import com.babifood.service.BaseArrangementService;
import com.babifood.utils.UtilString;

@Controller
@RequestMapping("arrangement")
public class BaseArrangementController {
	
	@Autowired
	private BaseArrangementService baseArrangementService;
	
	@RequestMapping(value = "base/findAll")
	@ResponseBody
	public List<Map<String, Object>> findBaseArrangements(){
		return baseArrangementService.findBaseArrangements();
	}
	
	
	@RequestMapping("base/save")
	@ResponseBody
	public Map<String, Object> saveBaseArrangement(@RequestBody BaseArrangementEntity arrangement){
		return baseArrangementService.saveBaseArrangement(arrangement);
	}
	
	@RequestMapping("base/remove")
	@ResponseBody
	public Map<String, Object> removeBaseArrangement(Integer id){
		return baseArrangementService.removeBaseArrangement(id);
	}
	
	@RequestMapping("base/specialArrangementList")
	@ResponseBody
	public Map<String, Object> findSpecialArrangementList(String year,String month,String arrangementId,String deptCode,String pNumber) {
		String date = year + "-" + (month.length() == 1? "0"+month : month);
		if(!UtilString.isEmpty(arrangementId)){
			return baseArrangementService.findSpecialArrangementList(date,arrangementId);
		}
		return baseArrangementService.findSpecialArrangementList(date,deptCode,pNumber);
	}
	
	@RequestMapping("base/saveSpecialArrangement")
	@ResponseBody
	public Map<String, Object> saveSpecialArrangement(SpecialArrangementEntity arrangement) {
		return baseArrangementService.saveSpecialArrangement(arrangement);
	}
	
	@RequestMapping("base/removeSpecialArrangement")
	@ResponseBody
	public Map<String, Object> removeSpecialArrangement(String id) {
		return baseArrangementService.removeSpecialArrangement(id);
	}
	
	@RequestMapping("base/findSpecialArrangement")
	@ResponseBody
	public Map<String, Object> findSpecialArrangement(String id) {
		return baseArrangementService.findSpecialArrangementById(id);
	}
	
	@RequestMapping("base/specialArrangementId")
	@ResponseBody
	public Map<String, Object> findSpecialArrangementId(String deptCode,String pNumber) {
		return baseArrangementService.findSpecialArrangementId(deptCode,pNumber);
	}
	
	@RequestMapping("base/bindArrangement")
	@ResponseBody
	public Map<String, Object> bindArrangement(String targetId,String type,String arrangementId) {
		try {
			return baseArrangementService.bindArrangement(targetId,type,arrangementId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
