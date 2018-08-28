package com.babifood.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.babifood.dao.CalculationFormulaDao;

@Repository
public class CalculationFormulaDaoImpl implements CalculationFormulaDao {

	private static Logger log = Logger.getLogger(CalculationFormulaDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Map<String, Object>> findListFormulas() {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT id as id, `name` as `name`, `key` as `key`, base_field as baseField, ");
		sql.append("symbol, `condition`, formula_use as formulaUse, formula_view as formulaView, sort, type ");
		sql.append("FROM ehr_calculation_formula");
		sql.append("");
		sql.append("");
		List<Map<String, Object>> formulaMapList = null;
		try {
			formulaMapList = jdbcTemplate.queryForList(sql.toString());
		} catch (Exception e) {
			log.error("查询公式列表失败", e);
			throw e;
		}
		return formulaMapList;
	}
}
