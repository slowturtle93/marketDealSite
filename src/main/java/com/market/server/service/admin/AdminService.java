package com.market.server.service.admin;

import com.market.server.dto.admin.AdminDTO;

public interface AdminService {
	
	public void passwordMatch(String loginId, String loginPw);
	
	boolean isDuplicatedId(String id);
	
	public int insert(AdminDTO adminDTO);
	
	public AdminDTO login(String loginId, String loginPw);
	
	public int updatePassword(int adminNo, String loginId, String passwordBeforeChange, String passwordAfterChange);
	
	public void update(AdminDTO adminDTO);
	
	public int delete(int adminNo);
	
}
