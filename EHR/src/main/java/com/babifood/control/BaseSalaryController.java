package com.babifood.control;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.babifood.entity.BaseSalaryEntity;
import com.babifood.service.BaseSalaryService;

@Controller
@RequestMapping("baseSalary")
public class BaseSalaryController {

	@Autowired
	private BaseSalaryService baseSalaryService;
	
	@RequestMapping("page")
	@ResponseBody
	public Map<String, Object> getPageBaseSalary(Integer page, Integer rows, String pNumber, String pName){
		return baseSalaryService.getPageBaseSalary(page, rows, pNumber, pName);
	}
	
	@RequestMapping("save")
	@ResponseBody
	public Map<String, Object> saveBaseSalary(BaseSalaryEntity baseSalary){
		return baseSalaryService.saveBaseSalary(baseSalary);
	}
	
	@RequestMapping("remove")
	@ResponseBody
	public Map<String, Object> removeBaseSalary(Integer id, String pNumber){
		return baseSalaryService.removeBaseSalary(id, pNumber);
	}
}
