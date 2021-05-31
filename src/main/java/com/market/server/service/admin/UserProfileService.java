package com.market.server.service.admin;

import java.util.List;

import com.market.server.dto.Search;
import com.market.server.dto.user.UserDTO;

public interface UserProfileService {
	
	public List<UserDTO> getUserProfile(Search search);
	
}
