package com.nanyou.admin.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.nanyou.common.model.Admin;
import com.nanyou.framework.jdbc.BaseJdbcDao;

@Repository
public class AdminJdbcDao extends BaseJdbcDao {

	public void createAdmin(Admin admin) {
		String sql = loadSQL("createAdmin");
		SqlParameterSource parameters = new BeanPropertySqlParameterSource(
				admin);
		getNamedParameterJdbcTemplate().update(sql, parameters);

	}

	public void updateAdmin(Admin admin) {
		String sql = loadSQL("updateAdmin");
		SqlParameterSource parameters = new BeanPropertySqlParameterSource(
				admin);
		getNamedParameterJdbcTemplate().update(sql, parameters);
	}

	public void deleteAdmin(Long adminId) {
		String sql = loadSQL("deleteAdmin");
		Map paramMap = new HashMap();
		paramMap.put("adminId", adminId);
		getNamedParameterJdbcTemplate().update(sql, paramMap);
	}

	public Admin getAdmin(Long adminId) {
		String sql = loadSQL("getAdmin");
		Map paramMap = new HashMap();
		paramMap.put("adminId", adminId);
		Collection list = getNamedParameterJdbcTemplate().query(sql, paramMap,
				new BeanPropertyRowMapper(Admin.class));

		if (list == null || list.isEmpty()) {
			return null;
		}
		return (Admin) list.iterator().next();
	}
	
	public Admin getAdminByAccountAndPassword(String account,String password) {
		String sql = loadSQL("getAdminByAccountAndPassword");
		Map paramMap = new HashMap();
		paramMap.put("account", account);
		paramMap.put("password", password);
		Collection list = getNamedParameterJdbcTemplate().query(sql, paramMap,
				new BeanPropertyRowMapper(Admin.class));

		if (list == null || list.isEmpty()) {
			return null;
		}
		return (Admin) list.iterator().next();
	}

}
