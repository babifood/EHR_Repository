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

import com.babifood.entity.BaseSalaryEntity;
import com.babifood.service.BaseSalaryService;
import com.babifood.utils.UtilDateTime;

/**
 * 基础信息
 * @author wangguocheng
 *
 */
@Controller
@RequestMapping("baseSalary")
public class BaseSalaryController {

	@Autowired
	private BaseSalaryService baseSalaryService;
	
	/**
	 * 分页查询基础薪资
	 * @param page
	 * @param rows
	 * @param pNumber
	 * @param pName
	 * @return
	 */
	@RequestMapping("page")
	@ResponseBody
	public Map<String, Object> getPageBaseSalary(Integer page, Integer rows, String pNumber, String pName){
		return baseSalaryService.getPageBaseSalary(page, rows, pNumber, pName);
	}
	
	/**
	 * 个人基础薪资调整记录
	 * @param page
	 * @param rows
	 * @param pNumber
	 * @param pName
	 * @return
	 */
	@RequestMapping("record")
	@ResponseBody
	public Map<String, Object> getBaseSalaryRecord(Integer page, Integer rows, String pNumber){
		return baseSalaryService.getBaseSalaryRecord(page, rows, pNumber);
	}
	
	/**
	 * 保存基础薪资
	 * @param baseSalary
	 * @return
	 */
	@RequestMapping("save")
	@ResponseBody
	public Map<String, Object> saveBaseSalary(BaseSalaryEntity baseSalary){
		return baseSalaryService.saveBaseSalary(baseSalary);
	}
	
	/**
	 * 删除基础薪资
	 * @param id
	 * @param pNumber
	 * @return
	 */
	@RequestMapping("remove")
	@ResponseBody
	public Map<String, Object> removeBaseSalary(Integer id, String pNumber){
		return baseSalaryService.removeBaseSalary(id, pNumber);
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
		return baseSalaryService.importExcel(file);
	}
	
	/**
	 * 导出数据
	 * @param response
	 * @param type
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("export")
	public void exportExcel(HttpServletResponse response,String type) throws Exception {
		String filename = new String("基础薪资列表".getBytes("UTF-8"),"ISO8859-1");
		if("1".equals(type)){
			filename += UtilDateTime.getCurrentTime("yyyyMMddHHmmss");
		}
		response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=" + filename + ".xls");
        OutputStream ouputStream = response.getOutputStream();    
        baseSalaryService.exportExcel(ouputStream,type);
	}
	
	
}
