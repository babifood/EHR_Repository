package com.babifood.service.impl;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.babifood.dao.RewardPunishmentDao;
import com.babifood.entity.PositionEntity;
import com.babifood.entity.RewardPunishmentEntity;
import com.babifood.service.RewardPunishmentService;
import com.babifood.utils.IdGen;
import com.babifood.utils.MD5;
@Service
public class RewardPunishmentServiceImpl implements RewardPunishmentService {
	@Autowired
	RewardPunishmentDao RewardPunishmentDao;
	@Override
	public List<Map<String, Object>> loadRAPItemAll(String category_id, String item_name) {
		// TODO Auto-generated method stub
		return RewardPunishmentDao.loadRAPItemAll(category_id,item_name);
	}
	@Override
	public Integer saveRAPItem(String item_name, String category_id,String category_name) {
		// TODO Auto-generated method stub
		String item_id = IdGen.uuid();
		return RewardPunishmentDao.saveRAPItem(item_id,item_name,category_id,category_name);
	}
	@Override
	public Integer eidtRAPItem(String item_id, String item_name, String category_id,String category_name) {
		// TODO Auto-generated method stub
		return RewardPunishmentDao.eidtRAPItem(item_id,item_name,category_id,category_name);
	}
	@Override
	public Integer removeRAPItem(String item_id) {
		// TODO Auto-generated method stub
		return RewardPunishmentDao.removeRAPItem(item_id);
	}
	@Override
	public List<Map<String, Object>> loadComboboxRAPItemData(String category_id) {
		// TODO Auto-generated method stub
		return RewardPunishmentDao.loadComboboxRAPItemData(category_id);
	}
	@Override
	public List<Map<String, Object>> loadRewardPunishment(String rap_category, String rap_item) {
		// TODO Auto-generated method stub
		return RewardPunishmentDao.loadRewardPunishment(rap_category,rap_item);
	}
	@Override
	public int[] saveRewardPunishment(RewardPunishmentEntity rewardpunishmentEntity) {
		// TODO Auto-generated method stub
		//rewardpunishmentEntity.setrap_id(IdGen.uuid());
		return RewardPunishmentDao.saveRewardPunishment(rewardpunishmentEntity);
	}
	@Override
	public Integer editRewardPunishment(RewardPunishmentEntity rewardpunishmentEntity) {
		// TODO Auto-generated method stub
		return RewardPunishmentDao.editRewardPunishment(rewardpunishmentEntity);
	}
	@Override
	public Integer removeRewardPunishment(String rap_id) {
		// TODO Auto-generated method stub
		return RewardPunishmentDao.removeRewardPunishment(rap_id);
	}

}
