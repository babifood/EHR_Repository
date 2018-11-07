package com.babifood.clocked.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.babifood.clocked.dao.ClockedResultBaseDao;
import com.babifood.clocked.entrty.ClockedResultBases;
import com.babifood.dao.impl.AuthorityControlDaoImpl;
import com.babifood.utils.CustomerContextHolder;
@Repository
public class ClockedResultBaseDaoImpl extends AuthorityControlDaoImpl implements ClockedResultBaseDao {
	public static final Logger log = Logger.getLogger(ClockedResultBaseDaoImpl.class);
	@Autowired
	JdbcTemplate jdbctemplate;
	@Override
	public int[] saveClockedResultBase(List<ClockedResultBases> saveDataList,int year,int month) throws Exception{
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_EHR);
		// TODO Auto-generated method stub
		StringBuffer sql_delete = new StringBuffer("delete from ehr_checking_result where year=? and month=?");//人员档案信息
		StringBuffer sql = new StringBuffer();
		sql.append("insert into  ehr_checking_result (year,month,worknum,username, ");
		sql.append("companycode,company,organcode,organ,deptcode, ");
		sql.append("dept,officecode,office,groupcode,groupname, ");
		sql.append("postcode,post,checkingtype,paibantype,checkingdate, ");
		sql.append("week,begintime,endtime,standardworklength, ");
		sql.append("checkingbegintime,checkingendtime,originalcheckinglength, ");
		sql.append("actualworklength,chidao,zaotui,kuanggongcishu,kuanggong,nianjia, ");
		sql.append("tiaoxiu,shijia,bingjia,peixunjia,hunjia, ");
		sql.append("chanjia,peichanjia,sangjia,qita,queqin, ");
		sql.append("qingjia,yidong,jiaban,chuchai,canbu, ");
		sql.append("eventbegintime,eventendtime,clockflag,inoutjob) ");
		sql.append(" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		final String strSql = sql.toString();
		List<Object[]> paramsList = new ArrayList<>();
		for(int i=0;i<saveDataList.size();i++){
			paramsList.add(new Object[]{
					saveDataList.get(i).getYear(),saveDataList.get(i).getMonth(),
					saveDataList.get(i).getWorkNum(),saveDataList.get(i).getUserName(),
					saveDataList.get(i).getCompanyCode(),saveDataList.get(i).getCompany(),
					saveDataList.get(i).getOrganCode(),saveDataList.get(i).getOrgan(),
					saveDataList.get(i).getDeptCode(),saveDataList.get(i).getDept(),
					saveDataList.get(i).getOfficeCode(),saveDataList.get(i).getOffice(),
					saveDataList.get(i).getGroupCode(),saveDataList.get(i).getGroupName(),
					saveDataList.get(i).getPostCode(),saveDataList.get(i).getPost(),
					saveDataList.get(i).getCheckingType(),
					saveDataList.get(i).getPaiBanType(),saveDataList.get(i).getCheckingDate(),
					saveDataList.get(i).getWeek(),saveDataList.get(i).getBeginTime(),
					saveDataList.get(i).getEndTime(),saveDataList.get(i).getStandWorkLength(),
					saveDataList.get(i).getCheckingBeginTime(),saveDataList.get(i).getCheckingEndTime(),
					saveDataList.get(i).getOriginalCheckingLength(),saveDataList.get(i).getActualWorkLength(),
					saveDataList.get(i).getChiDao(),saveDataList.get(i).getZaoTui(),
					saveDataList.get(i).getKuangGongCiShu(),
					saveDataList.get(i).getKuangGong(),saveDataList.get(i).getNianJia(),
					saveDataList.get(i).getTiaoXiu(),saveDataList.get(i).getShiJia(),
					saveDataList.get(i).getBingJia(),saveDataList.get(i).getPeiXunJia(),
					saveDataList.get(i).getHunJia(),saveDataList.get(i).getChanJia(),
					saveDataList.get(i).getPeiChanJia(),saveDataList.get(i).getSangJia(),
					saveDataList.get(i).getOtherQingJia(),saveDataList.get(i).getQueQin(),
					saveDataList.get(i).getQingJia(),saveDataList.get(i).getYiDong(),
					saveDataList.get(i).getJiaBan(),saveDataList.get(i).getChuCha(),
					saveDataList.get(i).getCanBu(),saveDataList.get(i).getEventBeginTime(),
					saveDataList.get(i).getEventEndTime(),saveDataList.get(i).getClockFlag(),
					saveDataList.get(i).getInOutJob()
					});
		}
		Object[] params=new Object[2];
		params[0]=year;
		params[1]=month;
		int[] rwso = null;
		StringBuffer returnSQL = super.jointDataAuthoritySql("companycode","organcode",sql_delete);
		jdbctemplate.update(returnSQL.toString(), params);
		rwso = jdbctemplate.batchUpdate(strSql,paramsList);
		return rwso;
	}
	@Override
	public List<Map<String, Object>> loadClockedResultData(int year,int month,String workNum,String periodEndDate) throws Exception {
		// TODO Auto-generated method stub
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_EHR);
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append("Year,Month,WorkNum,UserName,");
		sql.append("CompanyCode,Company,OrganCode,Organ,DeptCode, ");
		sql.append("Dept,OfficeCode,Office,GroupCode,GroupName, ");
		sql.append("PostCode,Post,CheckingType,PaiBanType,checkingDate, ");
		sql.append("Week,beginTime,endTime,standardWorkLength, ");
		sql.append("checkingBeginTime,checkingEndTime,originalCheckingLength, ");
		sql.append("actualWorkLength,chiDao,zaoTui,kuangGongCiShu,kuangGong,nianJia, ");
		sql.append("tiaoXiu,shiJia,bingJia,peixunJia,hunJia, ");
		sql.append("chanJia,PeiChanJia,SangJia,Qita,Queqin, ");
		sql.append("Qingjia,Yidong,Jiaban,Chuchai,Canbu, ");
		sql.append("EventBeginTime,EventEndTime,Clockflag ");
		sql.append("from ehr_checking_result ");
		sql.append("where Year=? and Month = ? and WorkNum=? and checkingDate<=?");
		sql.append("order by WorkNum,checkingDate");
		Object[] params=new Object[4];
		params[0]=year;
		params[1]=month;
		params[2]=workNum;
		params[3]=periodEndDate;
		return jdbctemplate.queryForList(sql.toString(), params);
		
	}
	@Override
	public List<Map<String, Object>> loadClockedResultDataList(int year,int month) throws Exception {
		// TODO Auto-generated method stub
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_EHR);
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append("Year,Month,WorkNum,UserName,");
		sql.append("CompanyCode,Company,OrganCode,Organ,DeptCode, ");
		sql.append("Dept,OfficeCode,Office,GroupCode,GroupName, ");
		sql.append("PostCode,Post,CheckingType,PaiBanType,checkingDate, ");
		sql.append("Week,beginTime,endTime,standardWorkLength, ");
		sql.append("checkingBeginTime,checkingEndTime,originalCheckingLength, ");
		sql.append("actualWorkLength,chiDao,zaoTui,kuangGongCiShu,kuangGong,nianJia, ");
		sql.append("tiaoXiu,shiJia,bingJia,peixunJia,hunJia, ");
		sql.append("chanJia,PeiChanJia,SangJia,Qita,Queqin, ");
		sql.append("Qingjia,Yidong,Jiaban,Chuchai,Canbu, ");
		sql.append("EventBeginTime,EventEndTime,Clockflag,inoutjob ");
		sql.append("from ehr_checking_result ");
		sql.append("where Year=? and Month = ? ");
		StringBuffer returnSQL = super.jointDataAuthoritySql("CompanyCode","OrganCode",sql);
		returnSQL.append("order by WorkNum,checkingDate");
		Object[] params=new Object[2];
		params[0]=year;
		params[1]=month;
		return jdbctemplate.queryForList(returnSQL.toString(), params);
	}
	@Override
	public int[] updateClockedResultBase(List<ClockedResultBases> saveDataList) throws Exception {
		// TODO Auto-generated method stub
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_EHR);
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("update ehr_checking_result set ");
		sql.append("checkingbegintime =?,checkingendtime=?,originalcheckinglength=?, ");
		sql.append("actualworklength=?,chidao=?,zaotui=?,kuanggongcishu=?,kuanggong=?,nianjia=?, ");
		sql.append("tiaoxiu=?,shijia=?,bingjia=?,peixunjia=?,hunjia=?, ");
		sql.append("chanjia=?,peichanjia=?,sangjia=?,qita=?,queqin=?, ");
		sql.append("qingjia=?,yidong=?,jiaban=?,chuchai=?,canbu=?, ");
		sql.append("eventbegintime=?,eventendtime=? ");
		sql.append("where WorkNum=? and checkingDate=?");
		final String strSql = sql.toString();
		List<Object[]> paramsList = new ArrayList<>();
		int size = saveDataList==null?0:saveDataList.size();
		for(int i=0;i<size;i++){
			paramsList.add(new Object[]{
					saveDataList.get(i).getCheckingBeginTime(),saveDataList.get(i).getCheckingEndTime(),
					saveDataList.get(i).getOriginalCheckingLength(),saveDataList.get(i).getActualWorkLength(),
					saveDataList.get(i).getChiDao(),saveDataList.get(i).getZaoTui(),
					saveDataList.get(i).getKuangGongCiShu(),
					saveDataList.get(i).getKuangGong(),saveDataList.get(i).getNianJia(),
					saveDataList.get(i).getTiaoXiu(),saveDataList.get(i).getShiJia(),
					saveDataList.get(i).getBingJia(),saveDataList.get(i).getPeiXunJia(),
					saveDataList.get(i).getHunJia(),saveDataList.get(i).getChanJia(),
					saveDataList.get(i).getPeiChanJia(),saveDataList.get(i).getSangJia(),
					saveDataList.get(i).getOtherQingJia(),saveDataList.get(i).getQueQin(),
					saveDataList.get(i).getQingJia(),saveDataList.get(i).getYiDong(),
					saveDataList.get(i).getJiaBan(),saveDataList.get(i).getChuCha(),
					saveDataList.get(i).getCanBu(),saveDataList.get(i).getEventBeginTime(),
					saveDataList.get(i).getEventEndTime(),saveDataList.get(i).getWorkNum(),
					saveDataList.get(i).getCheckingDate()
					});
		}
		return jdbctemplate.batchUpdate(strSql, paramsList);
	}
	/**
	 * 查询考勤结果汇总数据
	 */
	@Override
	public List<Map<String, Object>> loadSumClockedResultData(String workNum, String userName) throws Exception {
		// TODO Auto-generated method stub
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_EHR);
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append("Year,Month,WorkNum,UserName,CompanyCode,Company,OrganCode,");
		sql.append("Organ,DeptCode,Dept,OfficeCode,Office,GroupCode,GroupName,");
		sql.append("PostCode,Post,CheckingType,PaiBanType,SUM(standardWorkLength) as standardWorkLength,");
		sql.append("SUM(originalCheckingLength) as originalCheckingLength,SUM(actualWorkLength) as actualWorkLength,");
		sql.append("SUM(chiDao) as chiDao,SUM(zaoTui) as zaoTui,SUM(kuangGongCiShu) as kuangGongCiShu,SUM(kuangGong) as kuangGong,");
		sql.append("SUM(Queqin) as Queqin,SUM(Qingjia) as Qingjia,SUM(nianJia) as nianJia,");
		sql.append("SUM(tiaoXiu) as tiaoXiu,SUM(shiJia) as shiJia,SUM(bingJia) as bingJia,");
		sql.append("SUM(peixunJia) as peixunJia,SUM(hunJia) as hunJia,SUM(chanJia) as chanJia,");
		sql.append("SUM(PeiChanJia) as PeiChanJia,SUM(SangJia) as SangJia,SUM(Yidong) as Yidong,");
		sql.append("SUM(Jiaban) as Jiaban,SUM(Chuchai) as Chuchai,SUM(Canbu) as Canbu ");
		sql.append("from ehr_checking_result where 1=1");
		if(workNum!=null&&!workNum.equals("")){
			sql.append(" and WorkNum = '"+workNum+"'");
		}
		if(userName!=null&&!userName.equals("")){
			sql.append(" and UserName like '%"+userName+"%'");
		}
		StringBuffer returnSQL = super.jointDataAuthoritySql("CompanyCode","OrganCode",sql);
		returnSQL.append(" GROUP BY Year,Month,WorkNum,UserName,CompanyCode,Company,OrganCode,Organ,");
		returnSQL.append("DeptCode,Dept,OfficeCode,Office,GroupCode,GroupName,PostCode,Post,CheckingType,PaiBanType");
		returnSQL.append(" ORDER BY Year,Month desc");
		return jdbctemplate.queryForList(returnSQL.toString());
	}
}
