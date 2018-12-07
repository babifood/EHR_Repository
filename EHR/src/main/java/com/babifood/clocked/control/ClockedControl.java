package com.babifood.clocked.control;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.babifood.clocked.service.ClockedService;
import com.babifood.clocked.service.CollectionClockedDataService;
import com.babifood.clocked.service.ExportClockService;
import com.babifood.clocked.service.LoadClockedResultService;
import com.babifood.clocked.service.LockClockedService;
import com.babifood.clocked.service.PushClockedDataService;
import com.babifood.exception.CustomException;
import com.babifood.utils.UtilDateTime;

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
	@Autowired
	ExportClockService exportClockService;
	@Autowired
	LockClockedService lockClockedService;
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
	public Map<String,Object> loadSumClockedResult(String searchKey,String searchVal,String myYear,String myMonth,
			String comp,String organ,String dept){
		Map<String,Object> map =new HashMap<String,Object>();
		List<Map<String, Object>> list = null;
		list = loadClockedResultService.loadSumClockedResultData(searchKey,searchVal,myYear,myMonth,comp,organ,dept);
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
	@RequestMapping("/lockClockedData")
	public Map<String,Object> lockClockedData(Integer year,Integer month){
		Calendar tempCal = Calendar.getInstance();
		int sysYear = year==0?tempCal.get(Calendar.YEAR):year;
		int sysMonth = month==0?tempCal.get(Calendar.MONTH)+1:month;			
		Map<String,Object> map =new HashMap<String,Object>();
		int[] rows = lockClockedService.lockData(sysYear,sysMonth);
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
	//导出考勤汇总数据
	@RequestMapping("/sumClockedResultExport")
	@ResponseBody
	public Map<String, Object> exportSumClockedResult(HttpServletResponse response,String searchKey,String searchVal,String myYear,String myMonth,
			String comp,String organ,String dept) throws IOException{
		String filename = new String("考勤汇总数据表".getBytes("UTF-8"),"ISO8859-1");
		filename += UtilDateTime.getCurrentTime("yyyyMMddHHmmss");
		response.setContentType("application/vnd.ms-excel");    
        response.setHeader("Content-disposition", "attachment;filename=" + filename + ".xls");    
        OutputStream ouputStream = response.getOutputStream();   
		return exportClockService.exportSumClockedResult(ouputStream,searchKey,searchVal,myYear,myMonth,comp,organ,dept);
	}
	//导出考勤个人明细数据
	@RequestMapping("/detailsClockedResultExport")
	@ResponseBody
	public Map<String, Object> exportDetailsClockedResult(HttpServletResponse response,Integer year,Integer month,String workNum,String periodEndDate) throws IOException{
		String filename = new String("考勤个人明细数据表".getBytes("UTF-8"),"ISO8859-1");
		filename += UtilDateTime.getCurrentTime("yyyyMMddHHmmss");
		response.setContentType("application/vnd.ms-excel");    
        response.setHeader("Content-disposition", "attachment;filename=" + filename + ".xls");    
        OutputStream ouputStream = response.getOutputStream();   
		return exportClockService.exportDetailsClockedResult(ouputStream,year,month,workNum,periodEndDate);
	}
}
