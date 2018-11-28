package com.babifood.clocked.rule;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.babifood.clocked.entrty.ClockedBizData;
import com.babifood.clocked.entrty.ClockedResultBases;
import com.babifood.utils.UtilDateTime;
/**
 * 办公室-考勤业务数据--加班/出差/请假/异动计算规则
 * @author BABIFOOD
 *
 */
@Service
public class OfficeCalcRule {
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

		calculate(theResult);
	}
	/*
	 * 对最终的数据进行计算
	 */
	private void calculate(ClockedResultBases theResult) throws Exception {
		DateFormat dftime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 对不需要上班的日期，进行处理
		if (theResult.getClockFlag() ==0) {		
			theResult.setYiDong(0d);
			theResult.setChuCha(0d);
			theResult.setQingJia(0d);
			theResult.setNianJia(0d);
			theResult.setTiaoXiu(0d);
			theResult.setShiJia(0d);
			theResult.setBingJia(0d);
			theResult.setPeiXunJia(0d);
			theResult.setHunJia(0d);
			theResult.setChanJia(0d);
			theResult.setPeiChanJia(0d);
			theResult.setSangJia(0d);
			theResult.setOtherQingJia(0d);

			theResult.setChiDao(0);
			theResult.setZaoTui(0);
			theResult.setKuangGongCiShu(0);
			theResult.setKuangGong(0d);

			// 对于加班的数据，不能清空,比如周末加班
			// theResult.setJiaBan(0);
			if (theResult.getCheckingBeginTime() == null || theResult.getCheckingEndTime() == null) {
				theResult.setActualWorkLength(0d);
				theResult.setOriginalCheckingLength(0d);
			}

			// 餐补个数
			ClockedBaseRule.calcCanBu(theResult);
		} else if(theResult.getClockFlag() ==1){
			// 计算异动时间
			if (yiDongBegin != null && yiDongEnd != null) {
				double yiDongHours = WorkHourRule.getOfficeWorkHoursBySameDay(dftime.format(yiDongBegin), dftime.format(yiDongEnd), theResult);
				theResult.setYiDong(yiDongHours);
			}
			// 计算最终有效时长
			double finalHours = 0;
			if (theResult.getFinalBeginTime() != null && theResult.getFinalEndTime() != null) {
				finalHours = WorkHourRule.getOfficeWorkHoursBySameDay(dftime.format(theResult.getFinalBeginTime()), dftime.format(theResult.getFinalEndTime()), theResult);
			}
			// 缺勤时长
			theResult.setQueQin(theResult.getStandWorkLength() - finalHours);
			// 实际工作时长
			if (theResult.getStandWorkLength() - theResult.getQingJia() - theResult.getQueQin() < 0) {
				theResult.setActualWorkLength(0d);
			} else {
				theResult.setActualWorkLength(theResult.getStandWorkLength() - theResult.getQingJia() - theResult.getQueQin());
			}
			// 没有缺勤
			if (finalHours>=theResult.getStandWorkLength()) {
				theResult.setChiDao(0);
				theResult.setZaoTui(0);
				theResult.setKuangGongCiShu(0);;
				theResult.setKuangGong(0d);
			} else {
				if (theResult.getFinalBeginTime() == null || theResult.getFinalEndTime() == null) {
					theResult.setKuangGongCiShu(1);
					theResult.setKuangGong(theResult.getStandWorkLength() - finalHours);
				} else {
					// 迟到
					long chiDaoValue = 0;
					if (theResult.getFinalBeginTime().after(theResult.getStandBeginTime())) {
						chiDaoValue = UtilDateTime.getMinuteBetween(theResult.getStandBeginTime(), theResult.getFinalBeginTime());
					}
					// 早退
					long zaoTuiValue = 0;
					if (theResult.getFinalEndTime().before(theResult.getStandEndTime())) {
						zaoTuiValue = UtilDateTime.getMinuteBetween(theResult.getFinalEndTime(), theResult.getStandEndTime());
					}
					if (chiDaoValue >= 15 || zaoTuiValue >= 15) {
						double kuangGong = 0d;
						//如果员工迟到早退大于15分钟着算4个小时，如果大于4小时着算8小时
						if(theResult.getStandWorkLength()==8){
							if(theResult.getQueQin()<=4){
								kuangGong = 4;
							}else if(theResult.getQueQin()>4){
								kuangGong = 8;
							}else{
								kuangGong = theResult.getQueQin();
							}
						//如果周六员工迟到早退大于15分钟着算3个小时，如果大于3小时着算6小时	
						}else if(theResult.getStandWorkLength()==6){
							if(theResult.getQueQin()<=3){
								kuangGong = 3;
							}else if(theResult.getQueQin()>3){
								kuangGong = 6;
							}else{
								kuangGong = theResult.getQueQin();
							}
						}
						theResult.setKuangGongCiShu(1);
						theResult.setKuangGong(kuangGong);
					} else {
						if (chiDaoValue > 0) {
							theResult.setChiDao(1);
							theResult.setActualWorkLength(theResult.getActualWorkLength()+theResult.getQueQin());
							//缺勤置0
							theResult.setQueQin(0d);
						}
						if (zaoTuiValue > 0) {
							theResult.setZaoTui(1);
							theResult.setActualWorkLength(theResult.getActualWorkLength()+theResult.getQueQin());
							//缺勤置0
							theResult.setQueQin(0d);
						}
						
					}
				}
			}
			// 餐补个数
			ClockedBaseRule.calcCanBu(theResult);
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
//		if (temp[0].getTime() > theResult.getStandEndTime().getTime()) {
//			return;
//		}
		// 如果异动结束时间 < 上班时间
//		if (temp[1].getTime() < theResult.getStandBeginTime().getTime()) {
//			return;
//		}
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
