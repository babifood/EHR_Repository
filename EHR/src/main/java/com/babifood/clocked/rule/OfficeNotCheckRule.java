package com.babifood.clocked.rule;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.babifood.clocked.entrty.ClockedBizData;
import com.babifood.clocked.entrty.ClockedResultBases;
import com.babifood.utils.UtilDateTime;

//计算不打卡人员的业务数据
public class OfficeNotCheckRule {
	private Date yiDongBegin;
	private Date yiDongEnd;

	private Date qingJiaBegin;
	private Date qingJiaEnd;

	private Date chuChaiBegin;
	private Date chuChaiEnd;
	public void attach(ClockedResultBases theResult, List<ClockedBizData> theClockedBizDataList) throws Exception {
		if (theClockedBizDataList != null) {
			int size = theClockedBizDataList.size();
			for (int i = 0; i < size; i++) {
				attachOne(theResult, theClockedBizDataList.get(i));
			}
		}

		Date tmpBegin = getMin(yiDongBegin, qingJiaBegin);
		tmpBegin = getMin(tmpBegin, chuChaiBegin);

		Date tmpEnd = getMax(yiDongEnd, qingJiaEnd);
		tmpEnd = getMax(tmpEnd, chuChaiEnd);

		theResult.setEventBeginTime(tmpBegin);
		theResult.setEventEndTime(tmpEnd);
		//计算餐补
		if (theResult.getClockFlag() == 0) {
			theResult.setCanBu(0);
			//排班标准上下班时间（非工厂）非工作日加班大于4.5小时才发放餐补
			if (!theResult.getBeginTime().equals("9:00")&&!theResult.getEndTime().equals("18:00")&&theResult.getJiaBan() >= 4.5) {
				theResult.setCanBu(1);
			}
		} else {
			// 餐补个数
			if (theResult.getQingJia() >= 4.5) {
				theResult.setCanBu(0);
			} else {
				theResult.setCanBu(1);
			}
		}
	}
	/*
	 * 适配
	 */
	private void attachOne(ClockedResultBases theResult, ClockedBizData theClockedBizData) throws Exception {
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
		} else if (billType.equals("请假申请单")) {
			attachOneQingJia(theResult, theClockedBizData);
		} else if (billType.equals("出差申请单")) {
			attachOneChuChai(theResult, theClockedBizData);
		} else {
			return;
		}
	}
	/*
	 * 计算异动
	 */
	private void attachOneYiDong(ClockedResultBases theResult, ClockedBizData theClockedBizData) {
		// 上班和下班都打卡了,并且上下班打卡时间都正常
		if (theResult.getCheckingBeginTime() != null && theResult.getCheckingEndTime() != null && theResult.getStandBeginTime().getTime() >= theResult.getCheckingBeginTime().getTime() && theResult.getStandEndTime().getTime() <= theResult.getCheckingEndTime().getTime()) {
			return;
		}

		Date[] temp = ClockedBaseRule.calcActualTime(theResult, theClockedBizData);
		// 如果异动开始时间 > 下班时间
		if (temp[0].getTime() > theResult.getStandEndTime().getTime()) {
			return;
		}
		// 如果异动结束时间 < 上班时间
		if (temp[1].getTime() < theResult.getStandBeginTime().getTime()) {
			return;
		}
		// 上班和下班都打卡了,异动时间在打卡时间之间
		if (theResult.getCheckingBeginTime() != null && theResult.getCheckingEndTime() != null && temp[0].getTime() >= theResult.getCheckingBeginTime().getTime() && temp[1].getTime() <= theResult.getCheckingEndTime().getTime()) {
			return;
		}

		if (yiDongBegin == null) {
			yiDongBegin = temp[0];
		} else {
			if (yiDongBegin.getTime() > temp[0].getTime()) {
				yiDongBegin = temp[0];
			}
		}
		if (yiDongEnd == null) {
			yiDongEnd = temp[1];
		} else {
			if (yiDongEnd.getTime() < temp[1].getTime()) {
				yiDongEnd = temp[1];
			}
		}
	}

	/*
	 * 计算请假
	 */
	private void attachOneQingJia(ClockedResultBases theResult, ClockedBizData theClockedBizData) throws Exception {
		Date[] temp = ClockedBaseRule.calcActualTime(theResult, theClockedBizData);
		// 如果请假开始时间 》 下班时间
		if (temp[0].getTime() >= theResult.getStandEndTime().getTime()) {
			return;
		}
		// 如果请假结束时间 《 上班时间
		if (temp[1].getTime() <= theResult.getStandBeginTime().getTime()) {
			return;
		}
		if (qingJiaBegin == null) {
			qingJiaBegin = temp[0];
		} else {
			if (qingJiaBegin.getTime() > temp[0].getTime()) {
				qingJiaBegin = temp[0];
			}
		}
		if (qingJiaEnd == null) {
			qingJiaEnd = temp[1];
		} else {
			if (qingJiaEnd.getTime() < temp[1].getTime()) {
				qingJiaEnd = temp[1];
			}
		}
		ClockedBaseRule.calcOneQingJiaByOffice(theResult, theClockedBizData);
	}

	/*
	 * 计算出差
	 */
	private void attachOneChuChai(ClockedResultBases theResult, ClockedBizData theClockedBizData) throws Exception {
		SimpleDateFormat dftime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date[] temp = ClockedBaseRule.calcActualTime(theResult, theClockedBizData);
		// 如果出差开始时间 》 下班时间
		if (temp[0].getTime() >= theResult.getStandEndTime().getTime()) {
			return;
		}
		// 如果出差结束时间 《 上班时间
		if (temp[1].getTime() <= theResult.getStandBeginTime().getTime()) {
			return;
		}
		// 上班和下班都打卡了
		if (theResult.getCheckingBeginTime() != null && theResult.getCheckingEndTime() != null && temp[0].getTime() >= theResult.getCheckingBeginTime().getTime() && temp[1].getTime() <= theResult.getCheckingEndTime().getTime()) {
			return;
		}
		if (chuChaiBegin == null) {
			chuChaiBegin = temp[0];
		} else {
			if (chuChaiBegin.getTime() > temp[0].getTime()) {
				chuChaiBegin = temp[0];
			}
		}
		if (chuChaiEnd == null) {
			chuChaiEnd = temp[1];
		} else {
			if (chuChaiEnd.getTime() < temp[1].getTime()) {
				chuChaiEnd = temp[1];
			}
		}
		String beginTime = dftime.format(temp[0]);
		String endTime = dftime.format(temp[1]);
				
		double value = WorkHourRule.getOfficeWorkHoursBySameDay(beginTime,endTime,theResult);
		theResult.setChuCha(value);
	}

	/*
	 * 计算加班
	 */
	private void attachOneJiaBan(ClockedResultBases theResult, ClockedBizData theClockedBizData) {
		if (UtilDateTime.getDaysBetween(theResult.getCheckingDate(), theClockedBizData.getBeginTime()) == 0) {
			if (theResult.getClockFlag() == 1 && theClockedBizData.getBeginTime().getTime() >= theResult.getStandBeginTime().getTime() && theClockedBizData.getEndTime().getTime() <= theResult.getStandEndTime().getTime()) {
				return;
			}
			theResult.setJiaBan(theClockedBizData.getTimeLength());
		}
	}
	private Date getMin(Date x1, Date x2) {
		if (x1 == null) {
			return x2;
		} else {
			if (x2 == null) {
				return x1;
			} else {
				if (x1.before(x2)) {
					return x1;
				} else {
					return x2;
				}
			}
		}
	}
	private Date getMax(Date x1, Date x2) {
		if (x1 == null) {
			return x2;
		} else {
			if (x2 == null) {
				return x1;
			} else {
				if (x1.before(x2)) {
					return x2;
				} else {
					return x1;
				}
			}
		}
	}
}
