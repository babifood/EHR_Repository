package com.babifood.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.babifood.dao.AnnualLeaveDao;
import com.babifood.dao.BaseArrangementDao;
import com.babifood.service.AnnualLeaveService;
import com.babifood.utils.CustomerContextHolder;
import com.babifood.utils.UtilDateTime;
import com.babifood.utils.UtilString;

@Service
public class AnnualLeaveServiceImpl implements AnnualLeaveService {
	
	Logger logger = Logger.getLogger(AnnualLeaveServiceImpl.class);
	
	@Autowired
	AnnualLeaveDao annualLeaveDao;
	
	@Autowired
	BaseArrangementDao baseArrangementDao;

	@Override
	public List<Map<String, Object>> loadNowAnnualLeave(String npname, String npnumber) {
		return annualLeaveDao.loadNowAnnualLeave(npname, npnumber);
	}

	@Override
	public List<Map<String, Object>> loadHistoryAnnualLeave(String lpname) {
		return annualLeaveDao.loadHistoryAnnualLeave(lpname);
	}

	@Override
	public List<Map<String, Object>> GetHireDate() {
		return annualLeaveDao.GetHireDate();
	}

	/**
	 * 生成变动的最新司龄（年），的员工list
	 * 
	 * @param list
	 *            员工档案取所有员工的"工号"、"姓名"、"入职日期"+从当前年假记录表中取员工的"司龄"、"入职日期"
	 * @return List:"工号"、"姓名"、"入职日期"、"原来的司龄"、"现在的司龄"，"对本年，入职日期前的总天数"、"对本年，入职日期后的总天数"
	 */
	@Override
	public List<Map<String, Object>> setCompanyDayFlag(List<Map<String, Object>> list) {
		List<Map<String, Object>> listCD = new ArrayList<Map<String, Object>>();
		String indate = UtilDateTime.getCurrentTime("yyyy-MM-dd");
		for (Map<String, Object> map : list) {// list为数据库查询出来的数据
			String number = map.get("p_number").toString();
			String date = map.get("p_in_date").toString(); // 当前时刻员工档案里的员工入职日期
			int companydayflag = map.get("p_companydayflag") != null
					? Integer.parseInt(map.get("p_companydayflag").toString()) : 0;// 若该员工原来没有司龄（年），那么给他标-1
			Map<String, Integer> companyAge = computeCompanyDayFlag(date);// 计算新的司龄\对于本年，该员工入职日期前的总天数\对于本年，该员工入职日期后的总天数
			String currentYear = UtilDateTime.getCurrentYear();
			if (companydayflag != companyAge.get("currentCAge") || (currentYear + "-01-01").equals(indate)
					|| (currentYear + "-07-01").equals(indate)) {// 司龄不一样，或者入职日期有变更，都要更新该员工的年假记录
				map.put("p_companydayflag", companydayflag);// 原来的司龄
				map.putAll(companyAge);
				listCD.add(map);
				if (companydayflag != companyAge.get("currentCAge")) {
					annualLeaveDao.updateEmpCompanyAge(number, companyAge.get("currentCAge"));
				}
			}
		}
		return listCD;
	}

	/**
	 * 根据入职日期判断计算范围
	 * 
	 * @param p_in_date
	 *            员工入职时间
	 * @return 司龄\对于本年，该员工入职日期前的总天数\对于本年，该员工入职日期后的总天数
	 */
	@Override
	public Map<String, Integer> computeCompanyDayFlag(String p_in_date) {
		Map<String, Integer> companyAge = new HashMap<>();
		Date pindate = null;
		try {
			pindate = new SimpleDateFormat("yyyy-MM-dd").parse(p_in_date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		c.setTime(pindate);
		c.set(Calendar.YEAR, year);
		int overday = c.get(Calendar.DAY_OF_YEAR);// 换了年份的入职时间点，对于本年，该员工入职日期前的总天数
		int alloverday = (int) ((new Date().getTime() - pindate.getTime()) / (24 * 60 * 60 * 1000));// 该员工入职到现在的总天数
		int companyday = alloverday / 365;// 司龄（年）
		int overCompanyDay = (int) ((c.getTime().getTime() - pindate.getTime()) / (24 * 60 * 60 * 1000));
		companyAge.put("currentCAge", companyday);
		companyAge.put("overday", overday);
		companyAge.put("cAge", overCompanyDay / 365);
		return companyAge;
	}

	/**
	 * 计算年假天数，生成员工年假list
	 * 
	 * @param list
	 *            "工号"、"姓名"、"入职日期"、"原来的司龄"、"现在的司龄"，"对本年，入职日期前的总天数"、"对本年，入职日期后的总天数"
	 * @return List:工号、姓名、司龄、本年正常年假（还未计算病假导致的扣减）
	 * @throws ParseException
	 */
	@Override
	public List<Map<String, Object>> setAnnualLeavelist(List<Map<String, Object>> list) throws ParseException {
		List<Map<String, Object>> listAL = new ArrayList<Map<String, Object>>();
		if(list != null && list.size() > 0){
			String year = UtilDateTime.getCurrentYear();
			Map<String, List<Map<String, Object>>> nianjiaDanju = findOANianjia(year);//OA单据
			Map<String, Map<String, Object>> currentNianjia = findCurrentNianjia(year);//本年年假记录
			Map<String, Map<String, Object>> lastYearNianjia = findCurrentNianjia((Integer.valueOf(year) - 1) + "");//上年的年假记录（每年1月1日使用）
			Map<String, Integer> bingjia = findTotalBingjia((Integer.valueOf(year) - 1) + "");
			for (Map<String, Object> map : list) { // list为员工编号、员工名字、员工司龄标志
				String number = map.get("p_number").toString(); // 工号
				try {
					Map<String, Object> map1 = new HashMap<String, Object>();
					String name = map.get("p_id").toString(); // 姓名
					String p_in_date = map.get("p_in_date").toString(); // 入职日期
					int companydayflag = (int) map.get("currentCAge"); // 当前的司龄
					map1.put("p_number", number);
					map1.put("p_name", name);
					map1.put("p_in_date", p_in_date);
					map1.put("p_companydayflag", companydayflag);
					map1.put("year", year);
					map1.put("companyName", map.get("companyName"));
					map1.put("organizationName", map.get("organizationName"));
					int nannualleave = computeAnnualLeave(map); // 本年年假数，根据上年请病假的天数做扣减未做，待修正
					map1.put("nannualleave", nannualleave);
					int lannualleave = 0; // 上年结余数，待修正，应该在每年的1月1日取上一年最后的剩余年假数，之后持续一年时间不变
					Map<String, Object> nianjiaMap = currentNianjia.get(map.get("p_number") + "");
					if (currentNianjia.get(map.get("p_number") + "") != null) {
						lannualleave = (int) nianjiaMap.get("nannualleave");
					} else if (lastYearNianjia.get(map.get("p_number") + "") != null) {
						lannualleave = (int) nianjiaMap.get("remaindata");
					}
					Integer bingjiaHours = bingjia.get(number) == null ? 0 : bingjia.get(number);
					if(bingjiaHours > getArrangementType(map)){
						lannualleave = 0;
					}
					map1.put("lannualleave", lannualleave);
					int useddata = getUsedNianJia(nianjiaDanju.get(map.get("p_number") + ""));
					map1.put("useddata", useddata);
					int disableddata = 0;// 自动作废数，待修正，每年7月1号的时候，看“已使用数”是否大于等于“上年结余数”，如果不大于，那么等于“上年结余数”-“已使用数”，否则等于0
					if (UtilDateTime.getDaySpace(year + "-07-01", UtilDateTime.getCurrentTime("yyyy-MM-dd")) == 0) {
						if (nianjiaMap != null && nianjiaMap.size() > 0) {
							disableddata = ((int) nianjiaMap.get("nannualleave") - (int) nianjiaMap.get("useddata")) > 0
									? ((int) nianjiaMap.get("nannualleave") - (int) nianjiaMap.get("useddata")) : 0;
						}
					} else {
						if (nianjiaMap != null && nianjiaMap.size() > 0) {
							disableddata = (int) nianjiaMap.get("disableddata");
						}
					}
					map1.put("disableddata", disableddata);
					map1.put("remaindata", nannualleave + lannualleave - useddata - disableddata); // 剩余年假数
					map1.put("nannualleavedeadline", (Integer.valueOf(year) - 1) + "-07-01");
					map1.put("lannualleavedeadline", year + "-07-01");
					map1.put("OAID", getID());
					listAL.add(map1);
				} catch (Exception e) {
					logger.error("计算年假失败；pNumber=" + number, e);
				}
				
			}
		}
		return listAL;
	}
	
	private String getID(){
		String[] nums = new String[]{"0","1","2","3","4","5","6","7","8","9"}; 
		Random random = new Random();

		String id = "";
		int num = random.nextInt(10);
		if(num < 4){
			id +="-";
		}
		int index = random.nextInt(4) + 15;
		for(int i = 0; i < index ; i++){
			num = random.nextInt(10);
			id += nums[num];
		}
		return id;
	}
	
	/**
	 * 获取2个月病假小时数
	 * @param employee
	 * @return
	 */
	private Integer getArrangementType(Map<String, Object> employee) {
		List<String> targetIds = new ArrayList<String>();
		if (!UtilString.isEmpty(employee.get("companyCode")+"")) {
			targetIds.add(employee.get("companyCode")+"");
		}
		if (!UtilString.isEmpty(employee.get("deptCode")+"")) {
			targetIds.add(employee.get("deptCode")+"");
		}
		if (!UtilString.isEmpty(employee.get("organizationCode")+"")) {
			targetIds.add(employee.get("organizationCode")+"");
		}
		if (!UtilString.isEmpty(employee.get("officeCode")+"")) {
			targetIds.add(employee.get("officeCode")+"");
		}
		if(!UtilString.isEmpty(employee.get("groupCode")+"")){
			targetIds.add(employee.get("groupCode")+"");
		}
		targetIds.add(employee.get("p_number")+"");
		String arrangementType = "1";
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_EHR);
		List<Map<String, Object>> arrangementList = baseArrangementDao.findArrangementByTargetId(targetIds);
		if (arrangementList != null && arrangementList.size() > 0) {
			arrangementType = arrangementList.get(0).get("arrangementType") + "";
		}
		Integer sickHours = 372;
		if("1".equals(arrangementType)){
			sickHours = 372;
		} else if("2".equals(arrangementType)){
			sickHours = 372;
		} else if("3".equals(arrangementType)){
			sickHours = 348;
		} else if("4".equals(arrangementType)){
			sickHours = 412;
		}
		return sickHours;
	}

	/**
	 * 查询上年员工累计病假
	 * @param string
	 * @return
	 */
	private Map<String, Integer> findTotalBingjia(String year) {
		Map<String, Integer> bingjia = new HashMap<String, Integer>();
		List<Map<String, Object>> bingjiaList = annualLeaveDao.findTotalBingjia(year);
		if(bingjiaList != null && bingjiaList.size() > 0){
			for(Map<String, Object> map : bingjiaList){
				bingjia.put(map.get("pNumber") + "", Integer.valueOf(map.get("bingJia")+""));
			}
		}
		return bingjia;
	}

	/**
	 * 计算本年已使用年假数
	 * 
	 * @param list
	 * @return
	 */
	private int getUsedNianJia(List<Map<String, Object>> list) {
		Integer usedNianjia = 0;
		if (list != null && list.size() > 0) {
			for (Map<String, Object> map : list) {
				usedNianjia += Integer.valueOf(map.get("timelength") + "");
			}
		}
		return usedNianjia;
	}

	/**
	 * 查询当年
	 * 
	 * @param year
	 * @return
	 */
	private Map<String, Map<String, Object>> findCurrentNianjia(String year) {
		Map<String, Map<String, Object>> nainjia = new HashMap<String, Map<String, Object>>();
		List<Map<String, Object>> currentNianjiaLit = annualLeaveDao.findCurrentNianjiaLit(year);
		if (currentNianjiaLit != null && currentNianjiaLit.size() > 0) {
			for (Map<String, Object> map : currentNianjiaLit) {
				nainjia.put(map.get("worknum") + "", map);
			}
		}
		return nainjia;
	}

	/**
	 * 查询 OA年假单据
	 * 
	 * @param year
	 * @return
	 */
	private Map<String, List<Map<String, Object>>> findOANianjia(String year) {
		Map<String, List<Map<String, Object>>> nianJiaMap = new HashMap<String, List<Map<String, Object>>>();
		List<Map<String, Object>> nianJiaList = annualLeaveDao.findOANianjia(year + "-01-01");
		if (nianJiaList != null && nianJiaList.size() > 0) {
			for (Map<String, Object> map : nianJiaList) {
				List<Map<String, Object>> nianJias = nianJiaMap.get(map.get("worknum" + ""));
				if (nianJias == null || nianJias.size() == 0) {
					nianJias = new ArrayList<Map<String, Object>>();
				}
				nianJias.add(map);
				nianJiaMap.put(map.get("worknum") + "", nianJias);
			}
		}
		return nianJiaMap;
	}

	/**
	 * 根据司龄计算年假小时数
	 * 
	 * @param p_companydayflag
	 *            司龄
	 * @param overDay
	 *            对本年，入职日期前的总天数
	 * @param remainDay
	 *            对本年，入职日期后的总天数
	 * @return 年假小时数
	 */
	public int computeAnnualLeave(Map<String, Object> map) {
		int al = 0;
		double oday = Double.valueOf(map.get("overday") + "");
		Integer cAge = (Integer) map.get("cAge");
		Integer currentCAge = (Integer) map.get("currentCAge");
		if (currentCAge < 1) { // 司龄0，年假为0
			al = 0;
		} else if (currentCAge == 1) { // 司龄1，当年剩余的日历天数/365*40，向下取整
			double c = cAge == currentCAge ? (365 - oday) / 365 * 40 : 0.0;
			al = (int) Math.floor(Math.floor(c));
			al = (al/4) * 4;
		} else if (currentCAge < 10) { // 司龄2到9年，年假40
			al = 40;
		} else if (currentCAge == 10) { // 司龄10年，当年已过的日历天数/365*40+当年剩余的日历天数/365*80，向下取整
			double c = cAge == currentCAge ? (oday / 365 * 40 + (365 - oday) / 365 * 80) : (oday / 365 * 40);
			al = (int) Math.floor(c);
			al = (al/4) * 4;
		} else if (currentCAge < 20) { // 司龄11到19年间，年假80
			al = 80;
		} else if (currentCAge == 20) { // 司龄20年，当年已过的日历天数/365*80+当年剩余的日历天数/365*120，向下取整
			double c = cAge == currentCAge ? (oday / 365 * 40 + (365 - oday) / 365 * 120) : (oday / 365 * 80);
			al = (int) Math.floor(c);
			al = (al/4) * 4;
		} else { // 司龄20年以上，年假120
			al = 120;
		}
		return al;
	}

	@Override
	public void SaveAnnualLeave(List<Map<String, Object>> list) {
		annualLeaveDao.SaveAnnualLeave(list);
		String year = UtilDateTime.getCurrentYear();
		List<String> ids = new ArrayList<String>();
		for(Map<String, Object> map: list){
			ids.add(map.get("p_number") + "");
		}
		annualLeaveDao.pushAnnualToOA(ids, list, year);
	}

}
