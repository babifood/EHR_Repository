package com.babifood.clocked.control;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.babifood.clocked.service.ClockedService;
import com.babifood.clocked.service.CollectionClockedDataService;
import com.babifood.clocked.service.LoadClockedResultService;
import com.babifood.clocked.service.PushClockedDataService;
import com.babifood.exception.CustomException;

@Controller
@RequestMapping("/clocked")
public class ClockedControl {
	@Autowired
	LoadClockedResultService loadClockedResultService;
	@Autowired
	ClockedService clockedService;
	@Autowired
	CollectionClockedDataService collectionClockedDataService;
	@Autowired
	PushClockedDataService pushClockedDataService;
	/**
	 * 考勤明细查询
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/loadClockedResult")
	public Map<String,Object> loadClockedResult(Integer year,Integer month,String workNum,String periodEndDate){
		
		Calendar tempCal = Calendar.getInstance();
		int sysYear = year==0?tempCal.get(Calendar.YEAR):year;
		int sysMonth = month==0?tempCal.get(Calendar.MONTH)+1:month;		
		Map<String,Object> map =new HashMap<String,Object>();
		List<Map<String, Object>> list = null;
		list = loadClockedResultService.loadClockedResultData(sysYear,sysMonth,workNum,periodEndDate);
		map.put("total", list.size());
		map.put("rows", list);
		return map;
	}
	/**
	 * 考勤汇总查询
	 * @param WorkNum
	 * @param UserName
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/loadSumClockedResult")
	public Map<String,Object> loadSumClockedResult(String WorkNum,String UserName){
		Map<String,Object> map =new HashMap<String,Object>();
		List<Map<String, Object>> list = null;
		list = loadClockedResultService.loadSumClockedResultData(WorkNum,UserName);
		map.put("total", list.size());
		map.put("rows", list);
		return map;
	}
	@ResponseBody
	@RequestMapping("/initClockedData")
	public Map<String,Object> initClockedData(Integer year,Integer month){
		Map<String,Object> map =new HashMap<String,Object>();
		int[] rows = clockedService.init(year,month);
		if(rows!=null&&rows.length>0){
			map.put("status", "success");
		}else{
			map.put("status", "error");
		}
		return map;
	}
	@ResponseBody
	@RequestMapping("/executeClockedData")
	public Map<String,Object> executeClockedData(Integer year,Integer month){
		Calendar tempCal = Calendar.getInstance();
		int sysYear = year==0?tempCal.get(Calendar.YEAR):year;
		int sysMonth = month==0?tempCal.get(Calendar.MONTH)+1:month;			
		Map<String,Object> map =new HashMap<String,Object>();
		int[] rows = collectionClockedDataService.execute(sysYear,sysMonth);
		if(rows!=null&&rows.length>0){
			map.put("status", "success");
		}else{
			map.put("status", "error");
		}
		return map;
	}
	@ResponseBody
	@RequestMapping("/pushClockedData")
	public Map<String,Object> pushClockedData(Integer year,Integer month){
		Calendar tempCal = Calendar.getInstance();
		int sysYear = year==0?tempCal.get(Calendar.YEAR):year;
		int sysMonth = month==0?tempCal.get(Calendar.MONTH)+1:month;			
		Map<String,Object> map =new HashMap<String,Object>();
		String row = pushClockedDataService.pushDataToOA(sysYear, sysMonth);
		if(!row.equals("-1")){
			map.put("status", "success");
		}else{
			map.put("status", "error");
		}
		return map;
	}
}
