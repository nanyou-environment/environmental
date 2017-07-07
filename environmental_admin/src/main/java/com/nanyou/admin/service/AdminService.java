package com.nanyou.admin.service;

import com.nanyou.common.model.Admin;

public interface AdminService {
	public void createAdmin(Admin admin);

	public void updateAdmin(Admin admin);

	public void deleteAdmin(Long adminId);

	public Admin getAdmin(Long adminId);
	
	public Admin getAdminByAccountAndPassword(String account,String password);

}

