package com.nanyou.admin.service;

import com.nanyou.common.model.RubbishCat;

public interface RubbishCatService {
	public void createRubbishCat(RubbishCat rubbishCat);

	public void updateRubbishCat(RubbishCat rubbishCat);

	public void deleteRubbishCat(Long catId);

	public RubbishCat getRubbishCat(Long catId);

}

