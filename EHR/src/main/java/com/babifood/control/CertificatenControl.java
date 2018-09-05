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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.babifood.entity.Certificaten;
import com.babifood.service.CertificatenService;
import com.babifood.utils.CustomerContextHolder;
import com.babifood.utils.UtilDateTime;
//证件管理
@Controller
public class CertificatenControl {
	@Autowired
	CertificatenService certificatenService;
	@Autowired
	JdbcTemplate jdbctemplate;
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
	@RequestMapping("/CertificatenExport")
	@ResponseBody
	public Map<String, Object> exportDormitoryCosts(HttpServletResponse response, String type) throws IOException{
		String filename = new String("证件管理表".getBytes("UTF-8"),"ISO8859-1");
		if("1".equals(type)){
			filename += UtilDateTime.getCurrentTime("yyyyMMddHHmmss");
		}
		response.setContentType("application/vnd.ms-excel");    
        response.setHeader("Content-disposition", "attachment;filename=" + filename + ".xls");    
        OutputStream ouputStream = response.getOutputStream();   
		return certificatenService.exportDormitoryCosts(ouputStream, type);
	}
	@RequestMapping(value = "/CertificatenImportExcel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importExcel(@RequestParam(value="file",required = false) MultipartFile file, String type){
		return certificatenService.importExcel(file, type);
	}
}
