package com.babifood.control;

import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.babifood.service.SalaryDetailService;
import com.babifood.service.salary.SalaryCalculationService;
import com.babifood.utils.UtilDateTime;

@Controller
@RequestMapping("salaryDetail")
public class SalaryDetailController {

	@Autowired
	private SalaryDetailService salaryDetailService;
	
	@Autowired
	private SalaryCalculationService salaryCalculationService;
	
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
	public Map<String, Object> getPageSalaryDetails(Integer page, Integer rows, String pNumber, String pName, String resourceCode, String organzationName, String deptName, String officeName, String groupName){
		return salaryDetailService.getPageSalaryDetails(page, rows, pNumber, pName, resourceCode, organzationName, deptName, officeName, groupName);
	}
	
	/**
	 * 薪资计算
	 * @param type
	 * @return
	 */
	@RequestMapping("calculation")
	@ResponseBody
	public Map<String, Object> salaryCalculation(Integer type, String resourceCode, String year, String month){
		return salaryCalculationService.salaryCalculation(type, resourceCode, year, month);
	}
	
	/**
	 * 薪资明细导出
	 * @param response
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("export")
	public void exportExcel(HttpServletResponse response) throws Exception {
		String filename = new String("薪资详情列表".getBytes("UTF-8"),"ISO8859-1") + UtilDateTime.getCurrentTime("yyyyMMddHHmmss");
		response.setContentType("application/vnd.ms-excel");    
        response.setHeader("Content-disposition", "attachment;filename=" + filename + ".xls"); 
        OutputStream ouputStream = response.getOutputStream();    
        salaryDetailService.exportExcel(ouputStream);
	}
	
	/**
	 * 薪资明细导出
	 * @param response
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("auth")
	public Map<String, Object> getUserAuth() throws Exception {
		return salaryDetailService.getUserAuth();
	}
}
