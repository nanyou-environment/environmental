package com.nanyou.admin.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nanyou.admin.dao.AdminJdbcDao;
import com.nanyou.admin.service.AdminService;
import com.nanyou.common.model.Admin;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private AdminJdbcDao adminJdbcDao;

	public void setAdminJdbcDao(AdminJdbcDao adminJdbcDao) {
		this.adminJdbcDao = adminJdbcDao;
	}

	public void createAdmin(Admin admin) {
		adminJdbcDao.createAdmin(admin);
	}

	public void updateAdmin(Admin admin) {
		adminJdbcDao.updateAdmin(admin);
	}

	public void deleteAdmin(Long adminId) {
		adminJdbcDao.deleteAdmin(adminId);
	}

	public Admin getAdmin(Long adminId) {
		return adminJdbcDao.getAdmin(adminId);
	}

	public Admin getAdminByAccountAndPassword(String account, String password) {
		return adminJdbcDao.getAdminByAccountAndPassword(account, password);
	}

}

