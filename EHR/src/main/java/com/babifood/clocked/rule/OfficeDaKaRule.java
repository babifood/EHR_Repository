package com.babifood.clocked.rule;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.stereotype.Service;

import com.babifood.clocked.entrty.ClockedResultBases;
import com.babifood.clocked.entrty.OfficeDaKaRecord;
import com.babifood.utils.UtilDateTime;

/**
 * 行政考勤规则
 * @author BABIFOOD
 *
 */
@Service
public class OfficeDaKaRule {
	public static void attachWithDaKa(ClockedResultBases theReslut,List<OfficeDaKaRecord> theList) throws Exception {
		OfficeDaKaRecord tmpClockedRecord = getClockedRecordWithDaKa(theReslut, theList);
		attachWithDaKaOne(theReslut, tmpClockedRecord);
	}
	/**
	 * 某人某天的打卡情况---仅仅根据打卡记录--实际打卡情况，需要根据业务数据进一步判定
	 * 
	 * @param theReslut
	 * @param theClockedRecord
	 * @throws Exception 
	 */
	public static void attachWithDaKaOne(ClockedResultBases theReslut, OfficeDaKaRecord theClockedRecord) throws Exception {
		SimpleDateFormat dftime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		// ================没有打卡记录=======================================
		if (theClockedRecord == null) {
//			theReslut.setCheckingBeginTime(null);//打卡开始时间设置成null
//			theReslut.setCheckingEndTime(null);//打卡开始时间设置成null
			theReslut.setOriginalCheckingLength(0d);//打卡时长
//			theReslut.setKuangGong(theReslut.getStandWorkLength());//旷工时长
			theReslut.setActualWorkLength(0d);//实际工作时长
			// 退出程序
			return;
		}
		// =================有打卡记录=======================================
		// 打卡开始时间
		if(theClockedRecord.getBeginTime()!=null&&!theClockedRecord.getBeginTime().equals("")){
			theReslut.setCheckingBeginTime(dftime.parse(df.format(theClockedRecord.getClockedDate())+" "+theClockedRecord.getBeginTime()+":00"));
		}
		// 打卡结束时间
		if(theClockedRecord.getEndTime()!=null&&!theClockedRecord.getEndTime().equals("")){
			theReslut.setCheckingEndTime(dftime.parse(df.format(theClockedRecord.getClockedDate())+" "+theClockedRecord.getEndTime()+":00"));
		}
		if (theClockedRecord.getBeginTime() == null||theClockedRecord.getBeginTime().equals("") || theClockedRecord.getEndTime() == null||theClockedRecord.getEndTime().equals("")) {
			theReslut.setOriginalCheckingLength(0d);//打卡时长
//			theReslut.setKuangGong(theReslut.getStandWorkLength());//旷工时长
			theReslut.setActualWorkLength(0d);//实际工作时长
			// 退出程序
			return;
		}
		double workHours = WorkHourRule.getOfficeWorkHoursBySameDay(df.format(theClockedRecord.getClockedDate())+" "+theClockedRecord.getBeginTime()+":00", df.format(theClockedRecord.getClockedDate())+" "+theClockedRecord.getEndTime()+":00",theReslut);
		theReslut.setOriginalCheckingLength(workHours);
//		theReslut.setKuangGong(theReslut.getStandWorkLength()-workHours);//旷工时长
		theReslut.setActualWorkLength(workHours);
		/*
		// 迟到
		long chiDaoValue = 0;
		if (theClockedRecord.getBeginTime().after(theReslut.getStandBeginTime())) {
			chiDaoValue = UtilDateTime.getMinuteBetween(theReslut.getStandBeginTime(), theClockedRecord.getBeginTime());
		}
		// 早退
		long zaoTuiValue = 0;
		if (theClockedRecord.getEndTime().before(theReslut.getStandEndTime())) {
			zaoTuiValue = UtilDateTime.getMinuteBetween(theClockedRecord.getEndTime(), theReslut.getStandEndTime());
		}
		if (chiDaoValue > 15 || zaoTuiValue > 15) {
			theReslut.setKuangGong(1);
		} else {
			if (chiDaoValue > 0) {
				theReslut.setChiDao(1);
			}
			if (zaoTuiValue > 0) {
				theReslut.setZaoTui(1);
			}
		}
		*/
	}
	private static OfficeDaKaRecord getClockedRecordWithDaKa(ClockedResultBases theClockedResult, List<OfficeDaKaRecord> clockedRecordList) {
		OfficeDaKaRecord temp = null;
		int size = clockedRecordList == null ? 0 : clockedRecordList.size();
		boolean flag = false;
		for (int i = 0; i < size; i++) {
			flag = false;
			temp = clockedRecordList.get(i);
			if (temp.getWorkNum().equals(theClockedResult.getWorkNum())) {
				if (temp.getClockedDate() != null && UtilDateTime.getDaysBetween(theClockedResult.getCheckingDate(), temp.getClockedDate()) == 0) {
					flag = true;
					break;
				}
			}
		}
		if (flag) {
			return temp;
		} else {
			return null;
		}
	}
}
