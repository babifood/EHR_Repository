package com.babifood.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.babifood.dao.EmployeeBaseSettingDao;
import com.babifood.entity.BaseSettingEntity;
import com.babifood.utils.UtilString;

@Repository
public class EmployeeBaseSettingDaoImpl implements EmployeeBaseSettingDao {

	private static Logger log = Logger.getLogger(EmployeeBaseSettingDaoImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public int getCountOfBaseSettingInfo(Map<String, Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT count(*) FROM ehr_employee_base_setting b ");
		sql.append("LEFT JOIN ehr_person_basic_info a on a.p_number = b.p_number ");
		sql.append("WHERE a.P_state = '0'");
		if (!UtilString.isEmpty(params.get("ismeal") + "")) {
			sql.append(" AND b.is_meal = " + params.get("ismeal"));
		}
		if (!UtilString.isEmpty(params.get("workPlace") + "")) {
			sql.append(" AND b.work_place = " + params.get("workPlace"));
		}
		if (!UtilString.isEmpty(params.get("workType") + "")) {
			sql.append(" AND b.work_type = " + params.get("workType"));
		}
		if (!UtilString.isEmpty(params.get("pNumber") + "")) {
			sql.append(" AND a.p_number like '%" + params.get("pNumber") + "%'");
		}
		if (!UtilString.isEmpty(params.get("pnumber") + "")) {
			sql.append(" AND a.p_number ='" + params.get("pnumber") + "'");
		}
		if (!UtilString.isEmpty(params.get("pName") + "")) {
			sql.append(" AND a.p_name like '%" + params.get("pName") + "%'");
		}
		int total = 0;
		try {
			total = jdbcTemplate.queryForInt(sql.toString());
		} catch (Exception e) {
			log.error("查询基础设置信息总数失败", e);
			throw e;
		}
		return total;
	}

	@Override
	public List<Map<String, Object>> getPageBaseSettingInfo(Map<String, Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT b.id as id, b.is_meal as ismeal, a.p_number as pNumber,a.p_name as pName,");
		sql.append("b.work_type as workType, b.work_place as workPlace ");
		sql.append(" FROM ehr_employee_base_setting b ");
		sql.append("LEFT JOIN ehr_person_basic_info a on a.p_number = b.p_number ");
		sql.append("WHERE a.P_state = '0'");
		if (!UtilString.isEmpty(params.get("ismeal") + "")) {
			sql.append(" AND b.is_meal = " + params.get("ismeal"));
		}
		if (!UtilString.isEmpty(params.get("workPlace") + "")) {
			sql.append(" AND b.work_place = " + params.get("workPlace"));
		}
		if (!UtilString.isEmpty(params.get("workType") + "")) {
			sql.append(" AND b.work_type = " + params.get("workType"));
		}
		if (!UtilString.isEmpty(params.get("pNumber") + "")) {
			sql.append(" AND a.p_number like '%" + params.get("pNumber") + "%'");
		}
		if (!UtilString.isEmpty(params.get("pName") + "")) {
			sql.append(" AND a.p_name like '%" + params.get("pName") + "%'");
		}
		sql.append(" limit ? , ?");
		List<Map<String, Object>> baseSettingList = null;
		try {
			baseSettingList = jdbcTemplate.queryForList(sql.toString(), params.get("start"), params.get("pageSize"));
		} catch (Exception e) {
			log.error("分页查询基础设置信息失败", e);
			throw e;
		}
		return baseSettingList;
	}

	@Override
	public void insertBaseSettingInfo(BaseSettingEntity baseSetting) {
		String sql = "INSERT INTO `ehr_employee_base_setting` (`p_number`, `work_type`, `is_meal`, `work_place`) VALUES (?, ?, ?, ?)";
		try {
			jdbcTemplate.update(sql, baseSetting.getpNumber(), baseSetting.getWorkType(), baseSetting.getIsmeal(),
					baseSetting.getWorkPlace());
		} catch (Exception e) {
			log.error("新增基础设置信息失败", e);
			throw e;
		}
	}

	@Override
	public void updateBaseSettingInfo(BaseSettingEntity baseSetting) {
		String sql = "UPDATE ehr_employee_base_setting SET `p_number`=?, `work_type`=?, `is_meal`=?, `work_place`=? WHERE `ID`=?";
		try {
			jdbcTemplate.update(sql, baseSetting.getpNumber(), baseSetting.getWorkType(), baseSetting.getIsmeal(),
					baseSetting.getWorkPlace(), baseSetting.getId());
		} catch (Exception e) {
			log.error("修改基础设置信息失败", e);
			throw e;
		}
	}

	@Override
	public Map<String, Object> findBaseSettingByPnumber(String pNumber) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT p_number as pNumber, work_type as workType, work_place as workPlace, is_meal AS isMeal ");
		sql.append("FROM ehr_employee_base_setting WHERE p_number = ?");
		Map<String, Object> baseSetting = null;
		try {
			baseSetting = jdbcTemplate.queryForMap(sql.toString(), pNumber);
		} catch (Exception e) {
			log.error("查询员工基础设置信息失败，pNumber=" + pNumber, e);
			throw e;
		}
		return baseSetting;
	}

}
