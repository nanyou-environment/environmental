package com.nanyou.common.model;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.Date;

public class RubbishCat {

	private Long catId;
	private String name;
	private String desc;

	public Long getCatId() {
		return catId;
	}

	public void setCatId(Long aCatId) {
		this.catId = aCatId;
	}

	public String getName() {
		return name;
	}

	public void setName(String aName) {
		this.name = aName;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String aDesc) {
		this.desc = aDesc;
	}
}
