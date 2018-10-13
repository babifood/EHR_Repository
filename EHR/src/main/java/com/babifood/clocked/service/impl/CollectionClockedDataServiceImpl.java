package com.babifood.clocked.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
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
import com.babifood.clocked.rule.OfficeNotCheckRule;
import com.babifood.clocked.service.CollectionClockedDataService;
import com.babifood.clocked.service.LoadClockedResultService;
import com.babifood.constant.ModuleConstant;
import com.babifood.constant.OperationConstant;
import com.babifood.entity.LoginEntity;
import com.babifood.utils.UtilDateTime;
import com.cn.babifood.operation.LogManager;
import com.cn.babifood.operation.annotation.LogMethod;
@Service
public class CollectionClockedDataServiceImpl implements CollectionClockedDataService {
	public static final Logger log = Logger.getLogger(CollectionClockedDataServiceImpl.class);
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
	@LogMethod(module = ModuleConstant.CLOCKED)
	public void loadData(){
		LoginEntity login = (LoginEntity) SecurityUtils.getSubject().getPrincipal();
		LogManager.putUserIdOfLogInfo(login.getUser_id());
		LogManager.putOperatTypeOfLogInfo(OperationConstant.OPERATION_LOG_TYPE_COLLECTION);
		// TODO Auto-generated method stub
		try {
			//1读取行政考勤打卡记录
			officeDaKaList = officeDaKaDao.loadOfficeDaKaData(year, month);
			//2读取移动考勤打卡记录
			List<MobileDaKaLog> mobileDaKaLog = null;
			mobileDaKaLog = moveDaKaDao.loadMobileDaKaDate(year, month);
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
			List<ClockedBizData> clockedBizDataList = null;
			clockedBizDataList = coleckEventResultDao.loadColeckEventResultData(year, month);
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LogManager.putContectOfLogInfo(e.getMessage());
			log.error("loadData():"+e.getMessage());
		}
	}

	@Override
	@LogMethod(module = ModuleConstant.CLOCKED)
	public void attachWithDaKa(){
		// TODO Auto-generated method stub
		//1根据打卡记录计算相关数据
		ClockedResultBases tmpClockedResult = null;
		int size = clockedResultList==null?0:clockedResultList.size();
		String daKaType = null;
		for (int i = 0; i < size; i++) {
			try {
				tmpClockedResult = clockedResultList.get(i);
				//0行政考勤,1移动考勤,2不考勤
				daKaType = tmpClockedResult.getCheckingType() == null ? "" : tmpClockedResult.getCheckingType();
				if (daKaType.equals("1")) {
					MobileDaKaRule.attachWithDaKa(tmpClockedResult, mobileDaKaMap);
				} else if(daKaType.equals("0")){
					OfficeDaKaRule.attachWithDaKa(tmpClockedResult, officeDaKaList);
				}else if(daKaType.equals("2")){
					//不打人员不计算打卡业务跳过本次循环
					continue;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				LogManager.putContectOfLogInfo(e.getMessage());
				log.error("attachWithDaKa()计算打卡记录:"+e.getMessage());
			}
		}
	}

	@Override
	@LogMethod(module = ModuleConstant.CLOCKED)
	public void attachWithBizData(){
		// TODO Auto-generated method stub
		//1根据考勤业务计算相关数据
		ClockedResultBases tmpClockedResult = null;
		int size = clockedResultList==null?0:clockedResultList.size();
		OfficeCalcRule officeCalcRule = null;
		MobileCalcRule mobileCalcRule = null;
		OfficeNotCheckRule officeNotCheckRule =null;
		String daKaType = null;
		for (int i = 0; i < size; i++) {
			tmpClockedResult = clockedResultList.get(i);
			//0行政考勤,1移动考勤,2不考勤
			daKaType = tmpClockedResult.getCheckingType() == null ? "" : tmpClockedResult.getCheckingType();
			try{
				if (daKaType.equals("1")) {
					mobileCalcRule =  new MobileCalcRule();
					// 按规则进行计算
					mobileCalcRule.attach(tmpClockedResult, clockedBizDataMap.get(tmpClockedResult.getWorkNum()));
				} else if(daKaType.equals("0")){
					officeCalcRule = new OfficeCalcRule();
					// 按规则进行计算
					officeCalcRule.attach(tmpClockedResult, clockedBizDataMap.get(tmpClockedResult.getWorkNum()));
				}else if(daKaType.equals("2")){
					//不打卡人员当天没有请假的的默认视为有效出勤并计算餐补个数，
					officeNotCheckRule = new OfficeNotCheckRule();
					officeNotCheckRule.attach(tmpClockedResult, clockedBizDataMap.get(tmpClockedResult.getWorkNum()));
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				LogManager.putContectOfLogInfo(e.getMessage());
				log.error("attachWithBizData()计算考勤业务:"+e.getMessage());
			}
		}
	}

	@Override
	@LogMethod(module = ModuleConstant.CLOCKED)
	public int[] saveDate(){
		// TODO Auto-generated method stub
		//1保存数据
		int [] rows = null;
		try {
			rows =  clockedResultBaseDao.updateClockedResultBase(clockedResultList);
			LogManager.putContectOfLogInfo("保存数据");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LogManager.putContectOfLogInfo(e.getMessage());
			log.error("保存数据:"+e.getMessage());
		}
		return rows;
	}

	@Override
	public void pushOA() {
		// TODO Auto-generated method stub
		//1推送OA考勤结果
	}

	@Override
	public int[] execute(int year,int month){
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
//    	Long simple=System.currentTimeMillis();
//    	//三位随机数
//    	int random=new Random().nextInt(90000)+10000;//为变量赋随机值100-999;
//      System.out.println(simple.toString());  
//      System.out.println("YX"+random);
//		int a=100001;
//		int[] i=  {100001,100002,100003,100004,100005,100006};
//		for(int j=0;j<i.length;j++){
//			if(a==i[j]){
//				j=0;
//				a=a+1;
//			}
//		}
//		System.out.println(a);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Object[] params=new Object[2];
		params[0]=df.format(UtilDateTime.getMonthStartSqlDate(2018,7));
		params[1]=df.format(UtilDateTime.getMonthEndSqlDate(2018,7));
		System.out.println(params[0]);
		System.out.println(params[1]);
	}
}
