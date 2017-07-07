package com.nanyou.common.model;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.Date;

public class Admin {

	private Long adminId;
	private String name;
	private String phone;
	private String account;
	private String password;

	public Long getAdminId() {
		return adminId;
	}

	public void setAdminId(Long aAdminId) {
		this.adminId = aAdminId;
	}

	public String getName() {
		return name;
	}

	public void setName(String aName) {
		this.name = aName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String aPhone) {
		this.phone = aPhone;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String aAccount) {
		this.account = aAccount;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String aPassword) {
		this.password = aPassword;
	}
}
