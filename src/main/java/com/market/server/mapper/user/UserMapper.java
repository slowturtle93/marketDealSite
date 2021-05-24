package com.market.server.mapper.user;

import org.springframework.stereotype.Repository;

import com.market.server.dto.user.UserDTO;

import io.lettuce.core.dynamic.annotation.Param;

@Repository
public interface UserMapper {
	
	int isDuplicatedId(String id);
	
	public int insert(UserDTO userDTO);
	
	public UserDTO login(@Param("loginId") String loginId, @Param("loginPw") String loginPw);
	
	public int updatePassword(String login, String loginPw);
	
	public int update(UserDTO userDTO);
	
	public int delete(String loginId);
}
