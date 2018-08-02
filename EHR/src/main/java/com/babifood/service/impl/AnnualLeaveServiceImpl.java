package com.babifood.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.babifood.dao.AnnualLeaveDao;
import com.babifood.service.AnnualLeaveService;
@Service
public class AnnualLeaveServiceImpl implements AnnualLeaveService {
	@Autowired
	AnnualLeaveDao AnnualLeaveDao;
	@Override
	public List<Map<String, Object>> loadNowAnnualLeave(String npname) {
		return AnnualLeaveDao.loadNowAnnualLeave(npname);
	}
	@Override
	public List<Map<String, Object>> loadHistoryAnnualLeave(String lpname) {
		return AnnualLeaveDao.loadHistoryAnnualLeave(lpname);
	}
	@Override
	public List<Map<String, Object>> GetHireDate() {
		return AnnualLeaveDao.GetHireDate();
	}
	/**
 	 * 生成变动的最新司龄（年），的员工list
 	 * @param list 员工档案取所有员工的"工号"、"姓名"、"入职日期"+从当前年假记录表中取员工的"司龄"、"入职日期"
 	 * @return List:"工号"、"姓名"、"入职日期"、"原来的司龄"、"现在的司龄"，"对本年，入职日期前的总天数"、"对本年，入职日期后的总天数"
 	 */
	@Override
    public List<Map<String, Object>> setCompanyDayFlag(List<Map<String, Object>> list){
    	 List<Map<String, Object>> listCD = new ArrayList<Map<String, Object>>();
    	 for (Map<String, Object> map : list) {//list为数据库查询出来的数据   
    		 String number=map.get("p_number").toString();
    		 String name=map.get("p_name").toString();
    		 String date=map.get("p_in_date").toString(); //当前时刻员工档案里的员工入职日期
    		 String indate=map.get("pinday")!=null?map.get("pinday").toString():"9999-01-01"; //当前时刻当前年假记录里的员工入职日期
    		 int companydayflag=map.get("p_companydayflag")!=null?Integer.parseInt(map.get("p_companydayflag").toString()):-1;//若该员工原来没有司龄（年），那么给他标-1
    		 int[] companydayflag2=computeCompanyDayFlag(date);//计算新的司龄\对于本年，该员工入职日期前的总天数\对于本年，该员工入职日期后的总天数
    		 if(companydayflag!=companydayflag2[0] || !date.equals(indate)){//司龄不一样，或者入职日期有变更，都要更新该员工的年假记录
    			 Map<String, Object> map1=new HashMap<String, Object>();
    			 map1.put("p_number", number);//工号
    	    	 map1.put("p_name", name);//姓名
    	    	 map1.put("p_in_date", date);//入职日期
    	    	 map1.put("p_companydayflag", companydayflag);//原来的司龄
    	    	 map1.put("p_companydayflag2", companydayflag2[0]); //新的司龄
    	    	 map1.put("overday", companydayflag2[1]); //对于本年，该员工入职日期前的总天数
    	    	 map1.put("remainday", companydayflag2[2]);//对于本年，该员工入职日期后的总天数
    	    	 listCD.add(map1);
    		 }
    	 }    	 
		 return listCD;
    }
    /**
 	* 根据入职日期判断计算范围
 	* @param p_in_date 员工入职时间
 	* @return 司龄\对于本年，该员工入职日期前的总天数\对于本年，该员工入职日期后的总天数
 	*/
	@Override
	public int[] computeCompanyDayFlag(String p_in_date) {
		Date pindate = null;
		try {
			pindate = new SimpleDateFormat("yyyy-MM-hh").parse(p_in_date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		c.setTime(pindate);
		c.set(Calendar.YEAR, year);
		int overday = c.get(Calendar.DAY_OF_YEAR);//换了年份的入职时间点，对于本年，该员工入职日期前的总天数
		int alloverday = (int) ((new Date().getTime() - pindate.getTime())/(24 * 60 * 60 * 1000))-2;//该员工入职到现在的总天数
        int companyday = alloverday/365;//司龄（年）
        int remainday = 365 - overday;//对于本年，该员工入职日期后的总天数
		int[] CDFlag=new int[3];
		
		CDFlag[0]=companyday;
		CDFlag[1]=overday;
		CDFlag[2]=remainday;
		System.out.println("入职日期"+p_in_date);
		System.out.println("司龄"+companyday);
		System.out.println("对于本年，该员工入职日期前的总天数"+overday);
		System.out.println("对于本年，该员工入职日期后的总天数"+remainday);
		return CDFlag;
	} 
      
	
    /**
   	* 计算年假天数，生成员工年假list
   	* @param list "工号"、"姓名"、"入职日期"、"原来的司龄"、"现在的司龄"，"对本年，入职日期前的总天数"、"对本年，入职日期后的总天数"
   	* @return List:工号、姓名、司龄、本年正常年假（还未计算病假导致的扣减）
   	*/
	@Override
	public List<Map<String, Object>> setAnnualLeavelist(List<Map<String, Object>> list) {
		List<Map<String, Object>> listAL = new ArrayList<Map<String,Object>>();
		for (Map<String,Object>map:list) { //list为员工编号、员工名字、员工司龄标志    		
			Map<String,Object> map1 = new HashMap<String,Object>();
			String number = map.get("p_number").toString(); //工号
			String name = map.get("p_name").toString(); //姓名
			String p_in_date = map.get("p_in_date").toString(); //入职日期
			int companydayflag = (int) map.get("p_companydayflag2"); //当前的司龄
			int overday = (int) map.get("overday");
			int remainday = (int) map.get("remainday");
			map1.put("p_number", number);
			map1.put("p_name", name);
			map1.put("p_in_date", p_in_date);
			map1.put("p_companydayflag", companydayflag);
			int nannualleave = computeAnnualLeave(companydayflag, overday, remainday); // TODO 本年年假数，根据上年请病假的天数做扣减未做，待修正
			map1.put("nannualleave", nannualleave);
			int lannualleave = 0; // TODO 上年结余数，待修正，应该在每年的1月1日取上一年最后的剩余年假数，之后持续一年时间不变
			map1.put("lannualleave", lannualleave);
			int useddata = 0; // TODO 已使用数，待修正，应根据OA过来的请假单，做累计
			map1.put("useddata", useddata);
			map1.put("remaindata", nannualleave + lannualleave - useddata); //剩余年假数
			int disableddata = 0; // TODO 自动作废数，待修正，每年7月1号的时候，看“已使用数”是否大于等于“上年结余数”，如果不大于，那么等于“上年结余数”-“已使用数”，否则等于0
			map1.put("disableddata", disableddata);
			listAL.add(map1);
		}
		return listAL;
	}

	/**
	 * 根据司龄计算年假小时数
	 * @param p_companydayflag 司龄
	 * @param overDay 对本年，入职日期前的总天数
	 * @param remainDay 对本年，入职日期后的总天数
	 * @return 年假小时数
	 * */
	@Override
	public int computeAnnualLeave(int p_companydayflag, int overDay, int remainDay) {
		int al = 0;
		double yearday = 365;
		double day4 = 40;
		double day8 = 80;
		double day12 = 120;
		double oday = overDay;
		double rday = remainDay;
		if (p_companydayflag == 0) { //司龄0，年假为0
			al = 0;
		} else if (p_companydayflag == 1) { //司龄1，当年剩余的日历天数/365*40，向下取整
			double c = rday / yearday * day4;
			/*System.out.println("c===>"+c);//1.75
	       		 System.out.println("c===>"+Math.ceil(c));//2.0
	       		 System.out.println("c===>"+Math.floor(c));//1.0*/
			al = (int) Math.floor(Math.floor(c));
		} else if (1 <= p_companydayflag && p_companydayflag <= 9) { //司龄2到9年，年假40
			al = 40;
		} else if (p_companydayflag == 10) { //司龄10年，当年已过的日历天数/365*40+当年剩余的日历天数/365*80，向下取整
			double c = oday / yearday * day4 + rday / yearday * day8;
			al = (int) Math.floor(c);
		} else if (11 <= p_companydayflag && p_companydayflag <= 19) { //司龄11到19年间，年假80
			al = 80;
		} else if (p_companydayflag == 20) { //司龄20年，当年已过的日历天数/365*80+当年剩余的日历天数/365*120，向下取整
			double c = oday / yearday * day8 + rday / yearday * day12;
			al = (int) Math.floor(c);
		} else if (21 <= p_companydayflag) { //司龄20年以上，年假120
			al = 120;
		}
		return al;
	}
	@Override
	public int[] SaveAnnualLeave(List<Map<String, Object>> list) {
		Calendar calendar = Calendar.getInstance();//日历对象
        int nowYear = calendar.get(Calendar.YEAR);//获取年份
		return AnnualLeaveDao.SaveAnnualLeave(list,nowYear);
	}

}
