package com.market.server.controller.admin;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.market.server.aop.LoginCheck;
import com.market.server.aop.LoginCheck.UserType;
import com.market.server.dto.Search;
import com.market.server.dto.admin.AdminDTO;
import com.market.server.dto.user.UserDTO;
import com.market.server.service.admin.Impl.AdminServiceImpl;
import com.market.server.service.admin.Impl.UserProfileServiceImpl;
import com.market.server.utils.SessionUtil;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
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
	public List<UserDTO> getUserProfile(@RequestBody UserProfileResponse userProfileResponse){
		Search search = new Search();
		// 조회조건
		search.add("loginId",     userProfileResponse.getLoginId());     // 로그인아이디
		search.add("status",      userProfileResponse.getStatus());      // 상태
		search.add("userLevel",   userProfileResponse.getUserLevel());   // 회원등급
		search.add("fromRegDttm", userProfileResponse.getFromRegDttm()); // 시작일자
		search.add("toRegDttm",   userProfileResponse.getToRegDttm());   // 종료일자
		
		List<UserDTO> userInfo = userProfileService.getUserProfile(search);
		
		return userInfo;
		
	}
	
	
	/*======================= response 객체 ======================= */
	
	 
	
	/*======================= response 객체 ======================= */
	
	@Getter
	@Setter
	private static class UserProfileResponse {
		private String loginId;
		private String status;
		private String userLevel;
		private String fromRegDttm;
		private String toRegDttm;
		
	}
	
}
