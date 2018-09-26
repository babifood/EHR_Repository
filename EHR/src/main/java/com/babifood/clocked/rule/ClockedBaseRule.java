package com.babifood.clocked.rule;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.babifood.clocked.entrty.ClockedBizData;
import com.babifood.clocked.entrty.ClockedResultBases;
import com.babifood.utils.UtilDateTime;

/*
 * 考勤基本规则
 */
@Service
public class ClockedBaseRule {
	/*
	 * 计算餐补
	 */
	public static void calcCanBu(ClockedResultBases theResult) {
		if (theResult.getClockFlag() == 0) {
			theResult.setCanBu(0);
			if (theResult.getJiaBan() >= 4.5) {
//				String organ = ClockedUtil.getOrgan(theResult.getOrgan()).getName();
//				if (organ.indexOf("集团") != -1 || organ.indexOf("营运") != -1) {
					theResult.setCanBu(1);
//				}
			}
		} else {
			// 餐补个数
			if (theResult.getStandWorkLength() == 8 && theResult.getOriginalCheckingLength() >= 4.5d) {
				theResult.setCanBu(1);
			} else if (theResult.getStandWorkLength() == 6 && theResult.getOriginalCheckingLength() >= 3.5d) {
				theResult.setCanBu(1);
			} else {
				theResult.setCanBu(0);
			}
		}

	}
	/*
	 * 计算一天内的请假--普通考勤
	 */
	public static void calcOneQingJiaByOffice(ClockedResultBases theResult, ClockedBizData theClockedBizData) throws Exception {
		Date[] temp = calcActualTime(theResult, theClockedBizData);
		// 如果请假开始时间 》 下班时间
		if (temp[0].getTime() >= theResult.getStandEndTime().getTime()) {
			return;
		}
		// 如果请假结束时间 《 上班时间
		if (temp[1].getTime() <= theResult.getStandBeginTime().getTime()) {
			return;
		}

		double value = WorkHourRule.getQingJiaHoursBySameDay(temp[0], temp[1], theResult);
		if ("年假".equals(theClockedBizData.getBizFlag1())) {
//			String organ = ClockedUtil.getOrgan(theResult.getOrgan()).getName();
//			// 华东团餐事业部,上海生产中心,广州生产中心 按0.5小时扣，其他按4小时
//			if (organ.indexOf("华东团餐事业部") != -1 || organ.indexOf("上海生产中心") != -1 || organ.indexOf("广州生产中心") != -1) {
//				if (value <= 1) {
//					value = 1;
//				} else if (value > 1 && value <= 2) {
//					value = 2;
//				} else if (value > 2 && value <= 3) {
//					value = 3;
//				} else if (value > 3 && value <= 4) {
//					value = 4;
//				} else if (value > 4 && value <= 5) {
//					value = 5;
//				} else if (value > 5 && value <= 6) {
//					value = 6;
//				} else if (value > 6 && value <= 7) {
//					value = 7;
//				} else if (value > 7 && value <= 8) {
//					value = 8;
//				} else {
//
//				}
//			} else {
//				if (value <= 4) {
//					value = 4;
//				} else {
//					value = 8;
//				}
//			}
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
		double sumQingJiaHours = theResult.getNianJia()+theResult.getShiJia()
		+theResult.getHunJia()+theResult.getSangJia()+theResult.getPeiXunJia()
		+theResult.getChanJia()+theResult.getPeiChanJia()+theResult.getTiaoXiu()
		+theResult.getOtherQingJia()+theResult.getBingJia();
		// 设置请假时数
		theResult.setQingJia(sumQingJiaHours);
	}
	/**
	 * 计算实际的业务时间--只适合异动单/出差单/请假三种业务单据
	 * 
	 * @param theResult
	 * @param theClockedBizData
	 */
	public static Date[] calcActualTime(ClockedResultBases theResult, ClockedBizData theClockedBizData) {
		Date begin = null;
		Date end = null;
		Date bizBeginTime = theClockedBizData.getBeginTime();
		Date bizEndTime = theClockedBizData.getEndTime();
		Date clockDate = theResult.getCheckingDate();
		// 判断是否跨天

		begin = bizBeginTime;
		end = bizEndTime;
		if (WorkHourRule.isKuaTianOnBeginTime(theResult, bizBeginTime, bizEndTime)) {
			if (UtilDateTime.getDaysBetween(bizBeginTime, clockDate) > 0) {
				// 跨天
				if (bizBeginTime.before(theResult.getStandBeginTime())) {
					begin = (theResult.getStandBeginTime());
				}
			}
		} else {
			begin = (bizBeginTime);
		}
		if (WorkHourRule.isKuaTianOnEndTime(theResult, bizBeginTime, bizEndTime)) {
			if (UtilDateTime.getDaysBetween(clockDate, bizEndTime) > 0) {
				// 跨天
				if (bizEndTime.after(theResult.getStandEndTime())) {
					end = theResult.getStandEndTime();
				}
			}
		} else {
			end = (bizEndTime);
		}
		return new Date[] { begin, end };
	}
}
