package com.babifood.control.scheduled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.babifood.service.scheduling.DakaRecordService;
import com.babifood.service.scheduling.DakaSourceService;

@Controller
@EnableScheduling
public class ScheduledController {
	
	@Autowired
	private DakaSourceService dakaSourceService;
	
	@Autowired
	private DakaRecordService dakaRecordService;
	
	@Scheduled(cron = "0 0/8 * * * ?")
	public void getDakaSource() {
		dakaSourceService.getDakaSource();
	}
	
	
	@Scheduled(cron = "0 0/2 * * * ?")
	public void checkDakaRecord(){
		try {
			dakaRecordService.checkDakaRecord();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
