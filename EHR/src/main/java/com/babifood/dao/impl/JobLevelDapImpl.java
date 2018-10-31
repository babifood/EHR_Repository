package com.babifood.dao.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.babifood.dao.JobLevelDao;
import com.babifood.entity.PositionEntity;
import com.babifood.entity.PostEntity;
@Repository
public class JobLevelDapImpl implements JobLevelDao {
	@Autowired
	JdbcTemplate jdbctemplate;
	Logger log = LoggerFactory.getLogger(LoginDaoImpl.class);
	//职级方法
	@Override
	public List<Map<String, Object>> loadJobLevelAll(Integer JobLevel_id,String JobLeverl_name,String position_name) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT u.JOBLEVEL_ID AS joblevel_id,u.JOBLEVEL_NAME AS joblevel_name,u.JOBLEVEL_DESC AS joblevel_desc,r.POSITION_NAME AS position_name ");
		sql.append(" from ehr_joblevel u");
		sql.append(" left JOIN (SELECT JOBLEVEL_ID,GROUP_CONCAT(POSITION_NAME) AS POSITION_NAME FROM ehr_position GROUP BY JOBLEVEL_ID) r");
		sql.append(" ON u.JOBLEVEL_ID=r.JOBLEVEL_ID where 1=1");
		if(JobLeverl_name!=null&&!JobLeverl_name.equals("")){
			sql.append(" and joblevel_name like '%"+JobLeverl_name+"%'");
		}
		if(position_name!=null&&!position_name.equals("")){
			sql.append(" and position_name like '%"+position_name+"%'");
		}
		sql.append(" GROUP BY joblevel_id ASC");
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
	public Integer saveJobLevel(String joblevel_name,String joblevel_desc) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("insert into ehr_joblevel (JOBLEVEL_NAME,JOBLEVEL_DESC) ");
		sql.append(" values(?,?)");
		Object[] params=new Object[2];
		params[0]=joblevel_name;
		params[1]=joblevel_desc;
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
	public Integer eidtJobLevel(Integer joblevel_id,String joblevel_name,String joblevel_desc) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("update ehr_joblevel set JOBLEVEL_NAME=?,JOBLEVEL_DESC=? where JOBLEVEL_ID=?");
		Object[] params=new Object[3];
		params[0]=joblevel_name;
		params[1]=joblevel_desc;
		params[2]=joblevel_id;
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
	public Integer removeJobLevel(Integer joblevel_id) {
		// TODO Auto-generated method stub
		int state = 1;
		int count;
		try {
			StringBuffer sql_selectjoblevelposition = new StringBuffer();
			sql_selectjoblevelposition.append("SELECT COUNT(JOBLEVEL_ID) from ehr_position WHERE JOBLEVEL_ID="+joblevel_id);
			count=jdbctemplate.queryForInt(sql_selectjoblevelposition.toString());
			//state:0职级下存在职位数据1成功-1失败
			if(count>0){
		        state = 0;
			}else{
				try {
					StringBuffer sql_joblevel = new StringBuffer();
					sql_joblevel.append("delete from ehr_joblevel where JOBLEVEL_ID=?");
					jdbctemplate.update(sql_joblevel.toString(),joblevel_id);
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
	public List<Map<String, Object>> loadComboboxJobLevelData() {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("select JOBLEVEL_ID as LEVELID,JOBLEVEL_NAME AS LEVELNAME,JOBLEVEL_DESC as LEVELDESC");
		sql.append(" from ehr_joblevel");
		List<Map<String, Object>> list = null;
		try {
			list=jdbctemplate.queryForList(sql.toString());
		} catch (Exception e) {
			// TODO: handle exception
			log.error("查询错误："+e.getMessage());
		}
		return list;		
	}
	//职位ID+职位名称+职级名称显示
	@Override
	public List<Map<String, Object>> loadPositionAll(Integer Position_id,String Position_name,Integer JobLevel_id,String JobLevel_name,String post_name) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("select u.POSITION_ID as position_id,u.POSITION_NAME as position_name,u.JOBLEVEL_ID as joblevel_id,r.JOBLEVEL_ID,r.JOBLEVEL_NAME as joblevel_name,r.JOBLEVEL_DESC as joblevel_desc,z.post_name");
		sql.append(" from ehr_position u inner join ehr_joblevel r on u.JOBLEVEL_ID = r.JOBLEVEL_ID");
		sql.append(" left JOIN (SELECT POSITION_ID,GROUP_CONCAT(POST_NAME) AS post_name FROM ehr_post GROUP BY POSITION_ID) z ON u.POSITION_ID=z.POSITION_ID where 1=1");
		if(Position_name!=null&&!Position_name.equals("")){
			sql.append(" and u.POSITION_NAME like '%"+Position_name+"%'");
		}
		if(JobLevel_name!=null&&!JobLevel_name.equals("")){
			sql.append(" and r.JOBLEVEL_NAME like '%"+JobLevel_name+"%'");
		}
		if(post_name!=null&&!post_name.equals("")){
			sql.append(" and z.post_name like '%"+post_name+"%'");
		}
		sql.append(" GROUP BY position_id ASC");
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
	public Integer savePosition(String Position_name,Integer JobLevel_id) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("insert into ehr_position (POSITION_NAME,JOBLEVEL_ID) ");
		sql.append(" values(?,?)");
		Object[] params=new Object[2];
		params[0]=Position_name;
		params[1]=JobLevel_id;
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
	public Integer editPosition(PositionEntity positionEntity) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("update ehr_position set POSITION_NAME=?,JOBLEVEL_ID=? where POSITION_ID=?");
		Object[] params=new Object[3];
		params[0]=positionEntity.getposition_name();
		params[1]=positionEntity.getjoblevel_id();
		params[2]=positionEntity.getposition_id();
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
	public Integer removePosition(Integer Position_id) {
		// TODO Auto-generated method stub
		int state = 1;
		int count;
		try {
			StringBuffer sql_selectpositionpost = new StringBuffer();
			sql_selectpositionpost.append("SELECT COUNT(POSITION_ID) from ehr_post WHERE POSITION_ID="+Position_id);
			count=jdbctemplate.queryForInt(sql_selectpositionpost.toString());
			//state:0职位下存在岗位数据1成功-1失败
			if(count>0){
		        state = 0;
			}else{
				try {
					StringBuffer sql_deleteposition = new StringBuffer();
					sql_deleteposition.append("delete from ehr_position where POSITION_ID=?");
					jdbctemplate.update(sql_deleteposition.toString(),Position_id);
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
	public List<Map<String, Object>> loadComboboxPositionData() {
		// TODO Auto-generated method stub
				StringBuffer sql = new StringBuffer();
				sql.append("SELECT P.POSITION_ID,P.POSITION_NAME,L.JOBLEVEL_NAME FROM ehr_position P INNER JOIN ehr_joblevel L ON P.JOBLEVEL_ID=L.JOBLEVEL_ID");
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
	public List<Map<String, Object>> loadPostAll(Integer post_id, String post_name, Integer position_id,String position_name) {
		// TODO Auto-generated method stub
				StringBuffer sql = new StringBuffer();
				sql.append("SELECT U.POST_ID AS post_id,U.POST_NAME AS post_name,U.POSITION_ID AS position_id,P.POSITION_NAME AS position_name,L.JOBLEVEL_ID as joblevel_id,L.JOBLEVEL_NAME AS joblevel_name");
				sql.append(" FROM ehr_post U");
				sql.append(" INNER JOIN ehr_position P ON U.POSITION_ID = P.POSITION_ID");
				sql.append(" INNER JOIN ehr_joblevel L ON P.JOBLEVEL_ID = L.JOBLEVEL_ID");
				sql.append(" WHERE 1=1");
				if(post_name!=null&&!post_name.equals("")){
					sql.append(" and U.POST_NAME like '%"+post_name+"%'");
				}
				if(position_name!=null&&!position_name.equals("")){
					sql.append(" and P.POSITION_NAME like '%"+position_name+"%'");
				}
				sql.append(" GROUP BY U.post_id ASC");
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
	public Integer savePost(String post_name, Integer position_id) {
		// TODO Auto-generated method stub
				StringBuffer sql = new StringBuffer();
				sql.append("insert into ehr_post (POST_NAME,POSITION_ID) ");
				sql.append(" values(?,?)");
				Object[] params=new Object[2];
				params[0]=post_name;
				params[1]=position_id;
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
	public Integer editPost(PostEntity postEntity) {
		// TODO Auto-generated method stub
				StringBuffer sql = new StringBuffer();
				sql.append("update ehr_post set POST_NAME=?,POSITION_ID=? where POST_ID=?");
				Object[] params=new Object[3];
				params[0]=postEntity.getpost_name();
				params[1]=postEntity.getposition_id();
				params[2]=postEntity.getpost_id();
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
	public Integer removePost(Integer post_id) {
		// TODO Auto-generated method stub
				int state = 1;
				try {
					StringBuffer sql_post = new StringBuffer();
					sql_post.append("delete from ehr_post where POST_ID=?");
					jdbctemplate.update(sql_post.toString(),post_id);
				} catch (Exception e) {
					// TODO: handle exception
					state = -1;
					log.error("查询错误："+e.getMessage());
				}
				return state;
	}

}
