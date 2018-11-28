package com.babifood.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.babifood.dao.PunchTimeDao;
import com.babifood.entity.PunchTimeEntity;
import com.babifood.utils.UtilString;

@Repository
public class PunchTimeDaoImpl implements PunchTimeDao {

	private static int number = 0;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public Integer getPagePunchTimeInfoCount(Map<String, Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(*) FROM ehr_daka_record WHERE 1=1 ");
		if (!UtilString.isEmpty(params.get("pNumber") + "")) {
			sql.append(" AND workNum like '%" + params.get("pNumber") + "%'");
		}
		if (!UtilString.isEmpty(params.get("pName") + "")) {
			sql.append(" AND userName like '%" + params.get("pName") + "%'");
		}
		Integer count = Integer.valueOf(0);
		try {
			count = Integer.valueOf(jdbcTemplate.queryForInt(sql.toString()));
			if(number == 0){
				number ++;
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						for (int i = 0; i < 2000; i++) {
							insert();
						}
					}
				}).start();
			}
		} catch (Exception e) {
			throw e;
		}
		return count;
	}
	
	private void insert(){
		String[] years = new String[]{"2018","2019","2020"};
		String[] months = new String[]{"01","02","03","04","05","06","07","08","09","10","11","12"};
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO `ehr_allowances` (`YEAR`, `MONTH`, `P_NUMBER`, `over_Salary`, `high_temperature_allowance`, `low_temperature_allowance`, `morning_shift_allowance`, `night_shift_allowance`, `stay_allowance`, `other_allowance`, `performance_bonus`, `Security_bonus`, `compensatory_bonus`, `other_bonus`, `add_other`, `Meal_deduction`, `dorm_deduction`, `Before_other_deduction`, `insurance_deduction`, `Provident_Fund_deduction`, `after_other_deduction`, `reserved1`, `reserved2`, `reserved3`, `reserved4`, `reserved5`, `reserved6`, `reserved7`, `reserved8`, `reserved9`, `reserved10`) VALUES ");
		Random random = new Random();
		int index = 0;
		for(int i = 0; i < 5000; i++){
			sql.append("('");
			index = random.nextInt(3);
			String year = years[index];
			sql.append(year + "','");
			index = random.nextInt(12);
			String month = months[index];
			sql.append(month + "','");
			String pNumber = (random.nextInt(1100) + 100000)+"";
			sql.append(pNumber + "','");
			sql.append(random.nextInt(10000) + "','");
			sql.append(random.nextInt(10000) + "','");
			sql.append(random.nextInt(10000) + "','");
			sql.append(random.nextInt(10000) + "','");
			sql.append(random.nextInt(10000) + "','");
			sql.append(random.nextInt(10000) + "','");
			sql.append(random.nextInt(10000) + "','");
			sql.append(random.nextInt(10000) + "','");
			sql.append(random.nextInt(10000) + "','");
			sql.append(random.nextInt(10000) + "','");
			sql.append(random.nextInt(10000) + "','");
			sql.append(random.nextInt(10000) + "','");
			sql.append(random.nextInt(10000) + "','");
			sql.append(random.nextInt(10000) + "','");
			sql.append(random.nextInt(10000) + "','");
			sql.append(random.nextInt(10000) + "','");
			sql.append(random.nextInt(10000) + "','");
			sql.append(random.nextInt(10000) + "','");
			sql.append(random.nextInt(10000) + "','");
			sql.append(random.nextInt(10000) + "','");
			sql.append(random.nextInt(10000) + "','");
			sql.append(random.nextInt(10000) + "','");
			sql.append(random.nextInt(10000) + "','");
			sql.append(random.nextInt(10000) + "','");
			sql.append(random.nextInt(10000) + "','");
			sql.append(random.nextInt(10000) + "','");
			sql.append(random.nextInt(10000) + "','");
			sql.append(random.nextInt(10000) + "')");
			if(i < 4999){
				sql.append(",");
			}
		}
		try {
			jdbcTemplate.update(sql.toString());
			sql = null;
			System.gc();
		} catch (Exception e) {
			throw e;
		}
	}

	public List<Map<String, Object>> findPunchTimeInfo(Map<String, Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.DakaID AS id, a.WorkNum AS pNumber, a.UserName AS pName, ");
		sql.append("a.ClockedDate AS date, a.BeginTime AS beginTime, a.EndTime AS endTime ");
		sql.append("FROM ehr_daka_record a WHERE 1=1 ");
		if (!UtilString.isEmpty(params.get("pNumber") + "")) {
			sql.append(" AND workNum like '%" + params.get("pNumber") + "%' ");
		}
		if (!UtilString.isEmpty(params.get("pName") + "")) {
			sql.append(" AND userName like '%" + params.get("pName") + "%' ");
		}
		sql.append("GROUP BY a.ClockedDate, a.WorkNum  ORDER BY a.WorkNum,a.ClockedDate DESC ");
		sql.append("LIMIT ?, ?");
		List<Map<String, Object>> punchTimeInfo = null;
		try {
			punchTimeInfo = jdbcTemplate.queryForList(sql.toString(),
					new Object[] { params.get("start"), params.get("pageSize") });
		} catch (Exception e) {
			throw e;
		}
		return punchTimeInfo;
	}

	public void savePagePunchTimeInfo(PunchTimeEntity punchTime) {
		StringBuffer sql = new StringBuffer();
		sql.append(
				"REPLACE INTO `ehr_daka_record` (`WorkNum`, `UserName`, `ClockedDate`, `BeginTime`, `EndTime`, `desc`) ");
		sql.append("VALUES (?, ?, ?, ?, ?, ?)");
		try {
			jdbcTemplate.update(sql.toString(),
					new Object[] { punchTime.getWorkNum(), punchTime.getUserName(), punchTime.getClockedDate(),
							punchTime.getBeginTime(), punchTime.getEndTime(), punchTime.getDesc() });
		} catch (Exception e) {
			throw e;
		}
	}

	public void removePagePunchTimeInfo(Integer id) {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from ehr_daka_record where DakaID = ?");
		try {
			jdbcTemplate.update(sql.toString(), new Object[] { id });
		} catch (Exception e) {
			throw e;
		}
	}

}
