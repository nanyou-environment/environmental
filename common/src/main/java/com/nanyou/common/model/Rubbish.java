package com.nanyou.common.model;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.Date;

public class Rubbish {

	private Long rubbishId;
	private Long catId;
	private String name;
	private String icon;
	private String desc;

	public Long getRubbishId() {
		return rubbishId;
	}

	public void setRubbishId(Long aRubbishId) {
		this.rubbishId = aRubbishId;
	}

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

	public String getIcon() {
		return icon;
	}

	public void setIcon(String aIcon) {
		this.icon = aIcon;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String aDesc) {
		this.desc = aDesc;
	}
}
