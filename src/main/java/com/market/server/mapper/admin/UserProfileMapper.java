package com.market.server.mapper.admin;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.market.server.dto.Search;
import com.market.server.dto.user.UserDTO;

@Mapper
public interface UserProfileMapper {
	
	public List<UserDTO> getUserProfile(Search search);
}
