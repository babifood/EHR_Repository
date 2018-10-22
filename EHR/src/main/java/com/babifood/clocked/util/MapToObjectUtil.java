package com.babifood.clocked.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.babifood.clocked.entrty.PushOaDataEntrty;

public class MapToObjectUtil {
	public static List<PushOaDataEntrty> MapToPushOaDataEntrty(List<Map<String, Object>> Objlist){
		List<PushOaDataEntrty> list = new ArrayList<PushOaDataEntrty>();
		for (Map<String, Object> map : Objlist) {
			PushOaDataEntrty p = new PushOaDataEntrty();
			p.setUserName(map.get("UserName")==null?"":map.get("UserName").toString());
			p.setWorkNum(map.get("WorkNum")==null?"":map.get("WorkNum").toString());
			p.setDeptCode(map.get("deptCode")==null?"":map.get("deptCode").toString());
			p.setOrganCode(map.get("orgerCode")==null?"":map.get("orgerCode").toString());
			p.setCheckingDate(map.get("checkingDate")==null?"":map.get("checkingDate").toString());
			p.setWeek(map.get("Week")==null?"":map.get("Week").toString());
			p.setNianJia(map.get("nianJia")==null?0d:Double.parseDouble(map.get("nianJia").toString()));
			p.setQueQin(map.get("Queqin")==null?0d:Double.parseDouble(map.get("Queqin").toString()));
			p.setHunJia(map.get("hunJia")==null?0d:Double.parseDouble(map.get("hunJia").toString()));
			p.setQingJia(map.get("Qingjia")==null?0d:Double.parseDouble(map.get("Qingjia").toString()));
			p.setChiDao(map.get("chiDao")==null?0:Integer.parseInt(map.get("chiDao").toString()));
			p.setZaoTui(map.get("zaoTui")==null?0:Integer.parseInt(map.get("zaoTui").toString()));
			p.setKuangGongCiShu(map.get("kuangGongCiShu")==null?0:Integer.parseInt(map.get("kuangGongCiShu").toString()));
			p.setSangJia(map.get("SangJia")==null?0d:Double.parseDouble(map.get("SangJia").toString()));
			p.setCanBu(map.get("Canbu")==null?0:Integer.parseInt(map.get("Canbu").toString()));
			p.setBingJia(map.get("bingJia")==null?0d:Double.parseDouble(map.get("bingJia").toString()));
			p.setYiDong(map.get("Yidong")==null?0d:Double.parseDouble(map.get("Yidong").toString()));
			p.setCheckingBeginTime(map.get("checkingBeginTime")==null?"":map.get("checkingBeginTime").toString());
			p.setCheckingEndTime(map.get("checkingEndTime")==null?"":map.get("checkingEndTime").toString());
			p.setOtherQingJia(map.get("Qita")==null?0d:Double.parseDouble(map.get("Qita").toString()));
			p.setJiaBan(map.get("Jiaban")==null?0d:Double.parseDouble(map.get("Jiaban").toString()));
			p.setBeginTime(map.get("beginTime")==null?"":map.get("beginTime").toString());
			p.setEndTime(map.get("endTime")==null?"":map.get("endTime").toString());
			p.setChuCha(map.get("Chuchai")==null?0d:Double.parseDouble(map.get("Chuchai").toString()));
			p.setShiJia(map.get("shiJia")==null?0d:Double.parseDouble(map.get("shiJia").toString()));
			p.setYear(map.get("Year")==null?"0":map.get("Year").toString());
			p.setMonth(map.get("Month")==null?"0":map.get("Month").toString());
			p.setClockFlag(map.get("Clockflag")==null?0:Integer.parseInt(map.get("Clockflag").toString()));
			p.setOriginalCheckingLength(map.get("originalCheckingLength")==null?0d:Double.parseDouble(map.get("originalCheckingLength").toString()));
			p.setEventBeginTime(map.get("EventBeginTime")==null?"":map.get("EventBeginTime").toString());
			p.setActualWorkLength(map.get("actualWorkLength")==null?0d:Double.parseDouble(map.get("actualWorkLength").toString()));
			p.setEventEndTime(map.get("EventEndTime")==null?"":map.get("EventEndTime").toString());
			p.setPeiXunJia(map.get("peixunJia")==null?0d:Double.parseDouble(map.get("peixunJia").toString()));
			p.setChanJia(map.get("chanJia")==null?0d:Double.parseDouble(map.get("chanJia").toString()));
			p.setPeiChanJia(map.get("PeiChanJia")==null?0d:Double.parseDouble(map.get("PeiChanJia").toString()));
			p.setTiaoXiu(map.get("tiaoXiu")==null?0d:Double.parseDouble(map.get("tiaoXiu").toString()));
			String checkingType = "";
			if(map.get("checkingType")!=null&&map.get("checkingType").equals("0")){
				checkingType = "行政考勤";
			}else if(map.get("checkingType")!=null&&map.get("checkingType").equals("1")){
				checkingType = "移动考勤";
			}else if(map.get("checkingType")!=null&&map.get("checkingType").equals("2")){
				checkingType = "不考勤";
			}
			p.setCheckingType(checkingType);
			p.setStandWorkLength(map.get("standardWorkLength")==null?0d:Double.parseDouble(map.get("standardWorkLength").toString()));
			list.add(p);
		}
		return list;
	}
	public static Map<String, List<PushOaDataEntrty>> PushOaDataEntrtyPutMap(List<PushOaDataEntrty> Datalist){
		Map<String, List<PushOaDataEntrty>> pushOaDataEntrtyMap = new HashMap<String, List<PushOaDataEntrty>>();
		if (Datalist != null && Datalist.isEmpty() == false) {
			int xsize = Datalist.size();
			PushOaDataEntrty tmpPushOaDataEntrty = null;
			List<PushOaDataEntrty> tmpList = null;
			for (int i = 0; i < xsize; i++) {
				tmpPushOaDataEntrty = Datalist.get(i);
				tmpList = pushOaDataEntrtyMap.get(tmpPushOaDataEntrty.getWorkNum());
				if (tmpList == null) {
					tmpList = new ArrayList<PushOaDataEntrty>();
					pushOaDataEntrtyMap.put(tmpPushOaDataEntrty.getWorkNum(), tmpList);
				}
				tmpList.add(tmpPushOaDataEntrty);
			}
		}
		return pushOaDataEntrtyMap;
	}
	public static List<PushOaDataEntrty> CopyList(List<PushOaDataEntrty> list){
		List<PushOaDataEntrty> newList  = new ArrayList<PushOaDataEntrty>(list);
		for(int i = 0;i<newList.size()-1;i++){
			for(int j = newList.size()-1;j>i;j--){
		    	if(newList.get(j).getWorkNum().equals(newList.get(i).getWorkNum())){
		    		newList.remove(j); 
		        }  
		    }
		}
		return newList;
	}
}
