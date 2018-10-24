package com.babifood.dao.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.babifood.dao.AnnualLeaveDao;
import com.babifood.utils.CustomerContextHolder;
import com.babifood.utils.UtilDateTime;
@Repository
public class AnnualLeaveDaoImpl implements AnnualLeaveDao {
	@Autowired
	JdbcTemplate jdbctemplate;
	Logger log = LoggerFactory.getLogger(LoginDaoImpl.class);
	//当前年假记录
	@Override
	public List<Map<String, Object>> loadNowAnnualLeave(String npname) {
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_EHR);
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT PNAME as pname,PNUMBER as pnumber,PINDAY as pinday,NOWYEAR as nowyear"
				+ ",NANNUALLEAVE as nannualleave,NANNUALLEAVEDEADLINE as nannualleavedeadline"
				+ ",LANNUALLEAVE as lannualleave,LANNUALLEAVEDEADLINE as lannualleavedeadline"
				+ ",USEDDATA as useddata,REMAINDATA as remaindata,DISABLEDDATA as disableddata ");
		sql.append(" from ehr_annualleave where 1=1");
		if(npname!=null&&!npname.equals("")){
			sql.append(" and PNAME like '%"+npname+"%'");
		}
		sql.append(" ORDER BY PNUMBER ASC");
		List<Map<String, Object>> list = null;
		try {
			list=jdbctemplate.queryForList(sql.toString());
		} catch (Exception e) {
			log.error("查询错误："+e.getMessage());
		}
		return list;
	}
	//历史年假记录
	@Override
	public List<Map<String, Object>> loadHistoryAnnualLeave(String lpname) {
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_EHR);
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT PNAME as pname,PNUMBER as pnumber,PINDAY as pinday,NOWYEAR as nowyear"
				+ ",NANNUALLEAVE as nannualleave,NANNUALLEAVEDEADLINE as nannualleavedeadline"
				+ ",LANNUALLEAVE as lannualleave,LANNUALLEAVEDEADLINE as lannualleavedeadline"
				+ ",USEDDATA as useddata,REMAINDATA as remaindata,DISABLEDDATA as disableddata ");
		sql.append(" from ehr_annualleave_history where 1=1");
		if(lpname!=null&&!lpname.equals("")){
			sql.append(" and PNAME like '%"+lpname+"%'");
		}
		sql.append(" ORDER BY PNUMBER ASC");
		List<Map<String, Object>> list = null;
		try {
			list=jdbctemplate.queryForList(sql.toString());
		} catch (Exception e) {
			log.error("查询错误："+e.getMessage());
		}
		return list;
	}
	//取员工入职日期等信息
	public List<Map<String, Object>> GetHireDate() {
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_EHR);
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT p_number,p_name,p_in_date ,p_company_age as p_companydayflag,p_company_id as companyCode,");
		sql.append("p_department_id as deptCode, p_organization_id as organizationCode, ");
		sql.append("p_section_office_id as officeCode,p_group_id as groupCode ");
		sql.append(" from ehr_person_basic_info where p_out_date is null or p_out_date = ''");
		List<Map<String, Object>> list = null;
		try {
			list=jdbctemplate.queryForList(sql.toString());
		} catch (Exception e) {
			log.error("查询错误："+e.getMessage());
		}
		return list;
	}
	public int[] SaveAnnualLeave(List<Map<String, Object>> list) {
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_EHR);
		StringBuffer sql = new StringBuffer();
		sql.append("REPLACE INTO `ehr_annualleave` (`PNAME`, `PNUMBER`, `PINDAY`, `COMPANYDAYFLAG`, ");
		sql.append("`NOWYEAR`, `NANNUALLEAVE`, `NANNUALLEAVEDEADLINE`, `LANNUALLEAVE`, `LANNUALLEAVEDEADLINE`, ");
		sql.append("`USEDDATA`, `REMAINDATA`, `DISABLEDDATA`, `CREATETIME`) VALUES");
		Map<String, Object> map = null;
		for(int i = 0; i < list.size() ; i++){
			map = list.get(i);
			sql.append("('" + map.get("p_name") + "','" + map.get("p_number") + "','" + map.get("p_in_date") + "'," + map.get("p_companydayflag"));
			sql.append(",'" + map.get("year") + "'," + map.get("nannualleave") + ",'" + map.get("nannualleavedeadline") + "'," + map.get("lannualleave"));
			sql.append(",'" + map.get("lannualleavedeadline") + "'," + map.get("useddata") + "," + map.get("remaindata") + "," + map.get("disableddata"));
			sql.append(",'" + UtilDateTime.getCurrentTime("yyyy-MM-dd HH:mm:ss") + "'");
			if(i == list.size() - 1){
				sql.append(")");
			} else {
				sql.append("),");
			}
		}
		int[] rows =null;
		try {
			//同时执行三个SQL
			jdbctemplate.update(sql.toString());
		} catch (Exception e) {
			log.error("查询错误："+e.getMessage());
		}
		return rows;
	}
	@Override
	public List<Map<String, Object>> findOANianjia(String start) {
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_OA);
		StringBuffer sql = new StringBuffer();
		sql.append("select year,month,m.code as worknum,createtime,billdate,billnum,billtype,");
		sql.append("timelength,begintime,endtime,validflag,bizflag1 ");
		sql.append("from a8xclockedbizdata a inner join org_member m on a.memberid=m.id ");
		sql.append("where bizflag1 = '年假' and  to_char(begintime,'yyyy-mm-dd') >=? ");
		List<Map<String, Object>> nianjiaList = null;
		try {
			nianjiaList = jdbctemplate.queryForList(sql.toString(), start);
		} catch (Exception e) {
			log.error("查询错误："+e.getMessage());
		}
		return nianjiaList;
	}
	@Override
	public List<Map<String, Object>> findCurrentNianjiaLit(String year) {
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_EHR);
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT PNAME as pname,PNUMBER as pnumber,PINDAY as pinday,NOWYEAR as nowyear,");
		sql.append("NANNUALLEAVE as nannualleave,NANNUALLEAVEDEADLINE as nannualleavedeadline,");
		sql.append("LANNUALLEAVE as lannualleave,LANNUALLEAVEDEADLINE as lannualleavedeadline,");
		sql.append("USEDDATA as useddata,REMAINDATA as remaindata,DISABLEDDATA as c ");
		sql.append(" from ehr_annualleave where NOWYEAR = ?");
		List<Map<String, Object>> nianjiaList = null;
		try {
			nianjiaList = jdbctemplate.queryForList(sql.toString(), year);
		} catch (Exception e) {
			log.error("查询错误："+e.getMessage());
		}
		return nianjiaList;
	}
	@Override
	public void updateEmpCompanyAge(String pNumber, Integer companyAge) {
		String sql = "update ehr_person_basic_info set p_company_age = ? where p_number = ?";
		try {
			jdbctemplate.update(sql, companyAge, pNumber);
		} catch (Exception e) {
			log.error("修改员工司龄失败："+e.getMessage());
		}
	}
	@Override
	public List<Map<String, Object>> findTotalBingjia(String year) {
		String sql = "SELECT WorkNum AS pNumber, SUM(bingJia) AS bingJia from ehr_checking_result  WHERE `Year` = ? GROUP BY WorkNum";
		List<Map<String, Object>> totalBingjia = null;
		try {
			totalBingjia = jdbctemplate.queryForList(sql, year);
		} catch (Exception e) {
			log.error("查询年度病假数失败："+e.getMessage());
		}
		return totalBingjia;
	}
}
