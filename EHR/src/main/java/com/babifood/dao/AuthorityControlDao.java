package com.babifood.dao;
/**
 * 权限控制
 * @author BABIFOOD
 *
 */

import java.util.List;
import java.util.Map;

public interface AuthorityControlDao {
	public List<Map<String, Object>> loadUserDataAuthority();
	public StringBuffer jointDataAuthoritySql(String companyCode,String organCode,StringBuffer sql);
	public List<String> findPNumberList(List<Map<String, Object>> auths);
}
