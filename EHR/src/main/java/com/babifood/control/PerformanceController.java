package com.babifood.control;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.babifood.service.PerformanceService;
import com.babifood.utils.UtilDateTime;

@Controller
@RequestMapping("performance")
public class PerformanceController {

	@Autowired
	private PerformanceService performanceService;
	
	@RequestMapping("page")
	@ResponseBody
	public Map<String, Object> getPagePerformances(Integer page, Integer rows, String pNumber, String pName, String organzationName, String deptName, String officeName) {
		return performanceService.getPagePerformances(page, rows, pNumber, pName, organzationName, deptName, officeName);
	}

	@RequestMapping("export")
	@ResponseBody
	public Map<String, Object> exportPerformances(HttpServletResponse response, String type, String year, String month) throws IOException{
		String filename = new String("员工绩效信息列表".getBytes("UTF-8"),"ISO8859-1");
		if("1".equals(type)){
			filename += UtilDateTime.getCurrentTime("yyyyMMddHHmmss");
		}
		response.setContentType("application/vnd.ms-excel");    
        response.setHeader("Content-disposition", "attachment;filename=" + filename + ".xls");    
        OutputStream ouputStream = response.getOutputStream();   
		return performanceService.exportPerformances(ouputStream, type, year, month);
	}
	
	@RequestMapping(value = "importExcel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importExcel(@RequestParam(value="excel")CommonsMultipartFile file, String type){
		return performanceService.importExcel(file, type);
	}
}
