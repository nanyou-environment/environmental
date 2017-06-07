package com.nanyou.admin.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.nanyou.common.model.Rubbish;
import com.nanyou.framework.jdbc.BaseJdbcDao;
import com.nanyou.framework.util.PageUtils;

@Repository
public class RubbishJdbcDao extends BaseJdbcDao {

	public void createRubbish(Rubbish rubbish) {
		String sql = loadSQL("createRubbish");
		SqlParameterSource parameters = new BeanPropertySqlParameterSource(
				rubbish);
		getNamedParameterJdbcTemplate().update(sql, parameters);

	}

	public void updateRubbish(Rubbish rubbish) {
		String sql = loadSQL("updateRubbish");
		SqlParameterSource parameters = new BeanPropertySqlParameterSource(
				rubbish);
		getNamedParameterJdbcTemplate().update(sql, parameters);
	}

	public void deleteRubbish(Long rubbishId) {
		String sql = loadSQL("deleteRubbish");
		Map paramMap = new HashMap();
		paramMap.put("rubbishId", rubbishId);
		getNamedParameterJdbcTemplate().update(sql, paramMap);
	}

	public Rubbish getRubbish(Long rubbishId) {
		String sql = loadSQL("getRubbish");
		Map paramMap = new HashMap();
		paramMap.put("rubbishId", rubbishId);
		Collection list = getNamedParameterJdbcTemplate().query(sql, paramMap,
				new BeanPropertyRowMapper(Rubbish.class));

		if (list == null || list.isEmpty()) {
			return null;
		}
		return (Rubbish) list.iterator().next();
	}
	
	public Map listRubbishByCatId(Map params){
		Map pageData = new HashMap();
        String sql = loadSQL("listRubbishByCatId", params);
        Integer totalCount = getNamedParameterJdbcTemplate().queryForObject(getTotalCountString(sql), params, Integer.class);
        pageData.put("totalCount", totalCount);
        pageData.put("totalPage", PageUtils.getTotalPage(totalCount));

        sql=getPaginationString(sql, PageUtils.getStartNum((Integer)params.get("page")), PageUtils.pageSize);
        List<Rubbish> list = getNamedParameterJdbcTemplate().query(sql, params, new BeanPropertyRowMapper(Rubbish.class));
        pageData.put("data", list);
        return pageData;
	}

}
