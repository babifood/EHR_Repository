package com.babifood.control;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.babifood.service.WorkshopClockedService;
import com.babifood.utils.UtilDateTime;

@Controller
public class WorkshopClockedControl {
	@Autowired
	WorkshopClockedService workshopClockedService;
	@Autowired
	JdbcTemplate jdbctemplate;
	@ResponseBody
	@RequestMapping("/loadWorkshopClocked")
	public Map<String,Object> loadWorkshopClocked(String WorkNumber,String UserName,String comp,String organ,String dept){
		Map<String,Object> map =new HashMap<String,Object>();
		List<Map<String, Object>> list = workshopClockedService.loadWorkshopClocked(WorkNumber,UserName,comp,organ,dept);
		map.put("total", list.size());
		map.put("rows", list);
		return map;
	}
	@RequestMapping("/WorkshopClockedExport")
	@ResponseBody
	public Map<String, Object> exportDormitoryCosts(HttpServletResponse response, String type,String WorkNumber,String UserName,String comp,String organ,String dept) throws IOException{
		String filename = new String("车间考勤表".getBytes("UTF-8"),"ISO8859-1");
		if("1".equals(type)){
			filename += UtilDateTime.getCurrentTime("yyyyMMddHHmmss");
		}
		response.setContentType("application/vnd.ms-excel");    
        response.setHeader("Content-disposition", "attachment;filename=" + filename + ".xls");    
        OutputStream ouputStream = response.getOutputStream();   
		return workshopClockedService.exportDormitoryCosts(ouputStream, type,WorkNumber,UserName,comp,organ,dept);
	}
	@RequestMapping(value = "/WorkshopClockedImportExcel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importExcel(@RequestParam(value="file",required = false) MultipartFile file, String type){
		return workshopClockedService.importExcel(file, type);
	}
}
