package com.babifood.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.babifood.dao.PerformanceDao;
import com.babifood.utils.BASE64Util;
import com.babifood.utils.UtilString;

@Repository
public class PerformanceDaoImpl extends AuthorityControlDaoImpl implements PerformanceDao {

	private static Logger log = Logger.getLogger(PerformanceDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 查询绩效薪资信息数量
	 */
	@Override
	public Integer getPerformancesCount(Map<String, Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(*) FROM ehr_performance a ");
		sql.append("INNER JOIN ehr_person_basic_info b ON a.p_number = b.p_number ");
		sql.append("LEFT JOIN ehr_dept c ON b.p_organization_id = c.DEPT_CODE ");
		sql.append("LEFT JOIN ehr_dept d ON b.p_department_id = d.DEPT_CODE ");
		sql.append("LEFT JOIN ehr_dept e ON b.p_section_office_id = e.DEPT_CODE where 1 = 1 ");
		if(!UtilString.isEmpty(params.get("pNumber")+"")){
			sql.append(" AND a.p_number like '%" + params.get("pNumber") + "%' ");
		}
		if(!UtilString.isEmpty(params.get("pName")+"")){
			sql.append(" AND b.p_name like '%" + params.get("pName") + "%' ");
		}
		if(!UtilString.isEmpty(params.get("organzationName")+"")){
			sql.append(" AND c.dept_name like '%" + params.get("organzationName") + "%' ");
		}
		if(!UtilString.isEmpty(params.get("deptName")+"")){
			sql.append(" AND d.dept_name like '%" + params.get("deptName") + "%' ");
		}
		if(!UtilString.isEmpty(params.get("officeName")+"")){
			sql.append(" AND e.dept_name like '%" + params.get("officeName") + "%' ");
		}
		Integer count = 0;
		try {
			sql = super.jointDataAuthoritySql("b.p_company_id", sql);
			count = jdbcTemplate.queryForInt(sql.toString());
		} catch (Exception e) {
			log.error("查询绩效数量信息失败", e);
			throw e;
		}
		return count;
	}

	/**
	 * 查询绩效薪资信息列表
	 */
	@Override
	public List<Map<String, Object>> queryPerformanceList(Map<String, Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.`YEAR` AS `year`, a.`MONTH` AS `month`, b.P_NUMBER AS pNumber, ");
		sql.append("a.performance_score AS performanceScore, b.p_name AS pName, ");
		sql.append("c.DEPT_NAME AS organzationName, d.DEPT_NAME AS deptName, e.DEPT_NAME AS officeName, ");
		sql.append("h.DEPT_NAME AS companyName, a.performance_salary AS pSalary ");
		sql.append("FROM ehr_person_basic_info b ");
		sql.append("INNER JOIN ehr_performance a ON a.p_number = b.p_number ");
		sql.append("LEFT JOIN ehr_dept c ON b.p_organization_id = c.DEPT_CODE ");
		sql.append("LEFT JOIN ehr_dept d ON b.p_department_id = d.DEPT_CODE ");
		sql.append("LEFT JOIN ehr_dept e ON b.p_section_office_id = e.DEPT_CODE ");
		sql.append("LEFT JOIN ehr_dept h on b.p_company_id = h.DEPT_CODE ");
		sql.append("LEFT JOIN ehr_base_salary f ON a.P_NUMBER = f.P_NUMBER ");
		sql.append("AND f.use_time = (SELECT MAX(use_time) FROM ehr_base_salary WHERE ");
		sql.append("use_time <= LAST_DAY(str_to_date(CONCAT(a.`YEAR`,'-',a.`MONTH`),'%Y-%m')) ");
		sql.append("AND P_NUMBER = a.P_NUMBER) where 1 = 1 ");
		if(!UtilString.isEmpty(params.get("year")+"")){
			sql.append(" AND a.`YEAR` = " + params.get("year"));
		}
		if(!UtilString.isEmpty(params.get("month")+"")){
			sql.append(" AND a.`MONTH` =" + params.get("month"));
		}
		if(!UtilString.isEmpty(params.get("pNumber")+"")){
			sql.append(" AND a.p_number like '%" + params.get("pNumber") + "%' ");
		}
		if(!UtilString.isEmpty(params.get("pName")+"")){
			sql.append(" AND b.p_name like '%" + params.get("pName") + "%' ");
		}
		if(!UtilString.isEmpty(params.get("organzationName")+"")){
			sql.append(" AND c.dept_name like '%" + params.get("organzationName") + "%' ");
		}
		if(!UtilString.isEmpty(params.get("deptName")+"")){
			sql.append(" AND d.dept_name like '%" + params.get("deptName") + "%' ");
		}
		if(!UtilString.isEmpty(params.get("officeName")+"")){
			sql.append(" AND e.dept_name like '%" + params.get("officeName") + "%' ");
		}
		sql = super.jointDataAuthoritySql("b.p_company_id", sql);
		if(!UtilString.isEmpty(params.get("start")+"") && !UtilString.isEmpty(params.get("pageSize")+"")){
			sql.append("GROUP BY a.`YEAR`, a.`MONTH`, a.P_NUMBER ORDER BY a.`YEAR` DESC, a.`MONTH` DESC ");
			sql.append("limit ?, ?");
		}
		List<Map<String, Object>> performanceList = null;
		try {
			if(UtilString.isEmpty(params.get("start")+"") || UtilString.isEmpty(params.get("pageSize")+"")){
				performanceList = jdbcTemplate.queryForList(sql.toString());
			} else {
				performanceList = jdbcTemplate.queryForList(sql.toString(), params.get("start"), params.get("pageSize"));
			}
			BASE64Util.Base64DecodeMap(performanceList);
		} catch (Exception e) {
			log.error("分页查询绩效信息列表失败", e);
			throw e;
		}
		return performanceList;
	}

	/**
	 * 批量保存绩效薪资信息
	 */
	@Override
	public void savePerformance(List<Object[]> performanceParam) {
		String sql = "REPLACE INTO `ehr_performance` (`YEAR`, `MONTH`, `P_NUMBER`, `performance_score`, `performance_salary`) VALUES (?, ?, ?, ?, ?)";
		try {
			jdbcTemplate.batchUpdate(sql, performanceParam);
		} catch (Exception e) {
			log.error("批量新增绩效薪资信息失败", e);
			throw e;
		}
	}

//	/**
//	 * 查询员工对应月份的绩效薪资信息
//	 */
//	@Override
//	public Map<String, Object> getPerformanceInfo(String year, String month, String pNumber) {
//		StringBuffer sql = new StringBuffer();
//		sql.append("SELECT `YEAR` AS `year`, `MONTH` AS `month`, P_NUMBER AS pNumber, ");
//		sql.append("performance_score AS performanceScore, performance_salary AS pSalary ");
//		sql.append("FROM ehr_performance WHERE P_NUMBER = ? AND `MONTH` = ? AND `YEAR` = ?");
//		List<Map<String, Object>> performanceList = null;
//		Map<String, Object> performanceInfo = null;
//		try {
//			performanceList = jdbcTemplate.queryForList(sql.toString(), pNumber, month, year);
//			BASE64Util.Base64DecodeMap(performanceList);
//		} catch (Exception e) {
//			log.error("查询员工绩效薪资信息失败", e);
//			throw e;
//		}
//		if(performanceList != null && performanceList.size() > 0){
//			performanceInfo = performanceList.get(0);
//		}
//		return performanceInfo;
//	}

	/**
	 * 修改绩效薪资信息
	 */
	@Override
	public void updatePerformanceScore(String year, String month, String pNumber, String score, String salary) {
		String sql = "update ehr_performance set performance_score = ? ,performance_salary = ? where P_NUMBER = ? AND `MONTH` = ? AND `YEAR` = ?";
		try {
			String sal = UtilString.isEmpty(salary) ? null : BASE64Util.getDecodeStringTowDecimal(salary);
			jdbcTemplate.update(sql, score, sal, pNumber, month, year);
		} catch (Exception e) {
			log.error("修改绩效分值失败", e);
			throw e;
		}
	}

}
