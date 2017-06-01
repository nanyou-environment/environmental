package com.nanyou.admin.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nanyou.admin.dao.RubbishCatJdbcDao;
import com.nanyou.admin.service.RubbishCatService;
import com.nanyou.common.model.RubbishCat;

@Service
public class RubbishCatServiceImpl implements RubbishCatService {
	
	@Autowired
	private RubbishCatJdbcDao rubbishCatJdbcDao;

	public void setRubbishCatJdbcDao(RubbishCatJdbcDao rubbishCatJdbcDao) {
		this.rubbishCatJdbcDao = rubbishCatJdbcDao;
	}

	public void createRubbishCat(RubbishCat rubbishCat) {
		rubbishCatJdbcDao.createRubbishCat(rubbishCat);
	}

	public void updateRubbishCat(RubbishCat rubbishCat) {
		rubbishCatJdbcDao.updateRubbishCat(rubbishCat);
	}

	public void deleteRubbishCat(Long catId) {
		rubbishCatJdbcDao.deleteRubbishCat(catId);
	}

	public RubbishCat getRubbishCat(Long catId) {
		return rubbishCatJdbcDao.getRubbishCat(catId);
	}

}

