package com.babifood.control;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.babifood.entity.BaseSettingEntity;
import com.babifood.service.EmployeeBaseSettingService;

@Controller
@RequestMapping("basesetting")
public class EmployeeBaseSettingController {

	@Autowired
	private EmployeeBaseSettingService employeeService;

	@RequestMapping("all")
	@ResponseBody
	public Map<String, Object> getPageBaseSettingInfo(Integer page, Integer rows, String pName, String pNumber,
			String workType, String workPlace, String ismeal) {
		return employeeService.getPageBaseSettingInfo(page, rows, pName, pNumber, workType, workPlace, ismeal);
	}

	@RequestMapping("save")
	@ResponseBody
	public Map<String, Object> saveBaseSettingInfo(BaseSettingEntity baseSetting){
		return employeeService.saveBaseSettingInfo(baseSetting);
	}
}
