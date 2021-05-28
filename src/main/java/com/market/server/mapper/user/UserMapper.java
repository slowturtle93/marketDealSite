package com.market.server.mapper.user;

import com.market.server.dto.user.UserDTO;

import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
	
	public UserDTO getUserInfo(@Param("loginId") String loginId);
	
	int isDuplicatedId(@Param("loginId")String loginId);
	
	public int insert(UserDTO userDTO);
	
	public UserDTO findByIdAndPassword(@Param("loginId") String loginId, @Param("loginPw") String loginPw);
	
	public int updatePassword(@Param("loginNo") int loginNo, @Param("loginPw") String loginPw);
	
	public int update(UserDTO userDTO);
	
	public int delete(@Param("loginNo") int loginNo);
}
