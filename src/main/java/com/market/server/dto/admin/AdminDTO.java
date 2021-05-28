package com.market.server.dto.admin;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AdminDTO {
	public enum Status {
		DEFAULT, DELETED
    }
	
	private int adminNo;    // 관리자일련번호
	private String loginId; // 로그인아이디
	private String loginPw; // 로그인비밀번호
	private String adminNm; // 관리자명
	private String email;   // 이메일
	private String telNum;  // 전화번호
	private String hpNum;   // 휴대폰번호
	private String posn;    // 직책
	private String dept;    // 부서
	private String regDttm; // 등록일시
	private String updDttm; // 수정일시
	private Status status;  // 상태
	
	public AdminDTO(){}
	
	public AdminDTO(String loginId, String loginPw, String adminNm, String email, String telNum,
			String hpNum, String posn, String dept, Status status) {
		this.loginId = loginId;
		this.loginPw = loginPw;
		this.adminNm = adminNm;
		this.email   = email;
		this.telNum  = telNum;
		this.hpNum   = hpNum;
		this.posn    = posn;
		this.dept    = dept;
		this.status  = status;
	}
	
	/**
	 * 관리자 등록 시  필수 데이터 NULL값 체크
	 * NULL값이 존재하면 관리자 등록 불가능인 false 반환 검사 후 이상이 없다면 true 반환
	 * 
	 * @param adminDTO
	 * @return
	 */
	public static boolean hasNullDataBeforeRegister(AdminDTO adminDTO) {
		return adminDTO.getLoginId() == null || adminDTO.getLoginPw() == null || adminDTO.getAdminNm() == null ||
			   adminDTO.getEmail()   == null || adminDTO.getTelNum()  == null || adminDTO.getHpNum()   == null ||
			   adminDTO.getPosn()    == null || adminDTO.getDept()    == null;
	}
	
	
}
