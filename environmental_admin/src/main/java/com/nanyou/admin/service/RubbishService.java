package com.nanyou.admin.service;

import java.util.Map;

import com.nanyou.common.model.Rubbish;

public interface RubbishService {
	public void createRubbish(Rubbish rubbish);

	public void updateRubbish(Rubbish rubbish);

	public void deleteRubbish(Long rubbishId);

	public Rubbish getRubbish(Long rubbishId);
	
	public Map listRubbishByCatId(Map params);

}

