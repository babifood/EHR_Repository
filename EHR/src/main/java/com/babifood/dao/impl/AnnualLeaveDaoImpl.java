package com.babifood.dao.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.babifood.dao.AnnualLeaveDao;
import com.babifood.utils.IdGen;
@Repository
public class AnnualLeaveDaoImpl implements AnnualLeaveDao {
	@Autowired
	JdbcTemplate jdbctemplate;
	Logger log = LoggerFactory.getLogger(LoginDaoImpl.class);
	//当前年假记录
	@Override
	public List<Map<String, Object>> loadNowAnnualLeave(String npname) {
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
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT p_number,p_name,p_in_date ,b.COMPANYDAYFLAG as p_companydayflag,b.PINDAY as pinday");
		sql.append(" from ehr_person_basic_info a");
		sql.append(" left join ehr_annualleave b");
		sql.append(" ON a.p_number=b.PNUMBER where 1=1");
		sql.append(" ORDER BY p_number ASC");
		List<Map<String, Object>> list = null;
		try {
			list=jdbctemplate.queryForList(sql.toString());
		} catch (Exception e) {
			log.error("查询错误："+e.getMessage());
		}
		return list;
	}
	public int[] SaveAnnualLeave(List<Map<String, Object>> list,int nowYear) {
		String [] sqlAll =new String [3];
		//把当前记录表里需要更新的人员的记录，插入历史记录表
		StringBuffer sql0 = new StringBuffer();
		sql0.append("insert into ehr_annualleave_history SELECT * FROM ehr_annualleave where ANNUALLEAVEID in"
				+ "(select * from(SELECT ANNUALLEAVEID FROM ehr_annualleave where pnumber in(");
		//删除当前记录表里需要更新的人员的记录
		StringBuffer sql1 = new StringBuffer();
		sql1.append("delete from ehr_annualleave where ANNUALLEAVEID in"
				+ "(select * from(SELECT ANNUALLEAVEID FROM ehr_annualleave where pnumber in(");
		//插入当前记录表更新的人员数据
		StringBuffer sql2 = new StringBuffer();
		sql2.append("insert into ehr_annualleave (ANNUALLEAVEID,PNAME,PNUMBER,PINDAY,COMPANYDAYFLAG,NOWYEAR"
				+ ",NANNUALLEAVE,NANNUALLEAVEDEADLINE,LANNUALLEAVE,LANNUALLEAVEDEADLINE,USEDDATA,REMAINDATA,DISABLEDDATA,CREATETIME)");
		sql2.append(" values");
		for (Map<String, Object> map : list) {
			sql0.append("'"+map.get("p_number").toString()+"',");//把当前记录表里需要更新的人员的记录，插入历史记录表
			sql1.append("'"+map.get("p_number").toString()+"',");//删除当前记录表里需要更新的人员的记录
			String annualleaveid = IdGen.uuid();
			sql2.append("("//插入当前记录表更新的人员数据
					+ "'"+annualleaveid+"'"
					+ ",'"+map.get("p_name").toString()+"'"
					+ ",'"+map.get("p_number").toString()+"'"
					+ ",DATE_FORMAT('"+map.get("p_in_date").toString()+"','%Y-%m-%d')"
					+ ","+(int) map.get("p_companydayflag")+""
					+ ","+nowYear+""
					+ ","+(int) map.get("nannualleave")+""
					+ ",DATE_FORMAT('"+(nowYear+1)+"-07-01','%Y-%m-%d')"
					+ ","+(int) map.get("lannualleave")+""
					+ ",DATE_FORMAT('"+(nowYear)+"-07-01','%Y-%m-%d')"
					+ ","+(int) map.get("useddata")+""
					+ ","+(int) map.get("remaindata")+""
					+ ","+(int) map.get("disableddata")+""
					+ ",DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')"
					+ "),");
		}
		StringBuffer coSql=sql0.deleteCharAt(sql0.length()-1);  
		coSql.append(")) a)");
		sqlAll[0]=coSql.toString();
		StringBuffer deSql=sql1.deleteCharAt(sql1.length()-1);  
		deSql.append(")) a)");
		sqlAll[1]=deSql.toString();
		StringBuffer inSql=sql2.deleteCharAt(sql2.length()-1);  
		sqlAll[2]=inSql.toString();
		int[] rows =null;
		try {
			//同时执行三个SQL
			rows = jdbctemplate.batchUpdate(sqlAll);
		} catch (Exception e) {
			log.error("查询错误："+e.getMessage());
		}
		return rows;
	}
}
