package com.babifood.control;

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

import com.babifood.service.AllowanceService;
import com.babifood.utils.UtilDateTime;

@Controller
@RequestMapping("allowance")
public class AllowanceController {

	@Autowired
	private AllowanceService allowanceService;
	
	@RequestMapping("page")
	@ResponseBody
	public Map<String, Object> getPageAllowanceList(Integer page, Integer rows, String pNumber, String pName, String organzationName, String deptName, String officeName){
		return allowanceService.getPageAllowanceList(page, rows, pNumber, pName, organzationName, deptName, officeName);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/improtExcel", method = RequestMethod.POST)
	public Map<String, Object> importExcel(@RequestParam(value="excel")CommonsMultipartFile file, String type) throws Exception {
		return allowanceService.importExcel(file, type);
	}
	
	@ResponseBody
	@RequestMapping("export")
	public void exportExcel(HttpServletResponse response,String type) throws Exception {
		response.setContentType("application/vnd.ms-excel");    
		response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + 
        		"部门信息" + UtilDateTime.getCurrentTime("yyyyMMddHHmmss") + ".xls");    
        OutputStream ouputStream = response.getOutputStream();    
        allowanceService.exportExcel(ouputStream,type);
	}
}
