package com.babifood.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.babifood.dao.CertificatenDao;
import com.babifood.entity.Certificaten;
@Repository
@Transactional
public class CertificatenDaoImpl implements CertificatenDao {
	@Autowired
	JdbcTemplate jdbctemplate;
	public static final Logger log = Logger.getLogger(CertificatenDaoImpl.class);
	@Override
	public List<Map<String, Object>> loadCertificaten(String c_p_number,String c_p_name) throws DataAccessException{
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append("c_id,c_p_id,c_p_number,c_p_name,c_certificate_name,c_organization,c_certificate_number,c_begin_date,c_end_date,c_desc");
		sql.append(" from ehr_certificate where 1=1");
		if(c_p_number!=null&&!c_p_number.equals("")){
			sql.append(" and c_p_number like '%"+c_p_number+"%'");
		}
		if(c_p_name!=null&&!c_p_name.equals("")){
			sql.append(" and c_p_name like '%"+c_p_name+"%'");
		}
		return jdbctemplate.queryForList(sql.toString());
	}

	@Override
	public Integer removeCertificaten(String c_id) throws DataAccessException{
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("delete from ehr_certificate where c_id=?");
		return jdbctemplate.update(sql.toString(),c_id);
	}
	@Override
	public void saveCertificaten(Certificaten certificaten) throws DataAccessException{
		// TODO Auto-generated method stub
		StringBuffer sql_del = new StringBuffer();
		sql_del.append("delete from ehr_certificate where c_id=?");
		StringBuffer sql = new StringBuffer();
		sql.append("insert into ehr_certificate (c_p_id,c_p_number,c_p_name,c_certificate_name,c_organization,c_certificate_number,c_begin_date,c_end_date,c_desc) ");
		sql.append(" values(?,?,?,?,?,?,?,?,?)");
		Object[] params=new Object[9];
		params[0]=certificaten.getC_p_id();
		params[1]=certificaten.getC_p_number();
		params[2]=certificaten.getC_p_name();
		params[3]=certificaten.getC_certificate_name();
		params[4]=certificaten.getC_organization();
		params[5]=certificaten.getC_certificate_number();
		params[6]=certificaten.getC_begin_date();
		params[7]=certificaten.getC_end_date();
		params[8]=certificaten.getC_desc();
		jdbctemplate.update(sql_del.toString(),certificaten.getC_id()==null?"":certificaten.getC_id());
		jdbctemplate.update(sql.toString(), params);
	}

}
