package com.babifood.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public final class UtilDateTime {
	
	// 获取某年份和月份的开始时间
	public static java.sql.Date getMonthStartSqlDate(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, 1, 0, 0, 0);
		java.sql.Date retStamp = new java.sql.Date(calendar.getTime().getTime());
		return retStamp;
	}

	// 获取某年份和月份的结束时间
	public static java.sql.Date getMonthEndSqlDate(int year, int month) {
		java.sql.Timestamp nextStamp = getMonthStart(year, month + 1);
		java.sql.Timestamp endMonthDay = getDayEnd(nextStamp, -1);
		return new java.sql.Date(endMonthDay.getTime());
	}

	// 获取某年份和月份的开始时间
	public static java.sql.Timestamp getMonthStart(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, 1, 0, 0, 0);
		java.sql.Timestamp retStamp = new java.sql.Timestamp(calendar.getTime().getTime());
		retStamp.setNanos(0);
		return retStamp;
	}

	// 获取某年份和月份的结束时间
	public static java.sql.Timestamp getMonthEnd(int year, int month) {
		java.sql.Timestamp nextStamp = getMonthStart(year, month + 1);
		java.sql.Timestamp endMonthDay = getDayEnd(nextStamp, -1);
		return endMonthDay;
	}

	public static java.sql.Timestamp getDayEnd(java.sql.Timestamp stamp, int daysLater) {
		Calendar tempCal = Calendar.getInstance();
		tempCal.setTime(new java.util.Date(stamp.getTime()));
		tempCal.set(tempCal.get(Calendar.YEAR), tempCal.get(Calendar.MONTH), tempCal.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
		tempCal.add(Calendar.DAY_OF_MONTH, daysLater);
		java.sql.Timestamp retStamp = new java.sql.Timestamp(tempCal.getTime().getTime());
		retStamp.setNanos(999999999);
		return retStamp;
	}

	public static java.sql.Timestamp getYearStart(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, 0, 1, 0, 0, 0);
		java.sql.Timestamp retStamp = new java.sql.Timestamp(calendar.getTime().getTime());
		retStamp.setNanos(0);
		return retStamp;
	}

	public static java.sql.Timestamp getYearEnd(int year) {
		// java.sql.Timestamp nextFisrtDayOfNextYear = getYearStart(year+1);
		// java.sql.Timestamp endMonthDay =
		// getDayEnd(nextFisrtDayOfNextYear,-1);
		// return endMonthDay;
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, 11, 31, 23, 59, 59);
		java.sql.Timestamp retStamp = new java.sql.Timestamp(calendar.getTime().getTime());
		retStamp.setNanos(999999999);
		return retStamp;
	}

	// 获取某时间所在月的开始时间
	public static java.sql.Timestamp getMonthStart(java.sql.Timestamp stamp) {
		return getMonthStart(stamp, 0);
	}

	public static java.sql.Timestamp getMonthStart(java.sql.Timestamp stamp, int daysLater) {
		Calendar tempCal = Calendar.getInstance();
		tempCal.setTime(new java.util.Date(stamp.getTime()));
		tempCal.set(tempCal.get(Calendar.YEAR), tempCal.get(Calendar.MONTH), 1, 0, 0, 0);
		tempCal.add(Calendar.DAY_OF_MONTH, daysLater);
		java.sql.Timestamp retStamp = new java.sql.Timestamp(tempCal.getTime().getTime());
		retStamp.setNanos(0);
		return retStamp;
	}

	// 获取某时间所在周的开始时间
	public static java.sql.Timestamp getWeekStart(java.sql.Timestamp stamp) {
		return getWeekStart(stamp, 0);
	}

	// 获取某时间所在周的结束时间
	public static java.sql.Timestamp getWeekEnd(java.sql.Timestamp stamp) {
		return getDayEnd(getWeekStart(new java.sql.Timestamp(System.currentTimeMillis())), 6);
	}

	public static java.sql.Timestamp getWeekStart(java.sql.Timestamp stamp, int daysLater) {
		Calendar tempCal = Calendar.getInstance();
		tempCal.setTime(new java.util.Date(stamp.getTime()));
		tempCal.set(tempCal.get(Calendar.YEAR), tempCal.get(Calendar.MONTH), tempCal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		tempCal.add(Calendar.DAY_OF_MONTH, daysLater);
		tempCal.set(Calendar.DAY_OF_WEEK, tempCal.getFirstDayOfWeek());
		java.sql.Timestamp retStamp = new java.sql.Timestamp(tempCal.getTime().getTime());
		retStamp.setNanos(0);
		return retStamp;
	}

	// 获取某一日期的年份
	public static int getYear(java.util.Date date) {
		if (date == null) {
			return -1;
		}
		Calendar tempCal = Calendar.getInstance();
		tempCal.setTime(date);
		return tempCal.get(Calendar.YEAR);
	}

	// 获取某一日期的月份
	public static int getMonth(java.util.Date date) {
		if (date == null) {
			return -1;
		}
		Calendar tempCal = Calendar.getInstance();
		tempCal.setTime(date);
		return tempCal.get(Calendar.MONTH) + 1;
	}

	// 获取某一日期的天数
	public static int getDay(java.util.Date date) {
		Calendar tempCal = Calendar.getInstance();
		tempCal.setTime(date);
		return tempCal.get(Calendar.DAY_OF_MONTH);
	}

	// 获取某年某月的总共天数
	public static int getDaySize(int year, int month) {
		java.util.Date date = getMonthEnd(year, month);
		return getDay(date);
	}

	public static String getWeekOfDate(Date dt) {
		String[] weekDays = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);

		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0) {
			w = 0;
		}

		return weekDays[w];
	}

	public static int getDaysBetween(java.util.Date start, java.util.Date end) {
		boolean negative = false;
		if (end.before(start)) {
			negative = true;
			java.util.Date temp = start;
			start = end;
			end = temp;
		}
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(start);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		GregorianCalendar calEnd = new GregorianCalendar();
		calEnd.setTime(end);
		calEnd.set(Calendar.HOUR_OF_DAY, 0);
		calEnd.set(Calendar.MINUTE, 0);
		calEnd.set(Calendar.SECOND, 0);
		calEnd.set(Calendar.MILLISECOND, 0);
		if (cal.get(Calendar.YEAR) == calEnd.get(Calendar.YEAR)) {
			if (negative) {
				return (calEnd.get(Calendar.DAY_OF_YEAR) - cal.get(Calendar.DAY_OF_YEAR)) * -1;
			}
			return calEnd.get(Calendar.DAY_OF_YEAR) - cal.get(Calendar.DAY_OF_YEAR);
		}
		int counter = 0;
		while (calEnd.after(cal)) {
			cal.add(Calendar.DAY_OF_YEAR, 1);
			counter++;
		}
		if (negative) {
			return counter * -1;
		}
		return counter;
	}

	// MINUTE
	public static long getMinuteBetween(java.util.Date start, java.util.Date end) {
		long diff = end.getTime() - start.getTime();
		long diffMin = diff / (60 * 1000);
		return diffMin;
	}

	// Hour
	public static long getHourBetween(java.util.Date start, java.util.Date end) {
		long diff = end.getTime() - start.getTime();
		long diffHours = diff / (60 * 60 * 1000);
		return diffHours;
	}

	public static boolean betweenByDay(java.util.Date date, java.util.Date start, java.util.Date end) {
		int x1 = UtilDateTime.getDaysBetween(start, date);
		int x2 = UtilDateTime.getDaysBetween(date, end);
		if (x1 < 0 || x2 < 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 计算时间间隔
	 * 
	 * @param begin
	 * @param end
	 * @return
	 */
	public static int[] getTimeInterval(Date begin, Date end) {
		long nd = 1000 * 24 * 60 * 60;
		long nh = 1000 * 60 * 60;
		long nm = 1000 * 60;
		// long ns = 1000;
		// 获得两个时间的毫秒时间差异
		long diff = end.getTime() - begin.getTime();
		// 计算差多少天
		long day = diff / nd;
		// 计算差多少小时
		long hour = diff % nd / nh;
		// 计算差多少分钟
		long min = diff % nd % nh / nm;
		// 计算差多少秒//输出结果
		// long sec = diff % nd % nh % nm / ns;
		// System.out.println(day + "天" + hour + "小时" + min + "分钟");
		int[] x = new int[3];
		x[0] = (int) day;
		x[1] = (int) hour;
		x[2] = (int) min;
		return x;
	}
	
	
	public static java.sql.Date getDayEndByDay(java.util.Date stamp, int daysLater) {
		Calendar tempCal = Calendar.getInstance();
		tempCal.setTime(new java.util.Date(stamp.getTime()));
		tempCal.set(tempCal.get(Calendar.YEAR), tempCal.get(Calendar.MONTH), tempCal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		tempCal.add(Calendar.DAY_OF_MONTH, daysLater);
		java.sql.Date retStamp = new java.sql.Date(tempCal.getTime().getTime());
		return retStamp;
	}
	
	public static java.sql.Date getDayEndByMonth(java.util.Date stamp, int month) {
		Calendar tempCal = Calendar.getInstance();
		tempCal.setTime(new java.util.Date(stamp.getTime()));
		tempCal.set(tempCal.get(Calendar.YEAR), tempCal.get(Calendar.MONTH), tempCal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		tempCal.add(Calendar.MONTH, month);
		java.sql.Date retStamp = new java.sql.Date(tempCal.getTime().getTime());
		return retStamp;
	}
	
	//获取当前时间
	public static String getCurrentTime(String pattern){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date date = new Date();
		return sdf.format(date);
	}
	
	/**
	 * 日期转换
	 * @param date
	 * @param pattern
	 * @return
	 * @throws ParseException
	 */
	public static Date getDate(String date,String pattern) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.parse(date);
	}
	
	/**
	 * 比较当前时间是否在该月之后
	 * @param time
	 * @param pattern
	 * @return
	 */
	public static boolean isLaterThanCurrentTime(String time,String pattern) {
		Boolean flag = false;
		try {
			Date date = getDate(time, pattern);
			if(date.getTime() > new Date().getTime()){
				flag = true;
			}
		} catch (Exception e) {
		}
		return flag;
	}
	
	/**
	 * 获取当指定年、月的当月天数
	 * @return
	 */
	public static int getDaysOfCurrentMonth(int year,int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		return calendar.getActualMaximum(Calendar.DATE);
	}
	
	/**
	 * 获取当前月份
	 * @return
	 */
	public static String getCurrentMonth() {
		Calendar calendar = Calendar.getInstance();
		String month = (calendar.get(Calendar.MONTH) + 1) + "";
		return month.length() == 1 ? "0" + month : month + "";
	}
	
	/**
	 * 获取当前年份
	 * @return
	 */
	public static String getCurrentYear() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.YEAR)+"";
	}
	
	/**
	 * 获取上个月的年份
	 * @return
	 */
	public static String getYearOfPreMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		return calendar.get(Calendar.YEAR)+"";
	}
	
	/**
	 * 获取当前月份
	 * @return
	 */
	public static String getPreMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		String month = (calendar.get(Calendar.MONTH) + 1) + "";
		return month.length() == 1 ? "0" + month : month + "";
	}
	
	/**
	 * 根据时间获取星期
	 * @param time
	 * @return
	 */
	public static String getWeekDay(String time) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String week = "";
		try {
			Date date = simpleDateFormat.parse(time);
			week = getWeekOfDate(date);
		} catch (ParseException e) {
		}
		return week;
	}
	
	/**
	 * 是否工作日
	 * @param time
	 * @param pattern
	 * @return
	 */
	public static Boolean isWorkDay(String time,String pattern) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		boolean isWorkDay = false;
		try {
			Date date = simpleDateFormat.parse(time);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			if(1 < calendar.get(Calendar.DAY_OF_WEEK) && calendar.get(Calendar.DAY_OF_WEEK) < 7){
				isWorkDay = true;
			}
		} catch (Exception e) {
		}
		return isWorkDay;
	}
	
	/**
	 * 根据标准/打卡时间获得标准/打卡上班小时数
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static Double getHours(String startDate,String endDate) {
		double nh = 1000 * 60 * 60;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
		try {
			long seconds = simpleDateFormat.parse(endDate).getTime() - simpleDateFormat.parse(startDate).getTime();
			return Math.round(seconds*100.0/nh)/100.0 - 1;
		} catch (ParseException e) {
		}
		return 0.00;
	}
	
	//获取系统当前日期的前一天
	public static Date getSystemFrontDate(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        Date date=new Date();  
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);  
        calendar.add(Calendar.DAY_OF_MONTH, -1);  
        date = calendar.getTime();  
        return date;
	}

	public static void main(String args[]) {
//		System.out.println(isWorkDay("2018-06-25", "yyyy-MM-dd"));
//		System.out.println(getWeekDay("2018-06-25"));
		System.out.println(getCurrentMonth());
		System.out.println(getCurrentYear());
		System.out.println(getPreMonth());
		System.out.println(getYearOfPreMonth());
//		System.out.println(getDaysOfCurrentMonth(2018, Integer.valueOf("02")));
//		System.out.println(getHours("09:00", "16:20"));
//		System.out.println(getDayEndByMonth(java.sql.Date.valueOf("2015-01-01"),7));
	}

}