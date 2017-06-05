package com.nanyou.admin.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.nanyou.common.model.RubbishCat;
import com.nanyou.framework.jdbc.BaseJdbcDao;
import com.nanyou.framework.util.PageUtils;

@Repository
public class RubbishCatJdbcDao extends BaseJdbcDao {

	public void createRubbishCat(RubbishCat rubbishCat) {
		String sql = loadSQL("createRubbishCat");
		SqlParameterSource parameters = new BeanPropertySqlParameterSource(
				rubbishCat);
		getNamedParameterJdbcTemplate().update(sql, parameters);

	}

	public void updateRubbishCat(RubbishCat rubbishCat) {
		String sql = loadSQL("updateRubbishCat");
		SqlParameterSource parameters = new BeanPropertySqlParameterSource(
				rubbishCat);
		getNamedParameterJdbcTemplate().update(sql, parameters);
	}

	public void deleteRubbishCat(Long catId) {
		String sql = loadSQL("deleteRubbishCat");
		Map paramMap = new HashMap();
		paramMap.put("catId", catId);
		getNamedParameterJdbcTemplate().update(sql, paramMap);
	}

	public RubbishCat getRubbishCat(Long catId) {
		String sql = loadSQL("getRubbishCat");
		Map paramMap = new HashMap();
		paramMap.put("catId", catId);
		Collection list = getNamedParameterJdbcTemplate().query(sql, paramMap,
				new BeanPropertyRowMapper(RubbishCat.class));

		if (list == null || list.isEmpty()) {
			return null;
		}
		return (RubbishCat) list.iterator().next();
	}
	
	public List<RubbishCat> listAllRubbishCat() {
		String sql = loadSQL("listRubbishCat");
		return getNamedParameterJdbcTemplate().query(sql,
				new BeanPropertyRowMapper(RubbishCat.class));
	}
	
	public Map listRubbishCat(Map params){
		Map pageData = new HashMap();
        String sql = loadSQL("listRubbishCat", params);
        Integer totalCount = getNamedParameterJdbcTemplate().queryForObject(getTotalCountString(sql), params, Integer.class);
        pageData.put("totalCount", totalCount);
        pageData.put("totalPage", PageUtils.getTotalPage(totalCount));

        sql=getPaginationString(sql, PageUtils.getStartNum((Integer)params.get("page")), PageUtils.pageSize);
        List<RubbishCat> list = getNamedParameterJdbcTemplate().query(sql, params, new BeanPropertyRowMapper(RubbishCat.class));
        pageData.put("data", list);
        return pageData;
	}
	
}
