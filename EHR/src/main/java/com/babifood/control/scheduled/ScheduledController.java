package com.babifood.control.scheduled;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
@EnableScheduling
public class ScheduledController {
	

	@Scheduled(cron = "1 0 0 1 * ?")
	public void initializeArrangement() {
	}
	
}
