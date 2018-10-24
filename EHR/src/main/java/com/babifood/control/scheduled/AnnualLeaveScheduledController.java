package com.babifood.control.scheduled;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.babifood.service.AnnualLeaveService;

@Controller
@EnableScheduling
public class AnnualLeaveScheduledController {
	@Autowired
	private AnnualLeaveService annualLeaveService;
	@Scheduled(cron = "0 0/6 * * * ?")
	public void getAnuualLeave() throws ParseException{
		List<Map<String, Object>> PersonlistNow = annualLeaveService.GetHireDate();
		List<Map<String, Object>> PersonlistNew = annualLeaveService.setCompanyDayFlag(PersonlistNow);
		List<Map<String, Object>> PersonAnnualLeavelist = annualLeaveService.setAnnualLeavelist(PersonlistNew);
		if(PersonAnnualLeavelist.isEmpty()){
			 System.out.println("没有需要更新的年假记录！");
		}else{
			annualLeaveService.SaveAnnualLeave(PersonAnnualLeavelist);
		}
     }
}

