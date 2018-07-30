package com.babifood.clocked.rule;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.babifood.clocked.dao.ClokedHolidayDao;
import com.babifood.clocked.dao.WorkCalendarDao;
import com.babifood.clocked.dao.impl.ClockedHolidayDaoImpl;
import com.babifood.clocked.dao.impl.WorkCalendarDaoImpl;
import com.babifood.clocked.entrty.BasicWorkCalendar;
import com.babifood.clocked.entrty.ClockedResultBases;
import com.babifood.clocked.entrty.Holiday;
import com.babifood.clocked.entrty.Person;
import com.babifood.clocked.entrty.SpecialWorkCalendar;
import com.babifood.utils.UtilDateTime;

/**
 * 判断某人某天是否需要上班-规则
 * @author BABIFOOD
 *
 */
@Service
public class ClockedYesNoRule {
	// 入离职标志
	public static final int Clock_Flag_IN_OUT_JOB = -1;
	// 不需要考勤
	public static final int Clock_Flag_NOT_WORK = 0;
	// 工作日
	public static final int Clock_Flag_WORK_NORMAL = 1;
	// 休息日上班
	public static final int Clock_Flag_WEEK_END_YES = 2;
	// 节假日上班---不会发生，按加班处理
	public static final int Clock_Flag_HOLIDAY_YES = 3;

	// 节假日数据集
	private List<Holiday> holidayList;

	// 特殊排班日数据集
	private List<BasicWorkCalendar> basicworkTimeList;
	private List<SpecialWorkCalendar> specialworkTimeList;
	@Autowired
	ClokedHolidayDao holiday;
	@Autowired
	WorkCalendarDao calendar;
	//初始化节假日信息和排班信息
	public void init(int year, int month){
		//1读取节假日信息
		//ClokedHolidayDao holiday = new ClockedHolidayDaoImpl();
		holidayList = holiday.loadHolidayDatas(year);
		//2读取排班信息
		//WorkCalendarDao calendar = new WorkCalendarDaoImpl();
		basicworkTimeList = calendar.loadBasicWorkCalendarDatas(year, month);
		specialworkTimeList = calendar.loadSpecialWorkCalendarDatas(year, month);		
	}
	/**
	 * 设置标准上下班时间及工作时长
	 * @param tmpResult
	 * @throws Exception 
	 */
	public void setStandWorkTime(Person person,ClockedResultBases tmpResult,Date systemFrontDate) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String sysFrontDate = sdf.format(systemFrontDate);
		int size = specialworkTimeList == null ? 0 : specialworkTimeList.size();
		//特殊排班
		for(int i = 0;i<size;i++){
			if(person.getCompanyCode().equals(specialworkTimeList.get(i).getTarget_id())&&sysFrontDate.equals(specialworkTimeList.get(i).getDate())){
				tmpResult.setBeginTime(specialworkTimeList.get(i).getStart_time());
				tmpResult.setEndTime(specialworkTimeList.get(i).getEnd_time());
				//排班类型
				tmpResult.setPaiBanType(specialworkTimeList.get(i).getArrangement_type());
				break;
			}else if(person.getOrganCode().equals(specialworkTimeList.get(i).getTarget_id())&&sysFrontDate.equals(specialworkTimeList.get(i).getDate())){
				tmpResult.setBeginTime(specialworkTimeList.get(i).getStart_time());
				tmpResult.setEndTime(specialworkTimeList.get(i).getEnd_time());
				//排班类型
				tmpResult.setPaiBanType(specialworkTimeList.get(i).getArrangement_type());
				break;
			}else if(person.getDeptCode().equals(specialworkTimeList.get(i).getTarget_id())&&sysFrontDate.equals(specialworkTimeList.get(i).getDate())){
				tmpResult.setBeginTime(specialworkTimeList.get(i).getStart_time());
				tmpResult.setEndTime(specialworkTimeList.get(i).getEnd_time());
				//排班类型
				tmpResult.setPaiBanType(specialworkTimeList.get(i).getArrangement_type());
				break;
			}else if(person.getOfficeCode().equals(specialworkTimeList.get(i).getTarget_id())&&sysFrontDate.equals(specialworkTimeList.get(i).getDate())){
				tmpResult.setBeginTime(specialworkTimeList.get(i).getStart_time());
				tmpResult.setEndTime(specialworkTimeList.get(i).getEnd_time());
				//排班类型
				tmpResult.setPaiBanType(specialworkTimeList.get(i).getArrangement_type());
				break;
			}else if(person.getGroupCode().equals(specialworkTimeList.get(i).getTarget_id())&&sysFrontDate.equals(specialworkTimeList.get(i).getDate())){
				tmpResult.setBeginTime(specialworkTimeList.get(i).getStart_time());
				tmpResult.setEndTime(specialworkTimeList.get(i).getEnd_time());
				//排班类型
				tmpResult.setPaiBanType(specialworkTimeList.get(i).getArrangement_type());
				break;
			}else if(person.getWorkNum().equals(specialworkTimeList.get(i).getTarget_id())&&sysFrontDate.equals(specialworkTimeList.get(i).getDate())){
				tmpResult.setBeginTime(specialworkTimeList.get(i).getStart_time());
				tmpResult.setEndTime(specialworkTimeList.get(i).getEnd_time());
				//排班类型
				tmpResult.setPaiBanType(specialworkTimeList.get(i).getArrangement_type());
				break;
			}
		}
		//基本排班
		if(tmpResult.getBeginTime()==null&&tmpResult.getEndTime()==null){
			size = basicworkTimeList == null ? 0 : basicworkTimeList.size();
			for(int i = 0;i<basicworkTimeList.size();i++){
				if(person.getCompanyCode().equals(basicworkTimeList.get(i).getTarget_id())){
					tmpResult.setBeginTime(basicworkTimeList.get(i).getStart_time());
					tmpResult.setEndTime(basicworkTimeList.get(i).getEnd_time());
					//排班类型
					tmpResult.setPaiBanType(basicworkTimeList.get(i).getArrangement_type());
					break;
				}else if(person.getOrganCode().equals(basicworkTimeList.get(i).getTarget_id())){
					tmpResult.setBeginTime(basicworkTimeList.get(i).getStart_time());
					tmpResult.setEndTime(basicworkTimeList.get(i).getEnd_time());
					//排班类型
					tmpResult.setPaiBanType(basicworkTimeList.get(i).getArrangement_type());
					break;
				}else if(person.getDeptCode().equals(basicworkTimeList.get(i).getTarget_id())){
					tmpResult.setBeginTime(basicworkTimeList.get(i).getStart_time());
					tmpResult.setEndTime(basicworkTimeList.get(i).getEnd_time());
					//排班类型
					tmpResult.setPaiBanType(basicworkTimeList.get(i).getArrangement_type());
					break;
				}else if(person.getOfficeCode().equals(basicworkTimeList.get(i).getTarget_id())){
					tmpResult.setBeginTime(basicworkTimeList.get(i).getStart_time());
					tmpResult.setEndTime(basicworkTimeList.get(i).getEnd_time());
					//排班类型
					tmpResult.setPaiBanType(basicworkTimeList.get(i).getArrangement_type());
					break;
				}else if(person.getGroupCode().equals(basicworkTimeList.get(i).getTarget_id())){
					tmpResult.setBeginTime(basicworkTimeList.get(i).getStart_time());
					tmpResult.setEndTime(basicworkTimeList.get(i).getEnd_time());
					//排班类型
					tmpResult.setPaiBanType(basicworkTimeList.get(i).getArrangement_type());
					break;
				}else if(person.getWorkNum().equals(basicworkTimeList.get(i).getTarget_id())){
					tmpResult.setBeginTime(basicworkTimeList.get(i).getStart_time());
					tmpResult.setEndTime(basicworkTimeList.get(i).getEnd_time());
					//排班类型
					tmpResult.setPaiBanType(basicworkTimeList.get(i).getArrangement_type());
					break;
				}
			}
		}
		//计算标准工作时长
		String strBeginTime = tmpResult.getBeginTime()+":00";
		String strEndTime = tmpResult.getEndTime()+":00";
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
		Date beginTime =df.parse(strBeginTime);
		Date endTime = df.parse(strEndTime);
		long  l = endTime.getTime()-beginTime.getTime();
		double hour=(l/(60*60*1000));
		//如果当天上班时间大于4个小时着需要减去一个小时的休息时间
		tmpResult.setStandWorkLength(hour>4?hour-1:hour);
	}
	/**
	 * 设置考勤标志
	 * @param tmpResult
	 * @throws Exception 
	 */
	public void markClockedYesNo(Person person,ClockedResultBases tmpResult) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//1根据正常的工作日和周六周日设置考勤标志
		Date systemFrontDate = tmpResult.getCheckingDate();
		String week = UtilDateTime.getWeekOfDate(systemFrontDate);
		if("周六".equals(week)||"周日".equals(week)){
			tmpResult.setClockFlag(Clock_Flag_NOT_WORK);
		}else{
			tmpResult.setClockFlag(Clock_Flag_WORK_NORMAL);
		}
		//2根据节假日设置考勤标志
		int size = holidayList == null ? 0 : holidayList.size();
		Holiday tmpHoliday = null;
		for (int i = 0; i < size; i++) {
			tmpHoliday = holidayList.get(i);
			Date beginDate = sdf.parse(tmpHoliday.getBeginDate());
			Date endDate = sdf.parse(tmpHoliday.getEndDate());
			// 在假日内
			if (UtilDateTime.betweenByDay(tmpResult.getCheckingDate(), beginDate, endDate)) {
				// 考勤标志
				tmpResult.setClockFlag(Clock_Flag_NOT_WORK);
			}
		}
		//3根据特殊排班设置考勤标志
		size = specialworkTimeList == null ? 0 : specialworkTimeList.size();
		String sysFrontDate = sdf.format(systemFrontDate);
		for(int j=0;j<size;j++){
			if(person.getCompanyCode().equals(specialworkTimeList.get(j).getTarget_id())&&sysFrontDate.equals(specialworkTimeList.get(j).getDate())){
				tmpResult.setClockFlag(Clock_Flag_WORK_NORMAL);
				break;
			}else if(person.getOrganCode().equals(specialworkTimeList.get(j).getTarget_id())&&sysFrontDate.equals(specialworkTimeList.get(j).getDate())){
				tmpResult.setClockFlag(Clock_Flag_WORK_NORMAL);
				break;
			}else if(person.getDeptCode().equals(specialworkTimeList.get(j).getTarget_id())&&sysFrontDate.equals(specialworkTimeList.get(j).getDate())){
				tmpResult.setClockFlag(Clock_Flag_WORK_NORMAL);
				break;
			}else if(person.getOfficeCode().equals(specialworkTimeList.get(j).getTarget_id())&&sysFrontDate.equals(specialworkTimeList.get(j).getDate())){
				tmpResult.setClockFlag(Clock_Flag_WORK_NORMAL);
				break;
			}else if(person.getGroupCode().equals(specialworkTimeList.get(j).getTarget_id())&&sysFrontDate.equals(specialworkTimeList.get(j).getDate())){
				tmpResult.setClockFlag(Clock_Flag_WORK_NORMAL);
				break;
			}else if(person.getWorkNum().equals(specialworkTimeList.get(j).getTarget_id())&&sysFrontDate.equals(specialworkTimeList.get(j).getDate())){
				tmpResult.setClockFlag(Clock_Flag_WORK_NORMAL);
				break;
			}
		}
		Date inDate = null;
		Date outDate= null;
		int year = 0;
		int month = 0;
		String sysCheckingDate = sdf.format(tmpResult.getCheckingDate());
		year = Integer.parseInt(sysCheckingDate.substring(0,4));
		month = Integer.parseInt(sysCheckingDate.substring(6,7));
		if(!person.getInDate().equals("")&&person.getInDate()!=null){
			inDate = sdf.parse(person.getInDate());
			int inYear = Integer.parseInt(person.getInDate().substring(0,4));
			int inMonth = Integer.parseInt(person.getInDate().substring(6,7));
			if(year==inYear&&month==inMonth){
				if(tmpResult.getCheckingDate().getTime()<=inDate.getTime()){
					tmpResult.setClockFlag(Clock_Flag_NOT_WORK);
					tmpResult.setInOutJob(Clock_Flag_IN_OUT_JOB);
				}
			}
		}
		if(!person.getOutDate().equals("")&&person.getOutDate()!=null){
			outDate= sdf.parse(person.getOutDate());
			int outYear = Integer.parseInt(person.getOutDate().substring(0,4));
			int outMonth = Integer.parseInt(person.getOutDate().substring(6,7));
			if(year==outYear&&month==outMonth){
				if(tmpResult.getCheckingDate().getTime()>=outDate.getTime()){
					tmpResult.setClockFlag(Clock_Flag_NOT_WORK);
					tmpResult.setInOutJob(Clock_Flag_IN_OUT_JOB);
				}
			}
		}
		 
	}
	public static void main(String[] args)  {
		
		
	}
}
