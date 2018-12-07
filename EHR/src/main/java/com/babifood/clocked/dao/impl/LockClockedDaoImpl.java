package com.babifood.clocked.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.babifood.clocked.dao.LockClockedDao;
import com.babifood.clocked.entrty.ClockedResultBases;
@Repository
public class LockClockedDaoImpl implements LockClockedDao {
	@Autowired
	JdbcTemplate jdbctemplate;
	@Override
	public int[] updateLockDataFlag(List<ClockedResultBases> updateDataList)  throws Exception {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("update ehr_checking_result set ");
		sql.append("dataflag=? ");
		sql.append("where WorkNum=? and checkingDate=?");
		final String strSql = sql.toString();
		List<Object[]> paramsList = new ArrayList<>();
		int size = updateDataList==null?0:updateDataList.size();
		for(int i=0;i<size;i++){
			paramsList.add(new Object[]{
					updateDataList.get(i).getDataflag(),
					updateDataList.get(i).getWorkNum(),
					updateDataList.get(i).getCheckingDate()
					});
		}
		return jdbctemplate.batchUpdate(strSql, paramsList);
	}

}
