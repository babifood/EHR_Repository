package com.babifood.clocked.rule;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.stereotype.Service;

import com.babifood.clocked.entrty.ClockedBizData;
import com.babifood.clocked.entrty.ClockedResultBases;
import com.babifood.utils.UtilDateTime;

/**
 * 移动-考勤业务数据--加班/出差/请假/异动计算规则
 * @author BABIFOOD
 *
 */
@Service
public class MobileCalcRule {
	public void attach(ClockedResultBases theResult, List<ClockedBizData> theClockedBizDataList) throws Exception {
		if (theClockedBizDataList != null) {
			int size = theClockedBizDataList.size();
			for (int i = 0; i < size; i++) {
				attachOne(theResult, theClockedBizDataList.get(i));
			}
		}

		calculate(theResult);
	}
	/*
	 * 对最终的数据进行计算
	 */
	private void calculate(ClockedResultBases theResult) throws Exception {
		DateFormat dftime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 对不需要上班的日期，进行处理
		if (theResult.getClockFlag() < 1) {
			theResult.setEventBeginTime(null);
			theResult.setEventEndTime(null);
			theResult.setBeginTime("");
			theResult.setEndTime("");

			theResult.setYiDong(0d);
			theResult.setChuCha(0d);
			theResult.setQingJia(0d);
			theResult.setShiJia(0d);
			theResult.setBingJia(0d);
			theResult.setHunJia(0d);
			theResult.setSangJia(0d);
			theResult.setNianJia(0d);
			theResult.setOtherQingJia(0d);
			

			theResult.setChiDao(0);
			theResult.setZaoTui(0);
			theResult.setKuangGong(0d);

			// 对于加班的数据，不能清空,比如周末加班
			// theResult.setJiaBan(0);
			if (theResult.getCheckingBeginTime() == null || theResult.getCheckingEndTime() == null) {
				theResult.setOriginalCheckingLength(0d);;
			}
			
			// 餐补个数
			ClockedBaseRule.calcCanBu(theResult);
		} else {
			// 计算工作时长
			double workHours = 0;
			if (theResult.getYiDong() <= 0 && theResult.getQingJia() <= 0) {
				if (theResult.getCheckingBeginTime() != null && theResult.getCheckingEndTime() != null) {
					workHours = WorkHourRule.getMobileWorkHoursBySameDay(dftime.format(theResult.getCheckingBeginTime()), dftime.format(theResult.getCheckingEndTime()), theResult,false);
				}
			} else {
				workHours = theResult.getOriginalCheckingLength() + theResult.getYiDong();
				if (workHours > theResult.getStandWorkLength()) {
					workHours = theResult.getStandWorkLength();
					workHours = workHours - theResult.getQingJia();
					workHours = workHours < 0 ? 0 : workHours;
				}
			}
			theResult.setOriginalCheckingLength(workHours);
			theResult.setActualWorkLength(workHours);
			// 计算最终有效时长
			double finalHours = 0;
			finalHours = workHours + theResult.getQingJia();
			if (finalHours > theResult.getStandWorkLength()) {
				finalHours = theResult.getStandWorkLength();
			}
			// 缺勤时长
			theResult.setQueQin(theResult.getStandWorkLength() - finalHours);

			// 没有缺勤
			if (finalHours>=theResult.getStandWorkLength()) {
				theResult.setChiDao(0);
				theResult.setZaoTui(0);
				theResult.setKuangGong(0d);
			} else {
				theResult.setChiDao(0);
				theResult.setZaoTui(0);
				theResult.setKuangGong(theResult.getQueQin());
			}
			// 餐补个数
			ClockedBaseRule.calcCanBu(theResult);
		}
	}
	/*
	 * 适配
	 */
	private void attachOne(ClockedResultBases theResult, ClockedBizData theClockedBizData) throws Exception{
		// 考勤日在业务发生日之前&&考勤日在业务发生日之后，退出
		if (!UtilDateTime.betweenByDay(theResult.getCheckingDate(), theClockedBizData.getBeginTime(), theClockedBizData.getEndTime())) {
			return;
		}
		// 单据类型
		String billType = theClockedBizData.getBillType();
		billType = billType == null ? "" : billType.trim();
		if (billType.equals("加班申请单")) {
			attachOneJiaBan(theResult, theClockedBizData);
		} else if (billType.equals("考勤异动单")) {
			attachOneYiDong(theResult, theClockedBizData);
		} else if (billType.equals("移动请假单")) {
			attachOneQingJia(theResult, theClockedBizData);
		} else {
			// do nothing
		}
	}
	/*
	 * 计算异动
	 */
	private void attachOneYiDong(ClockedResultBases theResult, ClockedBizData theClockedBizData) throws Exception {
		// 如果原始打卡时长为正常
		if (theResult.getOriginalCheckingLength() >= theResult.getStandWorkLength()) {
			return;
		}
		double value = WorkHourRule.getMobileWorkHoursBySameDay_8Hour(theClockedBizData.getBeginTime(), theClockedBizData.getEndTime(), theResult);
		theResult.setYiDong(theResult.getYiDong() + value);
		if (theResult.getYiDong() > theResult.getStandWorkLength()) {
			theResult.setYiDong(theResult.getStandWorkLength());
		}
	}

	/*
	 * 计算请假
	 */
	private void attachOneQingJia(ClockedResultBases theResult, ClockedBizData theClockedBizData) {
		double value = theClockedBizData.getTimeLength();
		if ("年假".equals(theClockedBizData.getBizFlag1())) {
			theResult.setNianJia(value);
		} else if ("事假".equals(theClockedBizData.getBizFlag1())) {
			theResult.setShiJia(value);
		} else if ("病假".equals(theClockedBizData.getBizFlag1())) {
			theResult.setBingJia(value);
		} else if ("婚假".equals(theClockedBizData.getBizFlag1())) {
			theResult.setHunJia(value);
		} else if ("丧假".equals(theClockedBizData.getBizFlag1())) {
			theResult.setSangJia(value);
		} else if ("培训假".equals(theClockedBizData.getBizFlag1())) {
			theResult.setPeiXunJia(value);
		} else if ("产假".equals(theClockedBizData.getBizFlag1())) {
			theResult.setChanJia(value);
		} else if ("陪产假".equals(theClockedBizData.getBizFlag1())) {
			theResult.setPeiChanJia(value);
		} else if ("调休".equals(theClockedBizData.getBizFlag1())) {
			theResult.setTiaoXiu(value);
		} else {
			theResult.setOtherQingJia(value);
		}
		// 设置请假时数
		theResult.setQingJia(value);
	}

	/*
	 * 计算加班
	 */
	private void attachOneJiaBan(ClockedResultBases theResult, ClockedBizData theClockedBizData) {
		if (UtilDateTime.getDaysBetween(theResult.getCheckingDate(), theClockedBizData.getBeginTime()) == 0) {
			//如果考勤标志等于1(注释:1表示工作日)&&加班业务的开始时间>=标注上班时间&&加班业务的结束时间<=标注的下班时间
			if (theResult.getClockFlag() == 1 && theClockedBizData.getBeginTime().getTime() >= theResult.getStandBeginTime().getTime() && theClockedBizData.getEndTime().getTime() <= theResult.getStandEndTime().getTime()) {
				return;
			}
			theResult.setJiaBan(theClockedBizData.getTimeLength());
		}
	}
	
}
