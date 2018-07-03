package com.babifood.control.scheduled;

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
	private AnnualLeaveService AnnualLeaveService;

	@Scheduled(cron = "0 54 9 * * ?")
	public void getAnuualLeave(){
		List<Map<String, Object>> PersonlistNow = AnnualLeaveService.GetHireDate();
		List<Map<String, Object>> PersonlistNew = AnnualLeaveService.setCompanyDayFlag(PersonlistNow);
		List<Map<String, Object>> PersonAnnualLeavelist = AnnualLeaveService.setAnnualLeavelist(PersonlistNew);
		if(PersonAnnualLeavelist.isEmpty()){
			 System.out.println("没有需要更新的年假记录！");
		}else{
			 int[] rows=AnnualLeaveService.SaveAnnualLeave(PersonAnnualLeavelist);
			 System.out.println("插入“历史年假表”数据"+rows[0]+"条");
			 System.out.println("删除“当前年假表”中需更新人员数据"+rows[1]+"条");
			 System.out.println("插入“当前年假表”中最新人员数据"+rows[2]+"条");
		}
     }
}

