package com.market.server.service.admin.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.market.server.dto.Search;
import com.market.server.dto.user.UserDTO;
import com.market.server.mapper.admin.UserProfileMapper;
import com.market.server.service.admin.UserProfileService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class UserProfileServiceImpl implements UserProfileService{
	
	@Autowired
	private final UserProfileMapper userProfileMapper;
	
	public UserProfileServiceImpl(UserProfileMapper userProfileMapper) {
        this.userProfileMapper = userProfileMapper;
    }

	@Override
	public List<UserDTO> getUserProfile(Search search) {
		return userProfileMapper.getUserProfile(search);
	}
	
}
