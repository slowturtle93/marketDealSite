package com.market.server.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.market.server.aop.LoginCheck;
import com.market.server.aop.LoginCheck.UserType;
import com.market.server.dto.Search;
import com.market.server.dto.user.UserDTO;
import com.market.server.service.admin.Impl.UserProfileServiceImpl;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;



@RestController
@RequestMapping("/admin/")
@Log4j2
public class UserProfileController {
	
	private final UserProfileServiceImpl userProfileService;
	
	@Autowired
    public UserProfileController(UserProfileServiceImpl userProfileService) {
        this.userProfileService = userProfileService;
    }
	
	/**
	 * 사용자 조회 메서드
	 * 
	 * 조회조건
	 * - 전체조회, 회원 아디이, 상태, 등급, 등록일자
	 *  
	 * @param loginId
	 * @return
	 */
	@GetMapping("userProfile")
	@LoginCheck(type = UserType.ADMIN)
	public List<UserDTO> getUserProfile(@RequestBody UserProfileRequest userProfileRequest){
		Search search = new Search();
		// 조회조건
		search.add("loginId",     userProfileRequest.getLoginId());     // 로그인아이디
		search.add("status",      userProfileRequest.getStatus());      // 상태
		search.add("userLevel",   userProfileRequest.getUserLevel());   // 회원등급
		search.add("fromRegDttm", userProfileRequest.getFromRegDttm()); // 시작일자
		search.add("toRegDttm",   userProfileRequest.getToRegDttm());   // 종료일자
		
		//페이지
		search.add("pg",          userProfileRequest.getPg());   // 현재페이제
		search.add("pgSz",        userProfileRequest.getPgSz()); // 한 페이지당 Row 수
		search.setRow();                                          // 페이지 계산
		
		List<UserDTO> userInfo = userProfileService.getUserProfile(search);
		
		return userInfo;
		
	}
	
	
	/*======================= request 객체 ======================= */
	
	@Getter
	@Setter
	private static class UserProfileRequest {
		private String loginId;
		private String status;
		private String userLevel;
		private String fromRegDttm;
		private String toRegDttm;
		@NonNull
		private int pg;
		@NonNull
		private int pgSz;
	}
	
}
