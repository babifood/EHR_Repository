package com.babifood.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.babifood.dao.JobLevelDao;
import com.babifood.entity.LoginEntity;
import com.babifood.entity.PositionEntity;
import com.babifood.entity.PostEntity;
import com.babifood.entity.RoleMenuEntity;
import com.babifood.service.JobLevelPageService;
import com.babifood.utils.IdGen;
import com.babifood.utils.MD5;
@Service
public class JobLevelServiceImpl implements JobLevelPageService {
	@Autowired
	JobLevelDao JobLevelDao;
	public List<Map<String, Object>> loadJobLevelAll(Integer JobLevel_id,String JobLeverl_name,String position_name) {
		// TODO Auto-generated method stub
		return JobLevelDao.loadJobLevelAll(JobLevel_id,JobLeverl_name,position_name);
	}
	@Override
	public Integer saveJobLevel(Integer joblevel_id,String joblevel_name) {
		// TODO Auto-generated method stub
		return JobLevelDao.saveJobLevel(joblevel_id,joblevel_name);
	}
	@Override
	public Integer editJobLevel(Integer joblevel_id,String joblevel_name) {
		// TODO Auto-generated method stub
		return JobLevelDao.eidtJobLevel(joblevel_id, joblevel_name);
	}
	@Override
	public Integer removeJobLevel(Integer joblevel_id) {
		// TODO Auto-generated method stub
		return JobLevelDao.removeJobLevel(joblevel_id);
	}
	@Override
	public List<Map<String, Object>> loadComboboxJobLevelData() {
		// TODO Auto-generated method stub
		return JobLevelDao.loadComboboxJobLevelData();
	}
	@Override
	public List<Map<String, Object>> loadPositionAll(Integer Position_id,String Position_name,Integer JobLevel_id,String JobLevel_name,String post_name) {
		// TODO Auto-generated method stub
		return JobLevelDao.loadPositionAll(Position_id,Position_name,JobLevel_id,JobLevel_name,post_name);
	}
	@Override
	public Integer savePosition(String Position_name, Integer JobLevel_id) {
		// TODO Auto-generated method stub
		return JobLevelDao.savePosition(Position_name,JobLevel_id);
	}
	@Override
	public Integer editPosition(PositionEntity positionEntity) {
		// TODO Auto-generated method stub
		return JobLevelDao.editPosition(positionEntity);
	}
	@Override
	public Integer removePosition(Integer Position_id) {
		// TODO Auto-generated method stub
		return JobLevelDao.removePosition(Position_id);
	}
	@Override
	public List<Map<String, Object>> loadComboboxPositionData() {
		// TODO Auto-generated method stub
		return JobLevelDao.loadComboboxPositionData();
	}
	@Override
	public List<Map<String, Object>> loadPostAll(Integer post_id, String post_name, Integer position_id,
			String position_name) {
		/// TODO Auto-generated method stub
		return JobLevelDao.loadPostAll(post_id,post_name,position_id,position_name);
	}
	@Override
	public Integer savePost(String post_name, Integer position_id) {
		// TODO Auto-generated method stub
		return JobLevelDao.savePost(post_name,position_id);
	}
	@Override
	public Integer editPost(PostEntity postEntity) {
		// TODO Auto-generated method stub
		return JobLevelDao.editPost(postEntity);
	}
	@Override
	public Integer removePost(Integer post_id) {
		// TODO Auto-generated method stub
		return JobLevelDao.removePost(post_id);
	}

}
