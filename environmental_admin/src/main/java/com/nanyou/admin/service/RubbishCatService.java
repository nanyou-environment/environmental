package com.nanyou.admin.service;

import java.util.List;
import java.util.Map;

import com.nanyou.common.model.RubbishCat;

public interface RubbishCatService {
	public void createRubbishCat(RubbishCat rubbishCat);

	public void updateRubbishCat(RubbishCat rubbishCat);

	public void deleteRubbishCat(Long catId);

	public RubbishCat getRubbishCat(Long catId);
	
	public List<RubbishCat> listAllRubbishCat();
	
	public Map listRubbishCat(Map params);

}

