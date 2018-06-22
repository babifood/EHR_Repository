package com.babifood.service.impl;

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
 	 * 生成变动的最新司龄标记，的员工list
 	 * @param list 员工档案取所有员工的"工号"、"姓名"、"入职日期"+从当前年假记录表中取员工的"司龄标记"、"入职日期"
 	 * @param M 本年每个月的天数
 	 * @return List:"工号"、"姓名"、"入职日期"、"原来的司龄标记"、"现在的司龄标记"，"对本年，入职日期前的总天数"、"对本年，入职日期后的总天数"
 	 */
	@Override
    public List<Map<String, Object>> setCompanyDayFlag(List<Map<String, Object>> list,int[] M){
    	 List<Map<String, Object>> listCD = new ArrayList<Map<String, Object>>();
    	 for (Map<String, Object> map : list) {//list为数据库查询出来的数据   
    		 String number=map.get("p_number").toString();
    		 String name=map.get("p_name").toString();
    		 //当前时刻员工档案里的员工入职日期
    		 String date=map.get("p_in_date").toString(); 
    		 String[] D = date.split("-");
    		 int inMonth=Integer.parseInt(D[1]);//获取月
    		 int inDay=Integer.parseInt(D[2]);//获取日
    		 //当前时刻当前年假记录里的员工入职日期
    		 String indate=map.get("pinday")!=null?map.get("pinday").toString():"9999-01-01"; 
        	 //计算该员工本年“入职日期”前的总天数,计算该员工本年“入职日期”后的总天数
             int overDay = getOverDay(inMonth,M,inDay);
             int remainDay = getRemainDay(inMonth,M,inDay);
             //System.out.println(name+"该员工本年“入职日期”前的总天数："+overDay+"；该员工本年“入职日期”后的总天数："+remainDay);
    		 //若该员工原来没有司龄标志，那么给他标9
    		 String companydayflag=map.get("p_companydayflag")!=null?map.get("p_companydayflag").toString():"9";
    		 //计算新的司龄标记
    		 String companydayflag2=computeCompanyDayFlag(map.get("p_in_date").toString(),inDay,inMonth);
    		 if(!companydayflag.equals(companydayflag2) || !date.equals(indate)){//司龄标记不一样，或者入职日期有变更，都要更新该员工的年假记录
    			 Map<String, Object> map1=new HashMap<String, Object>();
    			 map1.put("p_number", number);//工号
    	    	 map1.put("p_name", name);//姓名
    	    	 map1.put("p_in_date", date);//入职日期
    	    	 map1.put("p_companydayflag", companydayflag);//原来的司龄标记
    	    	 map1.put("p_companydayflag2", computeCompanyDayFlag(date,overDay,remainDay)); //新的司龄标记
    	    	 map1.put("overday", overDay); //对于本年，该员工入职日期前的总天数
    	    	 map1.put("remainday", remainDay);//对于本年，该员工入职日期后的总天数
    	    	 listCD.add(map1);
    		 }
    	 }    	 
		 return listCD;
    }
     
    //已过天数
	@Override
    public int getOverDay(int nowMonth,int[] M,int nowDay){
       int overDay=0;
       int m=nowMonth-1;
    	for(int i=0;i<m;i++){
        	overDay += M[i];
        }
    	int overDay1 = overDay + nowDay -1;
 	return overDay1;
    }
    //剩余天数
	@Override
    public int getRemainDay(int nowMonth,int[] M,int nowDay){
       int remainDay=0;
       for(int i=nowMonth;i<12;i++){
       	remainDay += M[i];
       }
       remainDay += (M[nowMonth-1] - nowDay) +1;
 	return remainDay;
    }
    
    /**
 	* 根据入职日期判断计算范围
 	* @param p_in_date 员工入职时间
 	* @param overDay 对于本年，该员工入职日期前的总天数
 	* @param remainDay 对于本年，该员工入职日期后的总天数
 	* @return 司龄标记String
 	*/
	@Override
	public String computeCompanyDayFlag(String p_in_date, int overDay, int remainDay) {
		String[] inDate = p_in_date.split("-");
		Date date = new Date();
		Calendar calendar = Calendar.getInstance(); //日历对象
		calendar.setTime(date); //设置当前日期
		int nowYear = calendar.get(Calendar.YEAR); //当前年
		int nowMonth = calendar.get(Calendar.MONTH) + 1; //当前月
		int nowDay = calendar.get(Calendar.DATE); //当前日
		int inYear = Integer.parseInt(inDate[0]); //入职年
		int inMonth = Integer.parseInt(inDate[1]); //入职月
		int inDay = Integer.parseInt(inDate[2]); //入职日
		String CDflag = "0";
		if (nowYear - inYear < 1) { //年相减小于1，肯定入职不满一年，年假0
			CDflag = "0";
		} else if (nowYear - inYear == 1) { //年相减等于1
			if (nowMonth - inMonth < 0) { //月相减小于0，入职未满一年，年假0
				CDflag = "0";
			} else if (nowMonth - inMonth == 0) { //月相减等于0
				if (nowDay - inDay == 0) { //日相减小于等于0，入职未满一年，年假0
					CDflag = "0";
				} else if (0 < nowDay - inDay) { //日相减大于0，入职满一年，年假按1-10的算
					CDflag = "1";
				}
			} else if (0 < nowMonth - inMonth) { //月相减大于0，入职刚满一年，年假按1-10的算
				CDflag = "1";
			}
		} else if (1 < nowYear - inYear && nowYear - inYear < 10) { //年相减大于1小于10，年假40
			CDflag = "2";
		} else if (nowYear - inYear == 10) { //年相减等于10
			if (nowMonth - inMonth < 0) { //月相减小于等于0，入职未满十年，年假40
				CDflag = "2";
			} else if (nowMonth - inMonth == 0) { //月相减等于0
				if (nowDay - inDay == 0) { //日相减小于等于0，入职未满十年，年假40
					CDflag = "2";
				} else if (0 < nowDay - inDay) { //日相减大于0，入职满十年，年假前半段按1-10的算后半段按10-20的算
					CDflag = "3";
				}
			} else if (0 < nowMonth - inMonth) { //月相减大于0，入职满十年，年假前半段按1-10的算后半段按10-20的算
				CDflag = "3";
			}
		} else if (10 < nowYear - inYear && nowYear - inYear < 20) { //年相减大于10小于20，年假80
			CDflag = "4";
		} else if (nowYear - inYear == 20) { //年相减等于20
			if (nowMonth - inMonth < 0) { //月相减小于0，入职未满十年，年假80
				CDflag = "4";
			} else if (nowMonth - inMonth == 0) { //月相减等于0
				if (nowDay - inDay == 0) { //日相减小于等于0，入职未满二十年，年假80
					CDflag = "4";
				} else if (0 < nowDay - inDay) { //日相减大于0，入职满二十年，年假前半段按10-20的算后半段按20以上的算
					CDflag = "5";
				}
			} else if (0 < nowMonth - inMonth) { //月相减大于0，入职满十年，年假前半段按10-20的算后半段按20以上的算
				CDflag = "5";
			}
		} else { //年相减大于20，年假120
			CDflag = "6";
		}
		return CDflag;
	} 
      
    /**
   	* 计算年假天数，生成员工年假list
   	* @param list "工号"、"姓名"、"入职日期"、"原来的司龄标记"、"现在的司龄标记"，"对本年，入职日期前的总天数"、"对本年，入职日期后的总天数"
   	* @return List:工号、姓名、司龄标记、本年正常年假（还未计算病假导致的扣减）
   	*/
	@Override
	public List < Map < String,
	Object >> setAnnualLeavelist(List < Map < String, Object >> list) {
		List < Map < String,
		Object >> listAL = new ArrayList < Map < String,
		Object >> ();
		for (Map < String, Object > map: list) { //list为员工编号、员工名字、员工司龄标志    		
			Map < String,
			Object > map1 = new HashMap < String,
			Object > ();
			String number = map.get("p_number").toString(); //工号
			String name = map.get("p_name").toString(); //姓名
			String p_in_date = map.get("p_in_date").toString(); //入职日期
			String companydayflag = map.get("p_companydayflag2").toString(); //当前的司龄标记
			int overday = (int) map.get("overday");
			int remainday = (int) map.get("remainday");
			map1.put("p_number", number);
			map1.put("p_name", name);
			map1.put("p_in_date", p_in_date);
			map1.put("p_companydayflag", companydayflag);
			int nannualleave = computeAnnualLeave(companydayflag, overday, remainday); //本年年假数
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
	 * 根据司龄标记计算年假小时数
	 * @param p_companydayflag 司龄标记
	 * @param overDay 对本年，入职日期前的总天数
	 * @param remainDay 对本年，入职日期后的总天数
	 * @return 年假小时数
	 * */
	@Override
	public int computeAnnualLeave(String p_companydayflag, int overDay, int remainDay) {
		int al = 0;
		double yearday = 365;
		double day4 = 40;
		double day8 = 80;
		double day12 = 120;
		double oday = overDay;
		double rday = remainDay;
		if (p_companydayflag == "0") { //司龄不满一年，年假为0
			al = 0;
		} else if (p_companydayflag == "1") { //司龄刚满一年，当年剩余的日历天数/365*40，向下取整
			double c = rday / yearday * day4;
			/*System.out.println("c===>"+c);//1.75
	       		 System.out.println("c===>"+Math.ceil(c));//2.0
	       		 System.out.println("c===>"+Math.floor(c));//1.0*/
			al = (int) Math.floor(Math.floor(c));
		} else if (p_companydayflag == "2") { //司龄1到10年间，年假40
			al = 40;
		} else if (p_companydayflag == "3") { //司龄刚满十年，当年已过的日历天数/365*40+当年剩余的日历天数/365*80，向下取整
			double c = oday / yearday * day4 + rday / yearday * day8;
			al = (int) Math.floor(c);
		} else if (p_companydayflag == "4") { //司龄10到20年间，年假80
			al = 80;
		} else if (p_companydayflag == "5") { //司龄刚满二十年，当年已过的日历天数/365*80+当年剩余的日历天数/365*120，向下取整
			double c = oday / yearday * day8 + rday / yearday * day12;
			al = (int) Math.floor(c);
		} else if (p_companydayflag == "6") { //司龄20年以上，年假120
			al = 120;
		}
		return al;
	}
	@Override
	public int[] SaveAnnualLeave(List<Map<String, Object>> list,int nowYear) {
		return AnnualLeaveDao.SaveAnnualLeave(list,nowYear);
	}

}
