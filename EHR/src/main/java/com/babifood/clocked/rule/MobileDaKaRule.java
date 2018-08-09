package com.babifood.clocked.rule;

import java.text.SimpleDateFormat;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.babifood.clocked.entrty.ClockedResultBases;
import com.babifood.clocked.entrty.MoveDaKaRecord;

/**
 * 移动打卡规则
 * 
 */
@Service
public class MobileDaKaRule {
	public static void attachWithDaKa(ClockedResultBases theReslut, Map<String, MoveDaKaRecord> theMap) throws Exception {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String key = theReslut.getWorkNum() + df.format(theReslut.getCheckingDate());
		MoveDaKaRecord tmpClockedRecord = theMap.get(key);
		attachWithDaKaOne(theReslut, tmpClockedRecord);
	}
	/**
	 * 某人某天的打卡情况---仅仅根据打卡记录--实际打卡情况，需要根据业务数据进一步判定
	 * 
	 * @param theReslut
	 * @param theClockedRecord
	 * @throws Exception 
	 */
	public static void attachWithDaKaOne(ClockedResultBases theReslut, MoveDaKaRecord theClockedRecord) throws Exception {
		SimpleDateFormat dftime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// ================没有打卡记录=======================================
		if (theClockedRecord == null) {
			return;
		}
		// =================有打卡记录=======================================
		String daKaLocation = "";
		// 打卡开始时间
		if (theClockedRecord.getBegin() != null) {
			theReslut.setCheckingBeginTime(dftime.parse(theClockedRecord.getBegin().getClockTime()));
			daKaLocation = theClockedRecord.getBegin().getLocation();
		}
		// 打卡结束时间
		if (theClockedRecord.getEnd() != null) {
			theReslut.setCheckingEndTime(dftime.parse(theClockedRecord.getEnd().getClockTime()));
			if (daKaLocation.length() > 0) {
				daKaLocation = daKaLocation + "-" + theClockedRecord.getEnd().getLocation();
			}
		}
		theReslut.setDaKaLocation(daKaLocation);

		if (theClockedRecord.getBegin() != null && theClockedRecord.getEnd() != null) {
			double workHours = WorkHourRule.getMobileWorkHoursBySameDay(theClockedRecord.getBegin().getClockTime(), theClockedRecord.getEnd().getClockTime(), theReslut,true);
			theReslut.setOriginalCheckingLength(workHours);//打卡时长
			theReslut.setActualWorkLength(workHours);//实际工作时长
		}
	}
}
