package com.babifood.control;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.babifood.entity.ArrangementBaseTimeEntity;
import com.babifood.service.ArrangementBaseTimeService;

@Controller
@RequestMapping("arrangement")
public class ArrangementBaseTimeController {
	
	@Autowired
	private ArrangementBaseTimeService arrangementBaseTimeService;
	
	@RequestMapping(value = "baseTime/findAll")
	@ResponseBody
	public List<Map<String, Object>> findAllArrangementBaseTimes(){
		return arrangementBaseTimeService.findAllArrangementBaseTimes();
	}
	
	
	@RequestMapping("baseTime/save")
	@ResponseBody
	public Map<String, Object> saveArrangementBaseTime(@RequestBody ArrangementBaseTimeEntity arrangement){
		return arrangementBaseTimeService.saveArrangementBaseTime(arrangement);
	}
	
	@RequestMapping("baseTime/remove")
	@ResponseBody
	public Map<String, Object> removeArrangement(Integer id){
		return arrangementBaseTimeService.removeArrangementBaseTime(id);
	}
	
}
