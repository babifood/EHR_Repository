package com.babifood.service.scheduled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

@Service
public class SalaryCalculationService {

	@Autowired
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;
	
	public void salaryCalculation() {
		// TODO Auto-generated method stub
		
	}

}
