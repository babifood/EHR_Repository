package com.babifood.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.babifood.entity.ClockedResultBase;
import com.babifood.utils.UtilDateTime;

@Service
public class ClockedDataServiceimpl {
	//系统当前时间减一天
	private Date systemDate = null;
	//考勤人员信息
	List<ClockedResultBase> clockedPersonList;
	public void init(){
		//获取日期
		systemDate = UtilDateTime.getSystemFrontDate();
		//获取需要考勤的人员信息
		clockedPersonList = null;//(加载考勤人员信息方法)
		int clockedPersonSize = clockedPersonList == null ? 0 : clockedPersonList.size();
		
		//系统定时任务计算当天考勤数据（因在1点钟以后计算，应为移动考勤的数据抓取是在1点钟开始的）
		//初始化当天的考勤数据(根据人员的入职日期和离职日期判断（如果当天有人入职着初始化考勤数据，如果当天有人离职着不计算考勤数据）)
		
	}
	public void execute(){
		//加载数据
		//计算打卡
		//计算业务数据
		//结果推送OA
	}
	public void destory(){
		//释放资源
	}
	
}
