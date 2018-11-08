package com.babifood.dao.impl;

import java.util.ArrayList;
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
import com.babifood.utils.UtilString;
@Repository
public class AnnualLeaveDaoImpl extends AuthorityControlDaoImpl implements AnnualLeaveDao {
	@Autowired
	JdbcTemplate jdbctemplate;
	Logger log = LoggerFactory.getLogger(LoginDaoImpl.class);
	//当前年假记录
	@Override
	public List<Map<String, Object>> loadNowAnnualLeave(String npname,String npnumber) {
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_EHR);
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.PNAME as pname,a.PNUMBER as pnumber,a.PINDAY as pinday,a.NOWYEAR as nowyear,");
		sql.append("a.NANNUALLEAVE as nannualleave,a.NANNUALLEAVEDEADLINE as nannualleavedeadline,");
		sql.append("a.LANNUALLEAVE as lannualleave,a.LANNUALLEAVEDEADLINE as lannualleavedeadline,");
		sql.append("a.USEDDATA as useddata,a.REMAINDATA as remaindata,a.DISABLEDDATA as disableddata, ");
		sql.append("c.DEPT_NAME as companyName, d.DEPT_NAME as organizationName, e.DEPT_NAME as deptName,");
		sql.append("f.DEPT_NAME as officeName, g.DEPT_NAME as groupName");
		sql.append(" from ehr_annualleave a INNER JOIN ehr_person_basic_info b ON a.PNUMBER = b.p_number ");
		sql.append(" LEFT JOIN ehr_dept c on b.P_COMPANY_ID = c.DEPT_CODE ");
		sql.append(" LEFT JOIN ehr_dept d on b.P_ORGANIZATION_ID = d.DEPT_CODE ");
		sql.append(" LEFT JOIN ehr_dept e on b.P_DEPARTMENT_ID = e.DEPT_CODE ");
		sql.append(" LEFT JOIN ehr_dept f on b.P_SECTION_OFFICE_ID = f.DEPT_CODE ");
		sql.append(" LEFT JOIN ehr_dept g on b.P_GROUP_ID = g.DEPT_CODE where 1=1 ");
		if(!UtilString.isEmpty(npname)){
			sql.append(" and PNAME like '%"+npname+"%'");
		}
		if(!UtilString.isEmpty(npnumber)){
			sql.append(" and PNUMBER like '%"+npnumber+"%'");
		}
		sql = super.jointDataAuthoritySql("b.P_COMPANY_ID", "b.P_ORGANIZATION_ID", sql);
		sql.append(" group by a.NOWYEAR,a.PNUMBER ");
		sql.append(" ORDER BY a.NOWYEAR desc");
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
		sql.append("SELECT p_id,p_number,p_name,p_in_date ,p_company_age as p_companydayflag,p_company_id as companyCode,");
		sql.append("p_department_id as deptCode, p_organization_id as organizationCode,p_organization AS organizationName, ");
		sql.append("p_section_office_id as officeCode,p_group_id as groupCode, p_company_name AS companyName ");
		sql.append(" from ehr_person_basic_info where p_out_date is null or p_out_date = '' and p_oa_and_ehr = 'OA'");
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
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_EHR);
		String sql = "update ehr_person_basic_info set p_company_age = ? where p_number = ?";
		try {
			jdbctemplate.update(sql, companyAge, pNumber);
		} catch (Exception e) {
			log.error("修改员工司龄失败："+e.getMessage());
		}
	}
	@Override
	public List<Map<String, Object>> findTotalBingjia(String year) {
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_OA);
		String sql = "SELECT WorkNum AS pNumber, SUM(bingJia) AS bingJia from ehr_checking_result  WHERE `Year` = ? GROUP BY WorkNum";
		List<Map<String, Object>> totalBingjia = null;
		try {
			totalBingjia = jdbctemplate.queryForList(sql, year);
		} catch (Exception e) {
			log.error("查询年度病假数失败："+e.getMessage());
		}
		return totalBingjia;
	}
	@Override
	public void pushAnnualToOA(List<String> ids, List<Map<String, Object>> list, String year) {
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_OA);
		StringBuffer deleteSql = new StringBuffer();
		year = (Integer.valueOf(year) + 2) + "";
		deleteSql.append("delete from formmain_0294 where field0003 = '" + year + "' and field0007 in (");
		for(int i = 0; i < ids.size() ; i++){
			if(i == ids.size() - 1){
				deleteSql.append("'" + ids.get(i) + "')");
			} else {
				deleteSql.append("'" + ids.get(i) + "',");
			}
		}
		List<String> sqlList = new ArrayList<String>();
		for(int i = 0; i < list.size(); i++){
			StringBuffer pushSsql = new StringBuffer();
			pushSsql.append("insert into formmain_0294 (ID, STATE, START_MEMBER_ID, START_DATE, APPROVE_MEMBER_ID, ");
			pushSsql.append("APPROVE_DATE, FINISHEDFLAG, RATIFYFLAG, RATIFY_MEMBER_ID, RATIFY_DATE, SORT, MODIFY_MEMBER_ID, ");
			pushSsql.append("MODIFY_DATE, FIELD0002, FIELD0003, FIELD0004, FIELD0006, FIELD0007, FIELD0008, FIELD0009, ");
			pushSsql.append("FIELD0010, FIELD0001, FIELD0005, FIELD0011, FIELD0012, FIELD0013) values ");
			Map<String, Object> map = list.get(i);
			String deptName = UtilString.isEmpty(map.get("organizationName")+"") ? map.get("companyName") + "":map.get("organizationName") + "";
			pushSsql.append("('"+map.get("OAID")+"', '1', '', '', '0', '', '0', '0', '0', null, '0', '', '', '"+map.get("p_name"));
			pushSsql.append("', '"+year+"', '', '"+deptName+"', '"+map.get("p_number")+"', to_timestamp('"+(Integer.valueOf(year) - 1)+"-07-01','yyyy-MM-dd'), to_timestamp('"+year+"-07-01','yyyy-MM-dd'), '', '");
			pushSsql.append(map.get("useddata")+"', '"+map.get("lannualleave")+"', '"+map.get("remaindata")+"', '"+map.get("disableddata")+"', '"+map.get("nannualleave")+"')");
			sqlList.add(pushSsql.toString());
		}
		try {
			jdbctemplate.update(deleteSql.toString());
			jdbctemplate.batchUpdate(sqlList.toArray(new String[list.size()]));
		} catch (Exception e) {
			log.error("OA年假推送失败："+e.getMessage());
		}
	}
}
