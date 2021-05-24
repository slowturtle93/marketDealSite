package com.market.server.service.user;

import com.market.server.dto.user.UserDTO;

public interface UserService {
	
	boolean isDuplicatedId(String id);
	
	public void insert(UserDTO userDTO);
	
	public UserDTO login(String loginId, String loginPw);
	
	public void updatePassword(String id, String passwordBeforeChange, String passwordAfterChange);
	
	public void update(UserDTO userDTO);
	
	public void delete(String loginId);
}
