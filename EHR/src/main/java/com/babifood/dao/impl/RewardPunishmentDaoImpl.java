package com.babifood.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.babifood.dao.RewardPunishmentDao;
import com.babifood.entity.RewardPunishmentEntity;
@Repository
public class RewardPunishmentDaoImpl implements RewardPunishmentDao {
	@Autowired
	JdbcTemplate jdbctemplate;
	Logger log = LoggerFactory.getLogger(LoginDaoImpl.class);
	//奖惩项目方法
	@Override
	public List<Map<String, Object>> loadRAPItemAll(String category_id,String item_name) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT RAPI_ID as item_id,RAPI_NAME as item_name,RAPI_CATEGORY_ID as category_id,RAPI_CATEGORY_NAME as category_name ");
		sql.append(" from ehr_rewardandpunishment_item where 1=1");
		if(category_id!=null&&!category_id.equals("")){
			sql.append(" and RAPI_CATEGORY_ID = '"+category_id+"'");
		}
		if(item_name!=null&&!item_name.equals("")){
			sql.append(" and RAPI_NAME like '%"+item_name+"%'");
		}
		sql.append(" ORDER BY RAPI_CATEGORY_ID ASC");
		System.out.println(sql);
		List<Map<String, Object>> list = null;
		try {
			list=jdbctemplate.queryForList(sql.toString());
		} catch (Exception e) {
			// TODO: handle exception
			log.error("查询错误："+e.getMessage());
		}
		return list;
	}
	@Override
	public Integer saveRAPItem(String item_id,String item_name,String category_id,String category_name) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("insert into ehr_rewardandpunishment_item (RAPI_ID,RAPI_NAME,RAPI_CATEGORY_ID,RAPI_CATEGORY_NAME) ");
		sql.append(" values(?,?,?,?)");
		Object[] params=new Object[4];
		params[0]=item_id;
		params[1]=item_name;
		params[2]=category_id;
		params[3]=category_name;
		int rows =-1;
		System.out.println("saveRAPItem"+sql);
		try {
			rows = jdbctemplate.update(sql.toString(), params);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("查询错误："+e.getMessage());
		}
		return rows;
	}
	@Override
	public Integer eidtRAPItem(String item_id,String item_name,String category_id,String category_name) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("update ehr_rewardandpunishment_item set RAPI_NAME=?,RAPI_CATEGORY_ID=?,RAPI_CATEGORY_NAME=? where RAPI_ID=?");
		Object[] params=new Object[4];
		params[0]=item_name;
		params[1]=category_id;
		params[2]=category_name;
		params[3]=item_id;
		int rows =-1;
		int count;
		int CI=Integer.parseInt(category_id);
		int CI1=Integer.parseInt(category_id);
		try {
			StringBuffer sql_selectrewardandpunishment = new StringBuffer();
			sql_selectrewardandpunishment.append("SELECT COUNT(RAP_ITEM_ID) from ehr_rewardandpunishment WHERE RAP_ITEM_ID='"+item_id+"'");
			count=jdbctemplate.queryForInt(sql_selectrewardandpunishment.toString());
			//rows:0存在已经使用该奖惩项目的奖惩记录(不允许修改奖惩类别)；大于1成功；-1失败
			if(count>0){
				StringBuffer sql_selectrewardandpunishment_category_id = new StringBuffer();
				sql_selectrewardandpunishment_category_id.append("SELECT RAPI_CATEGORY_ID from ehr_rewardandpunishment_item WHERE RAPI_ID="+item_id);
				CI1=jdbctemplate.queryForInt(sql_selectrewardandpunishment_category_id.toString());
				if(CI!=CI1){
					rows = 0;
				}else{
					try {
						rows = jdbctemplate.update(sql.toString(), params);
					} catch (Exception e) {
						// TODO: handle exception
						log.error("修改错误："+e.getMessage());
					}
				}
			}else{
				try {
					rows = jdbctemplate.update(sql.toString(), params);
				} catch (Exception e) {
					// TODO: handle exception
					log.error("修改错误："+e.getMessage());
				}
			}			
		} catch (Exception e) {
			// TODO: handle exception
			log.error("查询错误："+e.getMessage());
		}
		return rows;
	}
	@Transactional("txManager")
	@Override
	public Integer removeRAPItem(String item_id) {
		// TODO Auto-generated method stub
		int state = 1;
		int count;
		try {
			StringBuffer sql_selectrewardandpunishment = new StringBuffer();
			sql_selectrewardandpunishment.append("SELECT COUNT(RAP_ITEM_ID) from ehr_rewardandpunishment WHERE RAP_ITEM_ID='"+item_id+"'");
			count=jdbctemplate.queryForInt(sql_selectrewardandpunishment.toString());
			//state:0存在已经使用该奖惩项目的奖惩记录1成功-1失败
			if(count>0){
		        state = 0;
			}else{
				try {
					StringBuffer sql_joblevel = new StringBuffer();
					sql_joblevel.append("delete from ehr_rewardandpunishment_item where RAPI_ID=?");
					jdbctemplate.update(sql_joblevel.toString(),item_id);
				} catch (Exception e) {
					// TODO: handle exception
					state = -1;
					log.error("删除错误："+e.getMessage());
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("查询错误："+e.getMessage());
		}
		return state;
	}
	@Override
	public List<Map<String, Object>> loadComboboxRAPItemData(String category_id) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("select RAPI_ID as id,RAPI_NAME as text");
		sql.append(" from ehr_rewardandpunishment_item");
		if(category_id!=null&&!category_id.equals("")){
			sql.append(" where RAPI_CATEGORY_ID = '"+category_id+"'");
		}
		List<Map<String, Object>> list = null;
		try {
			list=jdbctemplate.queryForList(sql.toString());
		} catch (Exception e) {
			// TODO: handle exception
			log.error("查询错误："+e.getMessage());
		}
		return list;
	}
	//奖惩记录
	@Override
	public List<Map<String, Object>> loadRewardPunishment(String rap_category, String rap_item) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("select u.RAP_ID as rap_id,u.RAPI_CATEGORY_ID as rap_category_id,r.RAPI_CATEGORY_NAME as rap_category_name,u.RAP_P_ID as rap_p_id,u.RAP_P_NAME as rap_p"
				+ ",u.RAP_ITEM_ID as rap_item_id,u.RAP_DATE as rap_date,u.RAP_REASON as rap_reason"
				+ ",u.RAP_MONEY as rap_money,u.RAP_PROPOSER_ID as rap_proposer_id,u.RAP_PROPOSER_NAME as rap_proposer"
				+ ",u.RAP_DESC as rap_desc,r.RAPI_ID,r.RAPI_NAME as rap_item_name");
		sql.append(" from ehr_rewardandpunishment u inner join ehr_rewardandpunishment_item r on u.RAP_ITEM_ID = r.RAPI_ID where 1=1");
		if(rap_category!=null&&!rap_category.equals("")){
			sql.append(" and u.RAPI_CATEGORY_ID like '%"+rap_category+"%'");
		}
		if(rap_item!=null&&!rap_item.equals("")){
			sql.append(" and r.RAPI_NAME like '%"+rap_item+"%'");
		}
		sql.append(" ORDER BY rap_id ASC");
		List<Map<String, Object>> list = null;
		try {
			list=jdbctemplate.queryForList(sql.toString());
		} catch (Exception e) {
			// TODO: handle exception
			log.error("查询错误："+e.getMessage());
		}
		return list;
	}
	@Override
	public int[] saveRewardPunishment(RewardPunishmentEntity rewardpunishmentEntity) {
		// TODO Auto-generated method stub
		String statu="0";
		int[] rows = null;
		/*int rows =-1;
		StringBuffer sql = new StringBuffer();
		sql.append("insert into ehr_rewardandpunishment (RAPI_CATEGORY_ID,RAP_P_ID,RAP_ITEM_ID,RAP_DATE,RAP_REASON,RAP_MONEY,RAP_PROPOSER_ID,RAP_DESC,RAP_STATE,RAP_P_NAME,RAP_PROPOSER_NAME) ");
		sql.append(" values(?,?,?,?,?,?,?,?,?,?,?)");*/
		String sql = "insert into ehr_rewardandpunishment (RAPI_CATEGORY_ID,RAP_P_ID,RAP_ITEM_ID,RAP_DATE"
				+ ",RAP_REASON,RAP_MONEY,RAP_PROPOSER_ID,RAP_DESC,RAP_STATE,RAP_P_NAME,RAP_PROPOSER_NAME)"
				+ " values(?,?,?,?,?,?,?,?,?,?,?)";
		String pid=rewardpunishmentEntity.getrap_p_id();
		String[] aspid = pid.split(",");
		String pnam=rewardpunishmentEntity.getrap_p();
		String[] aspnam = pnam.split(",");
		
		List<Object[]> params = new ArrayList<>();
		for(int i=0;i<aspid.length;i++){	
			params.add(new Object[]{
						rewardpunishmentEntity.getrap_category(),
						aspid[i],//奖惩人员ID
						rewardpunishmentEntity.getrap_item(),
						rewardpunishmentEntity.getrap_date(),
						rewardpunishmentEntity.getrap_reason(),
						rewardpunishmentEntity.getrap_money(),
						rewardpunishmentEntity.getrap_proposer_id(),//提议ID
						rewardpunishmentEntity.getrap_desc(),
						statu,
						aspnam[i],//奖惩人员姓名
						rewardpunishmentEntity.getrap_proposer()//提议人姓名
						});
		}
		
		try {
			rows = jdbctemplate.batchUpdate(sql, params);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("查询错误："+e.getMessage());
		}
		
		
		/*Object[] params=new Object[11];
		for(int i=0;i<aspid.length;i++){			
			params[0]=rewardpunishmentEntity.getrap_category();
			params[1]=aspid[i];//奖惩人员ID
			params[2]=rewardpunishmentEntity.getrap_item();
			params[3]=rewardpunishmentEntity.getrap_date();
			params[4]=rewardpunishmentEntity.getrap_reason();
			params[5]=rewardpunishmentEntity.getrap_money();
			params[6]=rewardpunishmentEntity.getrap_proposer_id();//提议ID
			params[7]=rewardpunishmentEntity.getrap_desc();
			params[8]=statu;
			params[9]=aspnam[i];//奖惩人员姓名
			params[10]=rewardpunishmentEntity.getrap_proposer();//提议人姓名
			try {
				rows = jdbctemplate.update(sql.toString(), params);
			} catch (Exception e) {
				// TODO: handle exception
				log.error("查询错误："+e.getMessage());
			}
		}*/
		/*System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");*/
		return rows;
	}
	@Override
	public Integer editRewardPunishment(RewardPunishmentEntity rewardpunishmentEntity) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("update ehr_rewardandpunishment set RAPI_CATEGORY_ID=?,RAP_P_ID=?"
				+ ",RAP_P_NAME=?,RAP_ITEM_ID=?,RAP_DATE=?,RAP_REASON=?,RAP_MONEY=?"
				+ ",RAP_PROPOSER_ID=?,RAP_PROPOSER_NAME=?,RAP_DESC=? where RAP_ID=?");
		Object[] params=new Object[11];
		params[0]=rewardpunishmentEntity.getrap_category();
		params[1]=rewardpunishmentEntity.getrap_p_id();
		params[2]=rewardpunishmentEntity.getrap_p();
		params[3]=rewardpunishmentEntity.getrap_item();
		params[4]=rewardpunishmentEntity.getrap_date();
		params[5]=rewardpunishmentEntity.getrap_reason();
		params[6]=rewardpunishmentEntity.getrap_money();
		params[7]=rewardpunishmentEntity.getrap_proposer_id();
		params[8]=rewardpunishmentEntity.getrap_proposer();
		params[9]=rewardpunishmentEntity.getrap_desc();
		params[10]=rewardpunishmentEntity.getrap_id();
		int rows =-1;
		int rapState;
		try {
			StringBuffer sql_selectRAPstate = new StringBuffer();
			sql_selectRAPstate.append("SELECT RAP_STATE from ehr_rewardandpunishment WHERE rap_id="+rewardpunishmentEntity.getrap_id());
			rapState=jdbctemplate.queryForInt(sql_selectRAPstate.toString());
			//state:0奖惩记录已经归档，不允许修改1成功-1失败
			if(rapState==1){
				rows=0;
			}else{
				try {
					rows = jdbctemplate.update(sql.toString(), params);
				} catch (Exception e) {
					// TODO: handle exception
					log.error("修改错误："+e.getMessage());
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("查询错误："+e.getMessage());
		}
		
		
		return rows;
	}
	@Override
	public Integer removeRewardPunishment(String rap_id) {
		// TODO Auto-generated method stub
		int state = 1;
		int rapState;
		try {
			StringBuffer sql_selectRAPstate = new StringBuffer();
			sql_selectRAPstate.append("SELECT RAP_STATE from ehr_rewardandpunishment WHERE rap_id="+rap_id);
			rapState=jdbctemplate.queryForInt(sql_selectRAPstate.toString());
			//state:0奖惩记录已经归档，不允许删除1成功-1失败
			if(rapState==1){
		        state = 0;
			}else{
				try {
					StringBuffer sql_joblevel = new StringBuffer();
					sql_joblevel.append("delete from ehr_rewardandpunishment where rap_id=?");
					jdbctemplate.update(sql_joblevel.toString(),rap_id);
				} catch (Exception e) {
					// TODO: handle exception
					state = -1;
					log.error("删除错误："+e.getMessage());
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("查询错误："+e.getMessage());
		}
		return state;
	}
}
