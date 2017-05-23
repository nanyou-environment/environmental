package com.nanyou.framework.facility;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.nanyou.framework.jdbc.BaseJdbcDao;


public class CommonServiceImpl{

	private BaseJdbcDao commonDao;

	public BaseJdbcDao getCommonDao() {
		return commonDao;
	}

	public void setCommonDao(BaseJdbcDao commonDao) {
		this.commonDao = commonDao;
	}
	
	public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate(){
		return this.commonDao.getNamedParameterJdbcTemplate();
	}
	
	public JdbcTemplate getJdbcTemplate(){
		return this.commonDao.getJdbcTemplate();
	}

	public String getPaginationString(String querySql, Integer start, Integer limit) {
		return this.commonDao.getPaginationString(querySql, start, limit);
	}

}
