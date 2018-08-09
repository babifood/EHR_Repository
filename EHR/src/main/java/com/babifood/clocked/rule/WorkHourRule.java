package com.babifood.clocked.rule;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.babifood.clocked.entrty.ClockedResultBases;
import com.babifood.utils.UtilDateTime;
/**
 * 工作时间--小时数--计算公式
 * 
 */
@Service
public class WorkHourRule {
	/**
	 * 计算移动打卡--某一天的工作时长
	 * 
	 * @param theBeginTime
	 *            开始时间
	 * @param theEndTime
	 *            结束时间
	 * @param theWorkBeginTimeStr
	 *            上班作业时间-字符，比如08:30
	 * @param theWorkEndTimeStr
	 *            下班作业时间-字符，比如17:30
	 * @return
	 * @throws Exception 
	 */
	public static double getMobileWorkHoursBySameDay(String theBeginTime, String theEndTime, ClockedResultBases theClockedResult, boolean isYuanShi) throws Exception {
		double value = 0.0d;
		// 打卡地点
		String daKaLocation = theClockedResult.getDaKaLocation();

		Date tmpBegin = getDateWithSecond0(theBeginTime);
		Date tmpEnd = getDateWithSecond0(theEndTime);

		if ("门店-门店".equals(daKaLocation)) {
			double standWorkTimeLength = theClockedResult.getStandWorkLength();
			value = getWorkTimeLength(tmpBegin, tmpEnd);
			if (standWorkTimeLength == 6) {
				// 小周 6小时工作制
				if (value >= 4.5d) {
					if (isYuanShi) {
						return value;
					}
					value = 6d;
				} else {
					value = getMobileWorkHoursBySameDay_8Hour(tmpBegin, tmpEnd, theClockedResult);
				}
			} else if (standWorkTimeLength == 8) {
				// 8小时工作制
				if (value >= 6d) {
					if (isYuanShi) {
						return value;
					}
					value = 8d;
				} else {
					value = getMobileWorkHoursBySameDay_8Hour(tmpBegin, tmpEnd, theClockedResult);
				}
			}
		} else {
			value = getMobileWorkHoursBySameDay_8Hour(tmpBegin, tmpEnd, theClockedResult);
		}
		return value;
	}
	/**
	 * 将时间置秒/毫秒数为0
	 * @throws Exception 
	 */
	public static Date getDateWithSecond0(String stamp) throws Exception {
		SimpleDateFormat dftime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date value = null;
		if(stamp.length()>0){
			value=dftime.parse(stamp);
		}
		return value;
	}
	private static double getWorkTimeLength(Date tmpBegin, Date tmpEnd) {
		double value = 0d;
		int[] x = UtilDateTime.getTimeInterval(tmpBegin, tmpEnd);
		if (x[2] >= 30) {
			value = x[1] + 0.5;
		} else {
			value = x[1];
		}
		return value;
	}
	public static double getMobileWorkHoursBySameDay_8Hour(Date tmpBegin, Date tmpEnd, ClockedResultBases theClockedResult) throws Exception {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		double value = 0.0d;
		// 中间休息时段
		Date xiuBeginTime = getDateWithSecond0(df.format(theClockedResult.getCheckingDate())+" 12:00:00");
		Date xiuEndTime = getDateWithSecond0(df.format(theClockedResult.getCheckingDate())+" 13:00:00");

		// 开始时间 在 12：00-13：00之间
		if (xiuBeginTime != null && xiuEndTime != null && tmpBegin.getTime() >= xiuBeginTime.getTime() && tmpBegin.getTime() <= xiuEndTime.getTime()) {
			// 结束时间 在 12：00-13：00之间
			if (xiuBeginTime != null && xiuEndTime != null && tmpEnd.getTime() >= xiuBeginTime.getTime() && tmpEnd.getTime() <= xiuEndTime.getTime()) {
				return 0;
			}
		}

		// 开始时间 在 12：00-13：00之间
		if (xiuBeginTime != null && xiuEndTime != null && tmpBegin.getTime() >= xiuBeginTime.getTime() && tmpBegin.getTime() <= xiuEndTime.getTime()) {
			tmpBegin = xiuEndTime;
		}

		// 结束时间 在 12：00-13：00之间
		if (xiuBeginTime != null && xiuEndTime != null && tmpEnd.getTime() >= xiuBeginTime.getTime() && tmpEnd.getTime() <= xiuEndTime.getTime()) {
			tmpEnd = xiuBeginTime;
		}

		value = getWorkTimeLength(tmpBegin, tmpEnd);

		// 打卡开始时间发生在中途休息前，并且打卡结束时间发生在中途休息后，则扣除中间休息的一小时
		if (xiuBeginTime != null && xiuEndTime != null && tmpBegin.getTime() <= xiuBeginTime.getTime() && tmpEnd.getTime() >= xiuEndTime.getTime()) {
			value = value - 1;
		}

		if (value > 8d) {
			value = 8d;
		}
		
		if(value < 0 ){
			value = 0;
		}

		return value;
	}
	/**
	 * 计算办公室打卡--某一天的工作时长
	 * 
	 * @param theBeginTime
	 *            开始时间
	 * @param theEndTime
	 *            结束时间
	 * @param theWorkBeginTimeStr
	 *            上班作业时间-字符，比如08:30
	 * @param theWorkEndTimeStr
	 *            下班作业时间-字符，比如17:30
	 * @return
	 * @throws Exception 
	 */
	public static double getOfficeWorkHoursBySameDay(String theBeginTime, String theEndTime, ClockedResultBases theClockedResult) throws Exception {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		double value = 0.0d;
		Date tmpBegin = getDateWithSecond0(theBeginTime);
		Date tmpEnd = getDateWithSecond0(theEndTime);
		// 此天的标准工作时间
		Date standBeginTime = getDateWithSecond0(df.format(theClockedResult.getCheckingDate())+" "+theClockedResult.getBeginTime()+":00");
		Date standEndTime = getDateWithSecond0(df.format(theClockedResult.getCheckingDate())+" "+theClockedResult.getEndTime()+":00");
		// ======================正常范围=============================
		if (tmpBegin.getTime() <= standBeginTime.getTime() && tmpEnd.getTime() >= standEndTime.getTime()) {
			int[] x = UtilDateTime.getTimeInterval(standBeginTime, standEndTime);
			// 减去中午休息一个小时
			value = x[1]-1;
			return value;
		}
		// ======================处理迟到或早退现象=============================
		// 中间休息时段
		Date xiuBeginTime = getDateWithSecond0(df.format(theClockedResult.getCheckingDate())+" 12:00:00");
		Date xiuEndTime = getDateWithSecond0(df.format(theClockedResult.getCheckingDate())+" 13:00:00");
		if (tmpBegin.getTime() <= standBeginTime.getTime()) {
			tmpBegin = standBeginTime;
		} else {
			// 开始时间 在 12：00-13：00之间
			if (xiuBeginTime != null && xiuEndTime != null && tmpBegin.getTime() >= xiuBeginTime.getTime() && tmpBegin.getTime() <= xiuEndTime.getTime()) {
				tmpBegin = xiuEndTime;
			}
		}
		if (tmpEnd.getTime() >= standEndTime.getTime()) {
			tmpEnd = standEndTime;
		} else {
			// 结束时间 在 12：00-13：00之间
			if (xiuBeginTime != null && xiuEndTime != null && tmpEnd.getTime() >= xiuBeginTime.getTime() && tmpEnd.getTime() <= xiuEndTime.getTime()) {
				tmpEnd = xiuBeginTime;
			}
		}
		value = getWorkTimeLength(tmpBegin, tmpEnd);
		// 打卡开始时间发生在中途休息前，并且打卡结束时间发生在中途休息后，则扣除中间休息的一小时
		if (xiuBeginTime != null && xiuEndTime != null && tmpBegin.getTime() <= xiuBeginTime.getTime() && tmpEnd.getTime() >= xiuEndTime.getTime()) {
			value = value - 1;
		}
		return value;
	}
	public static Date getStandBeginTime(Date checkingDate, String beginTime){
		// TODO Auto-generated method stub
		DateFormat dftime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date standBeginTime = null;
		String s = df.format(checkingDate) + " " + beginTime + ":00";
		try {
			standBeginTime = dftime.parse(s);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return standBeginTime;
	}
	public static Date getStandEndTime(Date checkingDate,String endTime,String beginTime){
		// TODO Auto-generated method stub
		DateFormat dftime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date standEndTime = null;
		String s = df.format(checkingDate) + " " + endTime + ":00";
		try {
			standEndTime = dftime.parse(s);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date standBeginTime = null;
		s = df.format(checkingDate) + " " + beginTime + ":00";
		try {
			standBeginTime = df.parse(s);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (standBeginTime.after(standEndTime)) {
			standEndTime = getTimeDelayOneDay(standEndTime, 1);
		}
		return standEndTime;
		
	}
	public static Date getTimeDelayOneDay(Date stamp, int daysLater) {
		Calendar tempCal = Calendar.getInstance();
		tempCal.setTime(new java.util.Date(stamp.getTime()));
		tempCal.set(tempCal.get(Calendar.YEAR), tempCal.get(Calendar.MONTH), tempCal.get(Calendar.DAY_OF_MONTH), tempCal.get(Calendar.HOUR_OF_DAY), tempCal.get(Calendar.MINUTE), 0);
		tempCal.add(Calendar.DAY_OF_MONTH, daysLater);
		java.sql.Timestamp retStamp = new java.sql.Timestamp(tempCal.getTime().getTime());
		retStamp.setNanos(0);
		return retStamp;
	}
	public static double getQingJiaHoursBySameDay(Date theBeginTime, Date theEndTime, ClockedResultBases theClockedResult) throws Exception {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		double value = 0.0d;
		Date tmpBegin = theBeginTime;
		Date tmpEnd = theEndTime;
		// 此天的标准工作时间
		Date standBeginTime = theClockedResult.getStandBeginTime();
		Date standEndTime = theClockedResult.getStandEndTime();
		// ======================正常范围=============================
		if (tmpBegin.getTime() <= standBeginTime.getTime() && tmpEnd.getTime() >= standEndTime.getTime()) {
			int[] x = UtilDateTime.getTimeInterval(standBeginTime, standEndTime);
			value = x[1];
			// 存在休息情况
//			if (theClockedResult.getXiuBeginTime() != null) {
//				value = value - 1;
//			}
			return value;
		}
		// ======================处理迟到或早退现象=============================
		// 中间休息时段
//		Date xiuBeginTime = theClockedResult.getXiuBeginTime();
//		Date xiuEndTime = theClockedResult.getXiuEndTime();
		Date xiuBeginTime = getDateWithSecond0(df.format(theClockedResult.getCheckingDate())+" 12:00:00");
		Date xiuEndTime = getDateWithSecond0(df.format(theClockedResult.getCheckingDate())+" 13:00:00");
		if (tmpBegin.getTime() <= standBeginTime.getTime()) {
			tmpBegin = standBeginTime;
		} else {
			// 开始时间 在 12：00-13：00之间
			if (xiuBeginTime != null && xiuEndTime != null && tmpBegin.getTime() >= xiuBeginTime.getTime() && tmpBegin.getTime() <= xiuEndTime.getTime()) {
				tmpBegin = xiuEndTime;
			}
		}
		if (tmpEnd.getTime() >= standEndTime.getTime()) {
			tmpEnd = standEndTime;
		} else {
			// 结束时间 在 12：00-13：00之间
			if (xiuBeginTime != null && xiuEndTime != null && tmpEnd.getTime() >= xiuBeginTime.getTime() && tmpEnd.getTime() <= xiuEndTime.getTime()) {
				tmpEnd = xiuBeginTime;
			}
		}
		// 计算时间长度
		value = getQingJiaTimeLength(tmpBegin, tmpEnd);

		// 打卡开始时间发生在中途休息前，并且打卡结束时间发生在中途休息后，则扣除中间休息的一小时
		if (xiuBeginTime != null && xiuEndTime != null && tmpBegin.getTime() <= xiuBeginTime.getTime() && tmpEnd.getTime() >= xiuEndTime.getTime()) {
			value = value - 1;
		}

		return value;
	}
	public static boolean isKuaTianOnEndTime(ClockedResultBases theResult, java.util.Date theBizBeginTime, java.util.Date theBizEndTime) {
		boolean flag = false;
		if (theResult.isDelayOneDay()) {
			if (theBizEndTime.getTime() > getTimeDelayOneDay(theResult.getStandEndTime(), 1).getTime()) {
				flag = true;
			}
		} else {
			flag = (UtilDateTime.getDaysBetween(theBizBeginTime, theBizEndTime)) > 0;
		}
		return flag;
	}

	public static boolean isKuaTianOnBeginTime(ClockedResultBases theResult, java.util.Date theBizBeginTime, java.util.Date theBizEndTime) {
		boolean flag = false;
		if (theResult.isDelayOneDay()) {
			if (theResult.getStandBeginTime().getTime() > getTimeDelayOneDay(theBizBeginTime, 1).getTime()) {
				flag = true;
			}
		} else {
			flag = (UtilDateTime.getDaysBetween(theBizBeginTime, theBizEndTime)) > 0;
		}
		return flag;
	}
	private static double getQingJiaTimeLength(Date tmpBegin, Date tmpEnd) {
		double value = 0d;
		int[] x = UtilDateTime.getTimeInterval(tmpBegin, tmpEnd);
		if (x[2] > 30) {
			value = x[1] + 1;
		} else if ((x[2] <= 30) && (x[2] > 0)) {
			value = x[1] + 0.5d;
		} else {
			value = x[1];
		}
		return value;
	}
}
