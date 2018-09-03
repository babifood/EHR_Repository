package com.babifood.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.babifood.dao.PunchTimeDao;
import com.babifood.entity.PunchTimeEntity;
import com.babifood.utils.UtilString;

@Repository
public class PunchTimeDaoImpl implements PunchTimeDao {

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
		} catch (Exception e) {
			throw e;
		}
		return count;
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
