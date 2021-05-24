package com.market.server.controller.user;

import javax.management.RuntimeErrorException;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.market.server.dto.user.UserDTO;
import com.market.server.service.user.UserService;
import com.market.server.utils.SessionUtil;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;


@RestController
@RequestMapping("/user/")
@Log4j2
public class UserController {
	
	@Autowired
	private UserService userService;

	/**
	 * 회원가입 시 ID 중복체크 여부 확인.
	 * 
	 * 회원가입 시 아이디의 중복체크를 진행한다. 아이디 중복체크는 회원가입 아이디 입력 후, 회원가입 요청시 두번 진행한다. 아이디 중복체크를 한 후 회원가입 버튼을 누를 때 까지
	 * 동일한 아이디로 누군가 가입한다면 PK Error가 발생되고 실제로 회원가입이 진행되지 않을 수 있기 때문에 회원가입을 눌렀을 때 한번 더 실행하는 것이 좋다.
	 *  
	 * @param loginId
	 * @return
	 */
	@GetMapping("duplicated/{id}")
	public boolean idCheck(@PathVariable @NotNull String id){
		boolean idDuplicated = userService.isDuplicatedId(id);
		return idDuplicated;
	}
	
	/**
	 * 유저가 입력한 정보로 회원가입을 진행한다. 보낸 값들 중 NULL값이 있으면 "NULL_ARGUMENT" 를 리턴한다. 회원가입 요청을 보내기 전 먼저 ID 중복체크를
	 * 진행한다. ID 중복시 403 상태코드를 반환한다. 회원가입 성공시 201 상태코드를 반환한다.
	 * 
	 * @param userDTO
	 * @return
	 */
	@PostMapping("signUp")
	public void register(@RequestBody @NotNull UserDTO userDTO){
	    if(UserDTO.hasNullDataBeforeSignup(userDTO)) {
	    	throw new NullPointerException("회원가입 시 필수 입력값을 모두 입력해야 합니다.");
	    }
	    userService.insert(userDTO);
	}
	
	/**
	 * 회원 로그인을 진행합니다. login 요청 시 loginId, loginPw 가 NULL 일 경우 NullPointerException을 throw 합니다.
	 * 
	 * @param loginRequest
	 * @param session
	 * @return
	 */
	@PostMapping("login")
	public ResponseEntity<LoginResponse> login(@RequestBody @NotNull UserLoginRequest loginRequest, HttpSession session){
		ResponseEntity<LoginResponse> responseEntity = null;
		LoginResponse loginResponse;
		UserDTO userDTO = userService.login(loginRequest.getLoginId(), loginRequest.getLoginPw());
		
		if(userDTO == null) { // ID, PW에 맞는 정보가 없을 때
			 loginResponse = LoginResponse.FAIL;
			 responseEntity = new ResponseEntity<LoginResponse>(loginResponse, HttpStatus.UNAUTHORIZED);
		}else if(UserDTO.Status.DEFAULT.equals(userDTO.getStatus())) { // 로그인 성공 시 세션에 ID 저장
			loginResponse = LoginResponse.success(userDTO);
			SessionUtil.setLoginId(session, loginRequest.getLoginNo(), loginRequest.getLoginId());
			responseEntity = new ResponseEntity<LoginResponse>(loginResponse, HttpStatus.OK);
		}else { // 예상하지 못한 오류일 경우
			log.error("login Error " + responseEntity);
			throw new RuntimeException("login Error!");
		}
			
		return responseEntity;
	}
	
	/**
	 * 회원 로그아웃
	 * 
	 * @param session
	 */
	@GetMapping("logout")
	public void logout(HttpSession session) {
		SessionUtil.logoutUser(session);
	}
	
	/**
	 * 회원의 비밀번호 정보 변경
	 * 
	 * @param passwordRequest
	 * @param session
	 */
	@PatchMapping("updatePW")
	public void UserUpdatePassword(@RequestBody @NotNull UpdatePasswordRequest passwordRequest, HttpSession session) {
		String passwordBeforeChange = passwordRequest.getPasswordBeforeChange();
	    String passwordAfterChange  = passwordRequest.getPasswordAfterChange();
	    String id = SessionUtil.getLoginId(session);
	    
	    if(passwordAfterChange == null || passwordBeforeChange == null) { // 유효성 검사
	    	throw new NullPointerException("패스워드를 입력해주세요.");
	    }else {
	    	userService.updatePassword(id, passwordBeforeChange, passwordAfterChange);
	    }
	}
	
	/**
	 * 회원 정보 변경
	 * 
	 * @param updateAddressRequest
	 * @param session
	 * @return
	 */
	@PatchMapping("update")
	public ResponseEntity<UpdateUserResponse> updataAddress(@RequestBody @NotNull UserDTO userDTO, HttpSession session){
		ResponseEntity<UpdateUserResponse> responseEntity = null;
		userDTO.setLoginId(SessionUtil.getLoginId(session));
		
		if(userDTO.getRoadFullAddr() == null || userDTO.getJibunAddr() == null || userDTO.getZipNo() == null) {
			//주소 정보 NULL인 경우
			responseEntity = new ResponseEntity<UpdateUserResponse>(UpdateUserResponse.EMPTY_ADDRESS, HttpStatus.BAD_REQUEST);
		}else if(userDTO.getAddrDetail() == null) {
			//주소 상세 정보 NULL인 경우
			responseEntity = new ResponseEntity<UpdateUserResponse>(UpdateUserResponse.EMPTY_ADDRESS_DETAIL, HttpStatus.BAD_REQUEST);
		}else if(userDTO.getHpNum() == null) {
			//핸드폰 정보 NULL인 경우
			responseEntity = new ResponseEntity<UpdateUserResponse>(UpdateUserResponse.EMPTY_HPNUMBER, HttpStatus.BAD_REQUEST);
		}else if(userDTO.getEmail() == null) {
			//이메일 정보 NULL인 경우
			responseEntity = new ResponseEntity<UpdateUserResponse>(UpdateUserResponse.EMPTY_EMAIL, HttpStatus.BAD_REQUEST);
		}else if(userDTO.getUserNm() == null) {
			//사용자명 정보 NULL인 경우
			responseEntity = new ResponseEntity<UpdateUserResponse>(UpdateUserResponse.EMPTY_USERNM, HttpStatus.BAD_REQUEST);
		}else {
			//수정 정보 이상 없을 시
			userService.update(userDTO);
			responseEntity = new ResponseEntity<UpdateUserResponse>(HttpStatus.OK);
		}
		
		return responseEntity;
	}
	
	
	
	@DeleteMapping("delete")
	public ResponseEntity<LoginResponse> deleteUser(@RequestBody UserDelete userDelete, HttpSession session){
		ResponseEntity<LoginResponse> responseEntity = null;
		String loginId = SessionUtil.getLoginId(session);
		userService.delete(loginId);
		
		return responseEntity;
	}
	
	
	/*======================= response 객체 ======================= */
	
	 @Getter
	 @AllArgsConstructor
	 @RequiredArgsConstructor
	 private static class LoginResponse {
	   enum LoginStatus {
	     SUCCESS, FAIL, DELETED
	   }

	   @NonNull
	   private LoginStatus result;
	   private UserDTO userDTO;

	   // success의 경우 memberInfo의 값을 set해줘야 하기 때문에 new 하도록 해준다.
	   private static final LoginResponse FAIL = new LoginResponse(LoginStatus.FAIL);
	   private static LoginResponse success(UserDTO userDTO) {
	     return new LoginResponse(LoginStatus.SUCCESS, userDTO);
	   }
	 }
	
	  @Getter
	  private static class UpdateUserResponse {
	    enum UpdateStatus {
	      EMPTY_ADDRESS, EMPTY_ADDRESS_DETAIL, EMPTY_HPNUMBER, EMPTY_EMAIL, EMPTY_USERNM 
	    }

	    @NonNull
	    private UpdateStatus message;

	    private static final UpdateUserResponse EMPTY_ADDRESS        = new UpdateUserResponse(UpdateStatus.EMPTY_ADDRESS);
	    private static final UpdateUserResponse EMPTY_ADDRESS_DETAIL = new UpdateUserResponse(UpdateStatus.EMPTY_ADDRESS_DETAIL);
	    private static final UpdateUserResponse EMPTY_HPNUMBER       = new UpdateUserResponse(UpdateStatus.EMPTY_HPNUMBER);
	    private static final UpdateUserResponse EMPTY_EMAIL          = new UpdateUserResponse(UpdateStatus.EMPTY_EMAIL);
	    private static final UpdateUserResponse EMPTY_USERNM         = new UpdateUserResponse(UpdateStatus.EMPTY_USERNM);

	    public UpdateUserResponse(UpdateStatus message) {
	      this.message = message;
	    }
	  }
	
	/*======================= response 객체 ======================= */
	
	  @Getter
	  @Setter
	  private static class UserLoginRequest {
	    @NonNull
	    private String loginNo;
	    @NonNull
	    private String loginId;
	    @NonNull
	    private String loginPw;
	  }
	  
	  @Getter
	  @Setter
	  private static class UpdatePasswordRequest {
	    @NonNull
	    private String passwordBeforeChange;
	    @NonNull
	    private String passwordAfterChange;
	  }
	  
	  @Setter
	  @Getter
	  private static class UserDelete {
	      @NonNull
	      private String loginId;
	      @NonNull
	      private String loginPw;
	  }
}
