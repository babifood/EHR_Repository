package com.babifood.clocked.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.babifood.clocked.dao.ClockedResultBaseDao;
import com.babifood.clocked.dao.ColeckEventResultDao;
import com.babifood.clocked.dao.MoveDaKaDao;
import com.babifood.clocked.dao.OfficeDaKaDao;
import com.babifood.clocked.entrty.ClockedBizData;
import com.babifood.clocked.entrty.ClockedResultBases;
import com.babifood.clocked.entrty.MobileDaKaLog;
import com.babifood.clocked.entrty.MoveDaKaRecord;
import com.babifood.clocked.entrty.OfficeDaKaRecord;
import com.babifood.clocked.rule.MobileCalcRule;
import com.babifood.clocked.rule.MobileDaKaRule;
import com.babifood.clocked.rule.OfficeCalcRule;
import com.babifood.clocked.rule.OfficeDaKaRule;
import com.babifood.clocked.rule.WorkHourRule;
import com.babifood.clocked.service.CollectionClockedDataService;
import com.babifood.clocked.service.LoadClockedResultService;
@Service
public class CollectionClockedDataServiceImpl implements CollectionClockedDataService {
	private int year = 0;
	private int month = 0;
	private List<OfficeDaKaRecord> officeDaKaList;
	private Map<String, MoveDaKaRecord> mobileDaKaMap;
	private List<ClockedResultBases> clockedResultList;
	private Map<String, List<ClockedBizData>> clockedBizDataMap;
	@Autowired
	OfficeDaKaDao officeDaKaDao;
	@Autowired
	MoveDaKaDao moveDaKaDao;
	@Autowired
	LoadClockedResultService loadClockedResultService;
	@Autowired
	ColeckEventResultDao coleckEventResultDao;
	@Autowired
	ClockedResultBaseDao clockedResultBaseDao;
	@Override
	public void loadData() throws Exception {
		// TODO Auto-generated method stub
		//1读取行政考勤打卡记录
		officeDaKaList = officeDaKaDao.loadOfficeDaKaData(year, month);
		//2读取移动考勤打卡记录
		List<MobileDaKaLog> mobileDaKaLog = moveDaKaDao.loadMobileDaKaDate(year, month);
		int size = mobileDaKaLog == null ? 0 : mobileDaKaLog.size();
		MoveDaKaRecord moveDaKaRecord = null;
		MobileDaKaLog tmpLog = null;
		mobileDaKaMap = new HashMap<String, MoveDaKaRecord>(size);
		String key = null;
		for (int i = 0; i < size; i++) {
			tmpLog = mobileDaKaLog.get(i);
			key = tmpLog.getWorkNum() + tmpLog.getClockDate();
			moveDaKaRecord = mobileDaKaMap.get(key);
			if (moveDaKaRecord == null) {
				moveDaKaRecord = new MoveDaKaRecord();
				moveDaKaRecord.setBegin(tmpLog);
				mobileDaKaMap.put(key, moveDaKaRecord);
			} else {
				moveDaKaRecord.setEnd(tmpLog);
			}
		}
		//3读取考勤事件业务信息
		clockedResultList = loadClockedResultService.loadClockedResultDataList(year, month);
		List<ClockedBizData> clockedBizDataList = coleckEventResultDao.loadColeckEventResultData(year, month);
		clockedBizDataMap = new HashMap<String, List<ClockedBizData>>(300);

		if (clockedBizDataList != null && clockedBizDataList.isEmpty() == false) {
			int xsize = clockedBizDataList.size();
			ClockedBizData tmpClockedBizData = null;
			List<ClockedBizData> tmpList = null;
			for (int i = 0; i < xsize; i++) {
				tmpClockedBizData = clockedBizDataList.get(i);
				tmpList = clockedBizDataMap.get(tmpClockedBizData.getWorkNum());
				if (tmpList == null) {
					tmpList = new ArrayList<ClockedBizData>(20);
					clockedBizDataMap.put(tmpClockedBizData.getWorkNum(), tmpList);
				}
				tmpList.add(tmpClockedBizData);
			}
		}
		if (clockedBizDataList != null) {
			clockedBizDataList.clear();
			clockedBizDataList = null;
		}
	}

	@Override
	public void attachWithDaKa() throws Exception {
		// TODO Auto-generated method stub
		//1根据打卡记录计算相关数据
		ClockedResultBases tmpClockedResult = null;
		int size = clockedResultList==null?0:clockedResultList.size();
		String daKaType = null;
		for (int i = 0; i < size; i++) {
			tmpClockedResult = clockedResultList.get(i);
			//0行政考勤,1移动考勤,2不考勤
			daKaType = tmpClockedResult.getCheckingType() == null ? "" : tmpClockedResult.getCheckingType();
			if (daKaType.equals("1")) {
				MobileDaKaRule.attachWithDaKa(tmpClockedResult, mobileDaKaMap);
			} else if(daKaType.equals("0")){
				OfficeDaKaRule.attachWithDaKa(tmpClockedResult, officeDaKaList);
			}
		}
	}

	@Override
	public void attachWithBizData() throws Exception {
		// TODO Auto-generated method stub
		//1根据考勤业务计算相关数据
		ClockedResultBases tmpClockedResult = null;
		int size = clockedResultList==null?0:clockedResultList.size();
		OfficeCalcRule officeCalcRule = null;
		MobileCalcRule mobileCalcRule = null;
		String daKaType = null;
		for (int i = 0; i < size; i++) {
			tmpClockedResult = clockedResultList.get(i);
			//0行政考勤,1移动考勤,2不考勤
			daKaType = tmpClockedResult.getCheckingType() == null ? "" : tmpClockedResult.getCheckingType();
			
			if (daKaType.equals("1")) {
				mobileCalcRule =  new MobileCalcRule();
				// 按规则进行计算
				mobileCalcRule.attach(tmpClockedResult, clockedBizDataMap.get(tmpClockedResult.getWorkNum()));
			} else if(daKaType.equals("0")){
				officeCalcRule = new OfficeCalcRule();
				// 按规则进行计算
				officeCalcRule.attach(tmpClockedResult, clockedBizDataMap.get(tmpClockedResult.getWorkNum()));

			}

		}
	}

	@Override
	public int[] saveDate() throws Exception {
		// TODO Auto-generated method stub
		//1保存数据
		return clockedResultBaseDao.updateClockedResultBase(clockedResultList);
	}

	@Override
	public void pushOA() {
		// TODO Auto-generated method stub
		//1推送OA考勤结果
	}

	@Override
	public int[] execute(int year,int month) throws Exception {
		// TODO Auto-generated method stub
		int [] rows=null;
		this.year = year;
		this.month= month;
		loadData();
		attachWithDaKa();
		attachWithBizData();
		rows = saveDate();
		return rows;
	}
	public static void main(String[] args) {
//		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String strBegin="2018-08-01 09:33:00";
//		String strEnd="2018-08-01 17:30:00";
//		Date beginDate =null;
//		try {
//			beginDate = df.parse(strBegin);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		Date endDate=null;
//		try {
//			endDate = df.parse(strEnd);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		double length = WorkHourRule.getQingJiaTimeLength(beginDate,endDate);
//		System.out.println(length);
		
//		int i,min,max;
//		int A[]={74,48,30,17,62};  // 声明整数数组A,并赋初值
//		 
//		min=max=A[0];
//		System.out.print("数组A的元素包括：");
//		for(i=0;i<A.length;i++){
//			System.out.print(A[i]+" ");
//			if(A[i]>max)   // 判断最大值
//			max=A[i];
//			if(A[i]<min)   // 判断最小值
//			min=A[i];
//		}
//		System.out.println("\n数组的最大值是："+max); // 输出最大值
//		System.out.println("数组的最小值是："+min); // 输出最小值
		String s=null;
		System.out.println(s.length());
		
	}
}
