package com.babifood.control.scheduled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.babifood.service.scheduled.InitializeArrangementService;
import com.babifood.service.scheduled.SalaryCalculationService;

@Controller
@EnableScheduling
public class ScheduledController {
	
	@Autowired
	private InitializeArrangementService arrangementService;

	@Autowired
	private SalaryCalculationService salaryService;
	/**
	 * 初始化考勤
	 */
	@Scheduled(cron = "1 0/2 * * * ?")
	public void initializeArrangement() {
		arrangementService.initializeArrangement();
	}
	
	/**
	 * 薪资计算
	 */
	@Scheduled(cron = "1 0 0/2 * * ?")
	public void salaryCalculation(){
		salaryService.salaryCalculation();
	}
	
}
