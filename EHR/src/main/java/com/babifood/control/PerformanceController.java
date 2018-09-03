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
import org.springframework.web.multipart.MultipartFile;

import com.babifood.service.PerformanceService;
import com.babifood.utils.UtilDateTime;

@Controller
@RequestMapping("performance")
public class PerformanceController {

	@Autowired
	private PerformanceService performanceService;
	
	/**
	 * 分页查询绩效薪资信息
	 * @param page
	 * @param rows
	 * @param pNumber
	 * @param pName
	 * @param organzationName
	 * @param deptName
	 * @param officeName
	 * @return
	 */
	@RequestMapping("page")
	@ResponseBody
	public Map<String, Object> getPagePerformances(Integer page, Integer rows, String pNumber, String pName, String organzationName, String deptName, String officeName) {
		return performanceService.getPagePerformances(page, rows, pNumber, pName, organzationName, deptName, officeName);
	}

	/**
	 * 导出绩效薪资列表
	 * @param response
	 * @param type
	 * @param year
	 * @param month
	 * @return
	 * @throws IOException
	 */
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
	
	/**
	 * 导入绩效薪资信息
	 * @param file
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "importExcel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importExcel(@RequestParam(value="file",required = false) MultipartFile file, String type){
		return performanceService.importExcel(file, type);
	}
	
	/**
	 * 修改绩效分值或绩效工资
	 * @param year
	 * @param month
	 * @param pNumber
	 * @param score
	 * @param salary
	 * @return
	 */
	@RequestMapping("save")
	@ResponseBody
	public Map<String, Object> savePerformanceScore(String year, String month, String pNumber, String score, String salary) {
		return performanceService.savePerformanceScore(year, month, pNumber, score, salary);
	}
}
