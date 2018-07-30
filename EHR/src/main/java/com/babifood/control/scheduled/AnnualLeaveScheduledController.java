package com.babifood.control.scheduled;

import java.util.Calendar;
import java.util.Date;
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

	//@Scheduled(cron = "0 30 00 * * ?")
	public void getAnuualLeave(){
     	//取执行计算的当天
         Date date = new Date();
         Calendar calendar = Calendar.getInstance();//日历对象
         calendar.setTime(date);//设置当前日期
         int nowYear = calendar.get(Calendar.YEAR);//获取年份
         int nowMonth = calendar.get(Calendar.MONTH) + 1;//获取月份
         int FebDay=28;//本年二月的天数,默认28，如果是闰月就29
         if(nowMonth == 2){
             if( nowYear % 4 == 0 && ( nowYear % 100 != 0 || nowYear % 400 == 0)){
             	FebDay = 29;
             }
         }
         //本年每个月的天数
         int[] M=new int[12];
         M[0]=31;M[1]=FebDay;M[2]=31;M[3]=30;M[4]=31;M[5]=30;M[6]=31;M[7]=31;M[8]=30;M[9]=31;M[10]=30;M[11]=31;

         new Thread(new Runnable() {
				@Override
				public void run() {					
			     	List<Map<String, Object>> PersonlistNow = AnnualLeaveService.GetHireDate();
			 		List<Map<String, Object>> PersonlistNew = AnnualLeaveService.setCompanyDayFlag(PersonlistNow,M);
			 		List<Map<String, Object>> PersonAnnualLeavelist = AnnualLeaveService.setAnnualLeavelist(PersonlistNew);
			 		if(PersonAnnualLeavelist.isEmpty()){
			 			System.out.println("没有需要更新的年假记录！");
			 		}else{
			 			int[] rows=AnnualLeaveService.SaveAnnualLeave(PersonAnnualLeavelist,nowYear);
			 			System.out.println("插入“历史年假表”数据"+rows[0]+"条");
			 			System.out.println("删除“当前年假表”中需更新人员数据"+rows[1]+"条");
			 			System.out.println("插入“当前年假表”中最新人员数据"+rows[2]+"条");
			 		}
				}
			}).start();
     }	
}
