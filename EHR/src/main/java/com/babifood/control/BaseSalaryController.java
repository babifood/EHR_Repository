package com.babifood.control;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.babifood.entity.BaseSalaryEntity;
import com.babifood.service.BaseSalaryService;

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
	 * 分页查询基础薪资
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
}
