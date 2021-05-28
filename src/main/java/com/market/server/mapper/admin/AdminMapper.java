package com.market.server.mapper.admin;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.market.server.dto.admin.AdminDTO;

@Mapper
public interface AdminMapper {
	
	int isDuplicatedId(String loginId);
	
	public int insert(AdminDTO adminDTO);
	
	public AdminDTO findByIdAndPassword(@Param("loginId") String loginId, @Param("loginPw") String loginPw);
	
	public AdminDTO login(@Param("loginId") String loginId, @Param("loginPw") String loginPw);
	
	public int updatePassword(@Param("adminNo") int adminNo, @Param("loginPw") String loginPw);
	
	public int update(AdminDTO adminDTO);
	
	public int delete(@Param("adminNo") int adminNo);
}
