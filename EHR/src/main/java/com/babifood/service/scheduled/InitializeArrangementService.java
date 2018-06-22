package com.babifood.service.scheduled;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.babifood.dao.BaseArrangementDao;
import com.babifood.entity.InitAttendanceEntity;
import com.babifood.service.BaseArrangementService;
import com.babifood.service.InitAttendanceService;
import com.babifood.service.PersonInFoService;
import com.babifood.utils.UtilDateTime;
import com.babifood.utils.UtilString;

@Service
public class InitializeArrangementService {

	private static Logger logger = Logger.getLogger(InitializeArrangementService.class);

	@Autowired
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;

	@Autowired
	private BaseArrangementService baseArrangementService;

	@Autowired
	private PersonInFoService personInfoService;

	@Autowired
	private BaseArrangementDao baseArrangementDao;

	@Autowired
	private InitAttendanceService initAttendanceService;

	public void initializeArrangement() {
		Integer count = personInfoService.getPersonCount();
		// 该月所有排班信息
		Map<String, Map<String, Object>> arrangements = getAllArrangementsByMonth();
		final int threadCount = 500;// 每个线程初始化的员工数量
		int num = count % threadCount == 0 ? count / threadCount : count / threadCount + 1;// 线程数
		for (int i = 0; i < num; i++) {
			final int index = i;
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					initEmployeeArrangement(index, threadCount, arrangements);
				}
			});
			threadPoolTaskExecutor.execute(thread);
		}
	}

	/**
	 * 初始化排班
	 * 
	 * @param index
	 * @param threadCount
	 * @param arrangements
	 */
	@SuppressWarnings("unchecked")
	protected void initEmployeeArrangement(int index, int threadCount,
			Map<String, Map<String, Object>> arrangements) {
		List<Map<String, Object>> employeeList = personInfoService.findPagePersonInfo(index, threadCount);// 查询员工列表
		String year = UtilDateTime.getCurrentYear();// 当前年
		String month = UtilDateTime.getCurrentMonth();// 当前月
		int days = UtilDateTime.getDaysOfCurrentMonth();
		if (employeeList != null && employeeList.size() > 0) {
			for (Map<String, Object> map : employeeList) {
				String arrangementId = getArrangementId(map);// 获取员工对应排班id
				List<InitAttendanceEntity> attendanceList = new ArrayList<InitAttendanceEntity>();
				if (!UtilString.isEmpty(arrangementId)) {
					// 对应排班
					Map<String, Object> specialArrengements = arrangements.get(arrangementId);
					if (specialArrengements != null && specialArrengements.size() > 0) {
						String normalStartTime = specialArrengements.get("startTime") + "";// 标准上班时间
						String normalEndTime = specialArrengements.get("endTime") + "";// 标准下班时间
						for (int i = 1; i <= days; i++) {
							InitAttendanceEntity attendance = new InitAttendanceEntity();
							String date = year + "-" + month;// 当前日期
							if ((i + "").length() == 1) {
								date = date + "-0" + i;
							} else {
								date = date + "-" + i;
							}
							attendance.setDate(date);
							// 当前日期的排班
							Map<String, Object> specialArrengement = null;
							List<Map<String, Object>> specials = (List<Map<String, Object>>) specialArrengements.get("special");
							if(specials != null && specials.size() >0){
								for (Map<String, Object> special : specials) {
									if (date.equals(special.get("date"))) {
										specialArrengement = special;
										break;
									}
								}
							}
							String weekDay = UtilDateTime.getWeekDay(date);// 星期几
							attendance.setWeekDay(weekDay);
							// 标准上下班时间设置
							if (specialArrengement != null) {// 存在特殊排班
								if ("0".equals(specialArrengement.get("isAttend"))) {// 不考勤
									attendance.setNormalStartTime(normalStartTime);
									attendance.setNormalEndTime(normalEndTime);
								} else {// 考勤
									attendance.setNormalStartTime(specialArrengement.get("startTime") + "");
									attendance.setNormalEndTime(specialArrengement.get("endTime") + "");
								}
							} else {// 不存在特殊排班
								if ("星期日".equals(weekDay) || "星期六".equals(weekDay)) {
									attendance.setNormalStartTime("09:00");
									attendance.setNormalEndTime("16:00");
								} else {
									attendance.setNormalStartTime(normalStartTime);
									attendance.setNormalEndTime(normalEndTime);
								}
							}
							attendance.setAbsenceHours("0.00");
							attendance.setArrangementType(specialArrengements.get("arrangementType") + "");
							attendance.setCompanionParentalVacation("0.00");
							attendance.setCompanyCode(map.get("companyCode") + "");
							attendance.setCompletion("1");
							// attendance.setCreateTime(createTime);
							attendance.setDeptCode(map.get("deptCode") + "");
							attendance.setFuneralVacation("0.00");
							 attendance.setGroupCode("");//TODO
							attendance.setLate("0");
							attendance.setLeave("0");
							attendance.setMarriageVacation("0.00");
							attendance.setMealSupplement("0");
							attendance.setMonth(month + "");
							attendance.setNormalTime(
									Math.round(Math.floor(UtilDateTime.getHours(normalStartTime, normalEndTime)))
											+ ".00");
							attendance.setOfficeCode(map.get("officeCode") + "");
							attendance.setOriginalTime("0.00");
							attendance.setOrganizationCode(map.get("organizationCode") + "");
							attendance.setOvertimeHours("0.00");
							attendance.setParentalVacation("0.00");
							attendance.setpName(map.get("pName") + "");
							attendance.setpNumber(map.get("pNumber") + "");
							attendance.setPost(map.get("postName") + "");
							attendance.setPunchEndTime("");
							attendance.setPunchStartTime("");
							attendance.setPunchType("");
							attendance.setRelaxation("0.00");
							attendance.setSickVacation("0.00");
							attendance.setThingVacation("0.00");
							attendance.setTrainVacation("0.00");
							attendance.setTravelHours("0.00");
							attendance.setUnusualHours("0.00");
							// attendance.setUpdateTime(updateTime);
							attendance.setVacationHours("0.00");
							attendance.setWorkTime("0.00");
							attendance.setYear(year + "");
							attendance.setYearVacation("0.00");
							attendanceList.add(attendance);
						}
					} else {
						logger.error("该员工未绑定排班，姓名：" + map.get("pName") + ",工号:" + map.get("pNumber"));
					}
				}
				// 插入员工初始排班数据
				if (attendanceList.size() > 0) {
					String pNumber = map.get("pNumber")+"";
					boolean isAttend = initAttendanceService.isInitAttendance(pNumber, year, month);
					if(!isAttend){
						initAttendanceService.addInitAttendance(attendanceList);
					}
				}
			}
		}
	}

	public String getArrangementId(Map<String, Object> map) {
		List<String> targetIds = new ArrayList<String>();
		if (!UtilString.isEmpty(map.get("companyCode") + "")) {
			targetIds.add(map.get("companyCode") + "");
		}
		if (!UtilString.isEmpty(map.get("deptCode") + "")) {
			targetIds.add(map.get("deptCode") + "");
		}
		if (!UtilString.isEmpty(map.get("organizationCode") + "")) {
			targetIds.add(map.get("organizationCode") + "");
		}
		if (!UtilString.isEmpty(map.get("officeCode") + "")) {
			targetIds.add(map.get("officeCode") + "");
		}
		// if(!UtilString.isEmpty(person.getP_company_id())){
		// targetIds.add(person.getP_company_id());
		// }
		targetIds.add(map.get("pNumber") + "");
		List<Map<String, Object>> arrangementList = baseArrangementDao.findArrangementByTargetId(targetIds);
		if (arrangementList != null && arrangementList.size() > 0) {
			return arrangementList.get(0).get("arrangementId") + "";
		}
		return null;
	}

	/**
	 * 查询当月所有排班信息
	 * 
	 * @return
	 */
	public Map<String, Map<String, Object>> getAllArrangementsByMonth() {
		Map<String, Map<String, Object>> arrangements = new HashMap<String, Map<String, Object>>();
		String year = UtilDateTime.getCurrentYear();
		String month = UtilDateTime.getCurrentMonth();
		int days = UtilDateTime.getDaysOfCurrentMonth();
		String startTime = year + "-" + month + "-01";
		String endTime = year + "-" + month + "-" + days;
		List<Map<String, Object>> arrangmentList = baseArrangementService.findBaseArrangements();
		List<Map<String, Object>> spcialArrangmentList = baseArrangementService.findCurrentMonthAllSpecialArrangement(startTime,endTime);
		if (arrangmentList != null && arrangmentList.size() > 0) {
			for (Map<String, Object> map : arrangmentList) {
				String arrangementId = map.get("id") + "";
				if(spcialArrangmentList != null && spcialArrangmentList.size() > 0){
					List<Map<String, Object>> specialArrangment = new ArrayList<Map<String, Object>>();
					for(Map<String, Object> specialMap:spcialArrangmentList){
						if(arrangementId.equals(specialMap.get("arrangementId")+"")){
							specialArrangment.add(specialMap);
						}
					}
					map.put("special", specialArrangment);
				}
				arrangements.put(arrangementId, map);
			}
		}
		return arrangements;
	}

}
