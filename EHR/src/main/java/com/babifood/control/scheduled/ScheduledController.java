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
	
	@Scheduled(cron = "0 1 0 * * ?")
	public void getDakaSourceFirst() {
		dakaSourceService.getDakaSource("1");
	}
	
	@Scheduled(cron = "0 30 0 * * ?")
	public void getDakaSourceSecond() {
		dakaSourceService.getDakaSource("2");
	}
	
	
	@Scheduled(cron = "0 1 1 * * ?")
	public void checkDakaRecordFirst(){
		try {
			dakaRecordService.checkDakaRecord("1");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Scheduled(cron = "0 30 1 * * ?")
	public void checkDakaRecord(){
		try {
			dakaRecordService.checkDakaRecord("2");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
