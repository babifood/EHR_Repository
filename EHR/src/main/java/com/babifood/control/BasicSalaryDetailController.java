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
import org.springframework.web.multipart.MultipartFile;

import com.babifood.service.BasicSalaryDetailService;
import com.babifood.utils.UtilDateTime;

@Controller
@RequestMapping("basicSalaryDetail")
public class BasicSalaryDetailController {

	@Autowired
	private BasicSalaryDetailService basicSalaryDetailService;
	
	/**
	 * 分页查询薪资明细
	 * @param page
	 * @param rows
	 * @param pNumber
	 * @param pName
	 * @param organzationName
	 * @param deptName
	 * @param officeName
	 * @param groupName
	 * @return
	 */
	@RequestMapping("page")
	@ResponseBody
	public Map<String, Object> getPageSalaryDetails(Integer page, Integer rows, String pNumber, String pName, String companyCode, String organzationName, String deptName, String officeName, String groupName){
		return basicSalaryDetailService.getPageSalaryDetails(page, rows, pNumber, pName, companyCode, organzationName, deptName, officeName, groupName);
	}
	
	
	/**
	 * 薪资明细导出
	 * @param response
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("export")
	public void exportExcel(HttpServletResponse response) throws Exception {
		String filename = new String("一线员工薪资详情列表".getBytes("UTF-8"),"ISO8859-1");
		response.setContentType("application/vnd.ms-excel");    
        response.setHeader("Content-disposition", "attachment;filename=" + filename + ".xls"); 
        OutputStream ouputStream = response.getOutputStream();    
        basicSalaryDetailService.exportExcel(ouputStream);
	}
	
	/**
	 * 导入数据
	 * @param file
	 * @param type
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/importExcel", method = RequestMethod.POST)
	public Map<String, Object> importExcel(@RequestParam(value="file",required = false) MultipartFile file) throws Exception {
		return basicSalaryDetailService.importExcel(file);
	}
	
}
