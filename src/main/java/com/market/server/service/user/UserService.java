package com.market.server.service.user;

import com.market.server.dto.user.UserDTO;

public interface UserService {
	
	public void passwordMatch(String loginId, String loginPw);
	
	public UserDTO getUserInfo(String loginId);
	
	boolean isDuplicatedId(String loginId);
	
	public int insert(UserDTO userDTO);
	
	public UserDTO login(String loginId, String loginPw);
	
	public int updatePassword(int loginNo, String loginId, String passwordBeforeChange, String passwordAfterChange);
	
	public void update(UserDTO userDTO);
	
	public int delete(int loginNo);
}
