package com.babifood.control.scheduled;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.babifood.service.AnnualLeaveService;

@Controller
@EnableScheduling
public class AnnualLeaveScheduledController {
	
	private Logger logger = Logger.getLogger(AnnualLeaveScheduledController.class);
	
	@Autowired
	private AnnualLeaveService annualLeaveService;
	@Scheduled(cron = "0 0 1 * * ?")
	public void getAnuualLeave() throws ParseException{
		try {
			List<Map<String, Object>> PersonlistNow = annualLeaveService.GetHireDate();
			List<Map<String, Object>> PersonlistNew = annualLeaveService.setCompanyDayFlag(PersonlistNow);
			List<Map<String, Object>> PersonAnnualLeavelist = annualLeaveService.setAnnualLeavelist(PersonlistNew);
			if(PersonAnnualLeavelist.isEmpty()){
				logger.error("没有需要更新的年假记录！");
			}else{
				annualLeaveService.SaveAnnualLeave(PersonAnnualLeavelist);
			}
		} catch (Exception e) {
			logger.error("年假计算失败", e);
		}
		
     }
}

