package com.babifood.control.scheduled;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.formula.functions.Index;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.babifood.service.ArrangementBaseTimeService;

@Controller
@EnableScheduling
public class ScheduledController {
	
	@Autowired
	private ArrangementBaseTimeService arrangementBaseTimeService;

//	@Scheduled(cron = "1 0 0 1 * ?")
	@RequestMapping("test")
	public void initializeArrangement() {
		Integer count = arrangementBaseTimeService.getCount();
		final int pageSize = 50;
		int num = count%500 == 0 ?count/500:count/500+1;
		for(int i = 0; i < num; i++){
			final int index = i;
			new Thread(new Runnable() {
				@Override
				public void run() {
					System.out.println(Thread.currentThread());
					for(int j = 0; j < 10; j++){
						int pageNum = 10*index + j + 1;
						List<Map<String, Object>> list = arrangementBaseTimeService.findPageBaseTimes(pageNum, pageSize);
						for(Map<String, Object> map:list){
							System.out.println(Thread.currentThread()+""+index+"---" + map.get("arrangement_name"));
						}
					}
					
				}
			}).start();
		}
	}
	
}
