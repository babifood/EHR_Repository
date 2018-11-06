package com.babifood.clocked.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.babifood.clocked.dao.PushClockedDataDao;
import com.babifood.clocked.entrty.PushOaDataEntrty;
import com.babifood.dao.impl.AuthorityControlDaoImpl;
import com.babifood.utils.CustomerContextHolder;
@Repository
public class PushClockedDataDaoImpl extends AuthorityControlDaoImpl implements PushClockedDataDao {
	@Autowired
	JdbcTemplate jdbctemplate;
	@Override
	public List<Map<String, Object>> loadClockingInData(int year, int month) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append("p.p_id AS UserName,ck.WorkNum,dept.OID AS deptCode,org.OID AS orgerCode,");
		sql.append("ck.checkingDate,ck.`Week`,ck.nianJia,ck.Queqin,ck.hunJia,ck.Qingjia,");
		sql.append("ck.chiDao,ck.zaoTui,ck.kuangGongCiShu,ck.SangJia,ck.Canbu,ck.bingJia,");
		sql.append("ck.Yidong,ck.checkingBeginTime,ck.checkingEndTime,ck.Qita,ck.Jiaban,");
		sql.append("ck.beginTime,ck.endTime,ck.Chuchai,ck.shiJia,ck.`Year`,ck.`Month`,");
		sql.append("ck.Clockflag,ck.originalCheckingLength,ck.EventBeginTime,ck.actualWorkLength,");
		sql.append("ck.EventEndTime,ck.peixunJia,ck.chanJia,ck.PeiChanJia,ck.tiaoXiu,");
		sql.append("ck.CheckingType,ck.standardWorkLength ");
		sql.append("FROM ehr_checking_result ck ");
		sql.append("INNER JOIN ehr_dept dept ON ck.DeptCode = dept.DEPT_CODE ");
		sql.append("INNER JOIN ehr_dept org ON ck.OrganCode = org.DEPT_CODE ");
		sql.append("INNER JOIN ehr_person_basic_info p ON ck.WorkNum = p.p_number ");
		sql.append("WHERE 1=1  and ck.`Year` =? and ck.`Month` =? and p.p_oa_and_ehr = 'OA'");
		StringBuffer returnSQL = super.jointDataAuthoritySql("ck.CompanyCode","ck.OrganCode",sql);
		Object[] params=new Object[2];
		params[0]= year;
		params[1]= month;
		return jdbctemplate.queryForList(returnSQL.toString(),params);
	}
	@Override
	public void deleteOAClockingInData(List<PushOaDataEntrty> list,int year, int month) throws Exception{
		// TODO Auto-generated method stub
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_OA);
		String[] sql = new String[list.size()];
		for(int i = 0;i<list.size();i++){
			sql[i]="delete from formmain_0382 where field0002 ='"+list.get(i).getWorkNum()+"' and field0029 = '"+year+"' and field0030='"+month+"'";
		}
		jdbctemplate.batchUpdate(sql);
	}

}
