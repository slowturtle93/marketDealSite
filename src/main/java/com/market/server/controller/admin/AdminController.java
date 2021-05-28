package com.market.server.controller.admin;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.market.server.aop.LoginCheck;
import com.market.server.aop.LoginCheck.UserType;
import com.market.server.dto.admin.AdminDTO;
import com.market.server.service.admin.Impl.AdminServiceImpl;
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
public class AdminController {
	
	private final AdminServiceImpl adminService;
	
	@Autowired
    public AdminController(AdminServiceImpl adminService) {
        this.adminService = adminService;
    }
	
	/**
	 * ID 중복체크 메서드
	 *  
	 * @param loginId
	 * @return
	 */
	@GetMapping("duplicated/{id}")
	public boolean idCheck(@PathVariable @NotNull String id){
		return adminService.isDuplicatedId(id);
	}
	
	/**
	 * 관리자 등록 메서드
	 * ResponseEntity는 HTTP 요청(Request) 또는 응답(Response)에 해당하는 HttpHeader와 HttpBody를 포함하는 클래스
	 * 
	 * @param adminDTO
	 * @return
	 */
	@PostMapping("register")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<AdminResultResponse> register(@RequestBody @NotNull AdminDTO adminDTO){
		ResponseEntity<AdminResultResponse> responseEntity = null; // 반환값
		AdminResultResponse adminResultResponse;                   // 상태값
		
		//관리자 등록 시 필수값 검사
	    if(AdminDTO.hasNullDataBeforeRegister(adminDTO)) {
	    	throw new NullPointerException("관리자 등록 시 필수 입력값을 모두 입력해야 합니다.");
	    }
	    
	    //관리자 등록
	    int result = adminService.insert(adminDTO);
	    
	    if(result == 1) { // 관리자 등록 성공인 경우
	    	adminResultResponse = AdminResultResponse.SUCCESS;
		    responseEntity      = new ResponseEntity<AdminResultResponse>(adminResultResponse, HttpStatus.OK);
	    }else { // 관리자 등록 실패인 경우
	    	log.error("insertUser ERROR! {}", adminDTO);
	    	adminResultResponse = AdminResultResponse.FAIL;
		    responseEntity      = new ResponseEntity<AdminResultResponse>(adminResultResponse, HttpStatus.UNAUTHORIZED);
	    }
	    
	    return responseEntity;
	}
	
	/**
	 * 관리자 로그인 메서드 
	 * 
	 * @param loginRequest
	 * @param session
	 * @return
	 */
	@PostMapping("login")
	public ResponseEntity<AdminLoginResponse> login(@RequestBody @NotNull AdminLoginRequest adminLoginRequest, HttpSession session){
		ResponseEntity<AdminLoginResponse> responseEntity = null; // 반환값
		AdminLoginResponse loginResponse;                         // 상태값
		// 사용자 정보 조회
		AdminDTO adminDTO = adminService.login(adminLoginRequest.getLoginId(), adminLoginRequest.getLoginPw());
		
		if(adminDTO == null) { // ID, PW에 맞는 정보가 없을 때
			 loginResponse  = AdminLoginResponse.FAIL;
			 responseEntity = new ResponseEntity<AdminLoginResponse>(loginResponse, HttpStatus.UNAUTHORIZED);
		}else if(AdminDTO.Status.DEFAULT.equals(adminDTO.getStatus())) { // 로그인 성공 시 세션에 ID 저장
			loginResponse = AdminLoginResponse.success(adminDTO);
			SessionUtil.setLoginAdminInfo(session, adminLoginRequest.getAdminNo(), adminLoginRequest.getLoginId());
			responseEntity = new ResponseEntity<AdminLoginResponse>(loginResponse, HttpStatus.OK);
		}else { // 예상하지 못한 오류일 경우
			log.error("login Error " + responseEntity);
			throw new RuntimeException("login Error!");
		}
			
		return responseEntity;
	}
	
	/**
	 * 관리자 로그아웃
	 * 
	 * @param session
	 */
	@GetMapping("logout")
	public void logout(HttpSession session) {
		SessionUtil.logoutAdminInfo(session);
	}
	
	/**
	 * 관리자 비밀번호 정보 변경
	 * 
	 * @param passwordRequest
	 * @param session
	 */
	@PatchMapping("password")
	@LoginCheck(type = UserType.ADMIN)
	public ResponseEntity<AdminResultResponse> UserUpdatePassword(@RequestBody @NotNull UpdatePasswordRequest passwordRequest, 
			                                                     HttpSession session) {
		ResponseEntity<AdminResultResponse> responseEntity = null; // 반환값
		AdminResultResponse adminResultResponse;                   // 상태값
		String passwordBeforeChange = passwordRequest.getPasswordBeforeChange(); // 이전 비밀번호
	    String passwordAfterChange  = passwordRequest.getPasswordAfterChange();  // 변경할 비밀번호
	    String loginId = SessionUtil.getLoginAdminId(session); // 세션에 저장된 로그인 아이디
	    int adminNo    = SessionUtil.getLoginAdminNo(session); // 세선에 저장된 관리자 번호
	    
	    // 유효성 검사
	    if(passwordAfterChange == null || passwordBeforeChange == null) { 
	    	throw new NullPointerException("패스워드를 입력해주세요.");
	    }
	    
	    //비밀번호 변경
	    int result = adminService.updatePassword(adminNo, loginId, passwordBeforeChange, passwordAfterChange);
	    
	    if(result == 1) { // 비밀번호 변경이 성공인 경우
	    	adminResultResponse = AdminResultResponse.SUCCESS;
		    responseEntity      = new ResponseEntity<AdminResultResponse>(adminResultResponse, HttpStatus.OK);
	    }else { // 비밀번호 변경이 실패인 경우
	    	log.error("update Password Error id : {}, pw : {}", loginId, passwordAfterChange);
	    	adminResultResponse = AdminResultResponse.FAIL;
		    responseEntity      = new ResponseEntity<AdminResultResponse>(adminResultResponse, HttpStatus.UNAUTHORIZED);
	    }
	    
	    return responseEntity;
	}
	
	/**
	 * 관리자 정보 변경
	 * 
	 * @param updateAddressRequest
	 * @param session
	 * @return
	 */
	@PatchMapping("update")
	@LoginCheck(type = UserType.ADMIN)
	public ResponseEntity<UpdateAdminResponse> updataAddress(@RequestBody @NotNull AdminDTO adminDTO, HttpSession session){
		ResponseEntity<UpdateAdminResponse> responseEntity = null; // 반환값
		adminDTO.setAdminNo(SessionUtil.getLoginAdminNo(session)); // 관리자 번호 Set
		
		if(adminDTO.getAdminNm() == null) {
			//관리자명 NULL인 경우
			responseEntity = new ResponseEntity<UpdateAdminResponse>(UpdateAdminResponse.EMPTY_ADMINNM, HttpStatus.BAD_REQUEST);
		}else if(adminDTO.getEmail() == null) {
			//이메일 정보 NULL인 경우
			responseEntity = new ResponseEntity<UpdateAdminResponse>(UpdateAdminResponse.EMPTY_EMAIL, HttpStatus.BAD_REQUEST);
		}else if(adminDTO.getTelNum() == null) {
			//전화번호 NULL인 경우
			responseEntity = new ResponseEntity<UpdateAdminResponse>(UpdateAdminResponse.EMPTY_TELNUM, HttpStatus.BAD_REQUEST);
		}else if(adminDTO.getHpNum() == null){
			//핸드폰 번호 NULL인 경우
			responseEntity = new ResponseEntity<UpdateAdminResponse>(UpdateAdminResponse.EMPTY_HPNUM, HttpStatus.BAD_REQUEST);
		}else if(adminDTO.getPosn() == null){
			//직책 정보 NULL인 경우
			responseEntity = new ResponseEntity<UpdateAdminResponse>(UpdateAdminResponse.EMPTY_POSN, HttpStatus.BAD_REQUEST);
		}else if(adminDTO.getDept() == null){
			//부서 정보 NULL인 경우
			responseEntity = new ResponseEntity<UpdateAdminResponse>(UpdateAdminResponse.EMPTY_DEPT, HttpStatus.BAD_REQUEST);
		}else{
			//수정 정보 이상 없을 시
			adminService.update(adminDTO);
			responseEntity = new ResponseEntity<UpdateAdminResponse>(UpdateAdminResponse.SUCCESS,  HttpStatus.OK);
		}
		
		return responseEntity;
	}
	
	
	/**
	 * 관리자 정보 삭제
	 * 
	 * @param adminDelete
	 * @param session
	 * @return
	 */
	@DeleteMapping("delete")
	@LoginCheck(type = UserType.ADMIN)
	public ResponseEntity<AdminResultResponse> deleteUser(@RequestBody AdminDelete adminDelete, HttpSession session){
		ResponseEntity<AdminResultResponse> responseEntity = null; // 반환값
		AdminResultResponse adminResultResponse;                   // 상태값
		
		int adminNo    = SessionUtil.getLoginAdminNo(session); // 세션에 저장된 관리자 번호
		String loginId = SessionUtil.getLoginAdminId(session); // 세션에 저장된 관리자 아이디
		
		//삭제 전 비밀번호 확인
		adminService.passwordMatch(loginId, adminDelete.getLoginPw());
		
		int result     = adminService.delete(adminNo);         // 관리자 정보 삭제 
		
		if(result == 1) { // 관리자 정보 삭제 성공인 경우
			logout(session); //세션 로그아웃
			adminResultResponse = AdminResultResponse.SUCCESS;
		    responseEntity      = new ResponseEntity<AdminResultResponse>(adminResultResponse, HttpStatus.OK);
		}else { // 관리자 정보 삭제 실패인 경우
			log.error("delete User Error! id : {}", SessionUtil.getLoginAdminId(session));
			adminResultResponse = AdminResultResponse.FAIL;
		    responseEntity      = new ResponseEntity<AdminResultResponse>(adminResultResponse, HttpStatus.NOT_FOUND);
		}
		
		return responseEntity;
	}
	
	
	/*======================= response 객체 ======================= */
	
	 @Getter                   
	 @AllArgsConstructor       // 필드값을 모두 포함한 생성자를 자동 생성해준다.
	 @RequiredArgsConstructor  // 생성자를 자동 생성하지만, 필드명 위에 @nonNull로 표기된 경우만 생성자의 매개변수로 받는다.
	 private static class AdminLoginResponse {
	   enum LoginStatus {
	     SUCCESS, FAIL, DELETED
	   }

	   @NonNull //null을 허용하지 하지 않음
	   private LoginStatus result;
	   private AdminDTO adminDTO;

	   // success의 경우 memberInfo의 값을 set해줘야 하기 때문에 new 하도록 해준다.
	   private static final AdminLoginResponse FAIL = new AdminLoginResponse(LoginStatus.FAIL);
	   private static AdminLoginResponse success(AdminDTO adminDTO) {
	     return new AdminLoginResponse(LoginStatus.SUCCESS, adminDTO);
	   }
	 }
	 
	 @Getter
     private static class AdminResultResponse{
    	 enum AdminResultStatus{
    		 SUCCESS, FAIL
    	 }
    	 
    	 @NonNull
    	 AdminResultStatus message;
    	 
    	 private static final AdminResultResponse SUCCESS = new AdminResultResponse(AdminResultStatus.SUCCESS);
    	 private static final AdminResultResponse FAIL    = new AdminResultResponse(AdminResultStatus.FAIL);
    	 
    	 public AdminResultResponse(AdminResultStatus message) {
   	      this.message = message;
   	    }
     }
	 
	 @Getter
	 private static class UpdateAdminResponse {
	   enum UpdateStatus {
		   SUCCESS, EMPTY_ADMINNM, EMPTY_EMAIL, EMPTY_TELNUM, EMPTY_HPNUM, EMPTY_POSN, EMPTY_DEPT  
	   }
     
	 @NonNull
	 private UpdateStatus message;

	 private static final UpdateAdminResponse SUCCESS       = new UpdateAdminResponse(UpdateStatus.SUCCESS);
	 private static final UpdateAdminResponse EMPTY_ADMINNM = new UpdateAdminResponse(UpdateStatus.EMPTY_ADMINNM);
	 private static final UpdateAdminResponse EMPTY_EMAIL   = new UpdateAdminResponse(UpdateStatus.EMPTY_EMAIL);
	 private static final UpdateAdminResponse EMPTY_TELNUM  = new UpdateAdminResponse(UpdateStatus.EMPTY_TELNUM);
	 private static final UpdateAdminResponse EMPTY_HPNUM   = new UpdateAdminResponse(UpdateStatus.EMPTY_HPNUM);
	 private static final UpdateAdminResponse EMPTY_POSN    = new UpdateAdminResponse(UpdateStatus.EMPTY_POSN);
	 private static final UpdateAdminResponse EMPTY_DEPT    = new UpdateAdminResponse(UpdateStatus.EMPTY_DEPT);
 
	 public UpdateAdminResponse(UpdateStatus message) {
		 this.message = message;
		 }
	 }
	
	/*======================= response 객체 ======================= */
	
	  @Getter
	  @Setter
	  private static class AdminLoginRequest {
	    @NonNull
	    private String adminNo;
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
	  private static class AdminDelete {
	      @NonNull
	      private String loginPw;
	  }
	
}
