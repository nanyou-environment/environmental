package com.nanyou.admin.service.Impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nanyou.admin.dao.RubbishJdbcDao;
import com.nanyou.admin.service.RubbishService;
import com.nanyou.common.model.Rubbish;

@Service
public class RubbishServiceImpl implements RubbishService {
	
	@Autowired
	private RubbishJdbcDao rubbishJdbcDao;

	public void setRubbishJdbcDao(RubbishJdbcDao rubbishJdbcDao) {
		this.rubbishJdbcDao = rubbishJdbcDao;
	}

	public void createRubbish(Rubbish rubbish) {
		rubbishJdbcDao.createRubbish(rubbish);
	}

	public void updateRubbish(Rubbish rubbish) {
		rubbishJdbcDao.updateRubbish(rubbish);
	}

	public void deleteRubbish(Long rubbishId) {
		rubbishJdbcDao.deleteRubbish(rubbishId);
	}

	public Rubbish getRubbish(Long rubbishId) {
		return rubbishJdbcDao.getRubbish(rubbishId);
	}

	public Map listRubbishByCatId(Map params) {
		return rubbishJdbcDao.listRubbishByCatId(params);
	}

}

