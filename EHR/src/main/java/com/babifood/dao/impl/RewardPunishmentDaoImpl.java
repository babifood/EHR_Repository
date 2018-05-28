package com.babifood.dao.impl;

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
		sql.append("SELECT RAPI_ID as item_id,RAPI_NAME as item_name,RAPI_CATEGORY_ID as category_id ");
		sql.append(" from ehr_rewardandpunishment_item where 1=1");
		if(category_id!=null&&!category_id.equals("")){
			sql.append(" and RAPI_CATEGORY_ID like '%"+category_id+"%'");
		}
		if(item_name!=null&&!item_name.equals("")){
			sql.append(" and RAPI_NAME like '%"+item_name+"%'");
		}
		sql.append(" GROUP BY RAPI_CATEGORY_ID ASC");
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
	public Integer saveRAPItem(String item_id,String item_name,String category_id) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("insert into ehr_rewardandpunishment_item (RAPI_ID,RAPI_NAME,RAPI_CATEGORY_ID) ");
		sql.append(" values(?,?,?)");
		Object[] params=new Object[3];
		params[0]=item_id;
		params[1]=item_name;
		params[2]=category_id;
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
	public Integer eidtRAPItem(String item_id,String item_name,String category_id) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("update ehr_rewardandpunishment_item set RAPI_NAME=?,RAPI_CATEGORY_ID=? where RAPI_ID=?");
		Object[] params=new Object[3];
		params[0]=item_name;
		params[1]=category_id;
		params[2]=item_id;
		int rows =-1;
		try {
			rows = jdbctemplate.update(sql.toString(), params);
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
			StringBuffer sql_selectjoblevelposition = new StringBuffer();
			sql_selectjoblevelposition.append("SELECT COUNT(RAP_ITEM_ID) from ehr_rewardandpunishment WHERE RAP_ITEM_ID="+item_id);
			count=jdbctemplate.queryForInt(sql_selectjoblevelposition.toString());
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
	public List<Map<String, Object>> loadComboboxRAPItemData() {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("select RAPI_ID as id,RAPI_NAME as text");
		sql.append(" from ehr_rewardandpunishment_item");
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
	public List<Map<String, Object>> loadRewardPunishment(String rap_category, String rap_item) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("select u.RAP_ID as rap_id,u.RAPI_CATEGORY_ID as rap_category,u.RAP_P_ID as rap_p,u.RAP_P_NAME as rap_p_name"
				+ ",u.RAP_ITEM_ID as rap_item_id,u.RAP_DATE as rap_date,u.RAP_REASON as rap_reason"
				+ ",u.RAP_MONEY as rap_money,u.RAP_PROPOSER_ID as rap_proposer,u.RAP_PROPOSER_NAME as rap_proposer_name"
				+ ",u.RAP_DESC as rap_desc,r.RAPI_ID,r.RAPI_NAME as rap_item_name");
		sql.append(" from ehr_rewardandpunishment u inner join ehr_rewardandpunishment_item r on u.RAP_ITEM_ID = r.RAPI_ID where 1=1");
		if(rap_category!=null&&!rap_category.equals("")){
			sql.append(" and u.RAPI_CATEGORY_ID like '%"+rap_category+"%'");
		}
		if(rap_item!=null&&!rap_item.equals("")){
			sql.append(" and r.RAPI_NAME like '%"+rap_item+"%'");
		}
		sql.append(" GROUP BY rap_id ASC");
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
	public Integer saveRewardPunishment(RewardPunishmentEntity rewardpunishmentEntity) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		String statu="0";
		sql.append("insert into ehr_rewardandpunishment (RAPI_CATEGORY_ID,RAP_P_ID,RAP_ITEM_ID,RAP_DATE,RAP_REASON,RAP_MONEY,RAP_PROPOSER_ID,RAP_DESC,RAP_STATE) ");
		sql.append(" values(?,?,?,?,?,?,?,?,?)");
		Object[] params=new Object[9];
		params[0]=rewardpunishmentEntity.getrap_category();
		params[1]=rewardpunishmentEntity.getrap_p_id();
		params[2]=rewardpunishmentEntity.getrap_item();
		params[3]=rewardpunishmentEntity.getrap_date();
		params[4]=rewardpunishmentEntity.getrap_reason();
		params[5]=rewardpunishmentEntity.getrap_money();
		params[6]=rewardpunishmentEntity.getrap_proposer_id();
		params[7]=rewardpunishmentEntity.getrap_desc();
		params[8]=statu;/*
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		System.out.println(rewardpunishmentEntity.getrap_id());
		System.out.println(rewardpunishmentEntity.getrap_category());
		System.out.println(rewardpunishmentEntity.getrap_p_id());
		System.out.println(rewardpunishmentEntity.getrap_item());
		System.out.println(rewardpunishmentEntity.getrap_date());
		System.out.println(rewardpunishmentEntity.getrap_reason());
		System.out.println(rewardpunishmentEntity.getrap_money());
		System.out.println(rewardpunishmentEntity.getrap_proposer_id());
		System.out.println(rewardpunishmentEntity.getrap_desc());
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");*/
		int rows =-1;
		try {
			rows = jdbctemplate.update(sql.toString(), params);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("查询错误："+e.getMessage());
		}
		return rows;
	}
	@Override
	public Integer editRewardPunishment(RewardPunishmentEntity rewardpunishmentEntity) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("update ehr_rewardandpunishment set RAPI_CATEGORY_ID=?,RAP_P_ID=?,RAP_ITEM_ID=?,RAP_DATE=?,RAP_REASON=?,RAP_MONEY=?,RAP_PROPOSER_ID=?,RAP_DESC=? where RAP_ID=?");
		Object[] params=new Object[9];
		params[0]=rewardpunishmentEntity.getrap_id();
		params[1]=rewardpunishmentEntity.getrap_category();
		params[2]=rewardpunishmentEntity.getrap_p_id();
		params[3]=rewardpunishmentEntity.getrap_item();
		params[4]=rewardpunishmentEntity.getrap_date();
		params[5]=rewardpunishmentEntity.getrap_reason();
		params[6]=rewardpunishmentEntity.getrap_money();
		params[7]=rewardpunishmentEntity.getrap_proposer_id();
		params[8]=rewardpunishmentEntity.getrap_desc();
		int rows =-1;
		try {
			rows = jdbctemplate.update(sql.toString(), params);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("查询错误："+e.getMessage());
		}
		return rows;
	}
	@Override
	public Integer removeRewardPunishment(String rap_id) {
		// TODO Auto-generated method stub
		int state = 1;
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("delete from ehr_rewardandpunishment where rap_id=?");
			jdbctemplate.update(sql.toString(),rap_id);
		} catch (Exception e) {
			// TODO: handle exception
			state = -1;
			log.error("查询错误："+e.getMessage());
		}
		return state;
	}
}
