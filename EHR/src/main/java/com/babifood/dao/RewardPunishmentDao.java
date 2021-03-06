package com.babifood.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.babifood.entity.RewardPunishmentEntity;


@Repository
public interface RewardPunishmentDao {
	
	//奖惩项目
	public List<Map<String,Object>> loadRAPItemAll(String category_id,String item_name);
	
	public Integer saveRAPItem(String item_id,String item_name,String category_id,String category_name);
	
	public Integer eidtRAPItem(String item_id,String item_name,String category_id,String category_name);
	
	public Integer removeRAPItem(String item_id);
	
	//奖惩记录
	public List<Map<String,Object>> loadComboboxRAPItemData(String category_id);
			
	public List<Map<String,Object>> loadRewardPunishment(String searchKey, String searchVal);
			
	public int[] saveRewardPunishment(RewardPunishmentEntity rewardpunishmentEntity);
			
	public Integer editRewardPunishment(RewardPunishmentEntity rewardpunishmentEntity);
		
	public Integer removeRewardPunishment(String rap_id);
	
	
	
}
