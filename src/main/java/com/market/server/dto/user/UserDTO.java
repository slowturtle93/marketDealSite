package com.market.server.dto.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDTO {
	public enum Status {
		DEFAULT, DELETED
    }
	
	private int loginNo;         // 사용자번호
	private String loginId;      // 로그인아이디
	private String userNm;       // 사용자명
	private String loginPw;      // 로그인비밀번호
	private String hpNum;        // 전화번호
	private String email;        // 이메일
	private String userLevel  ;  // 회원등급
	private String roadFullAddr; // 도로명주소
	private String jibunAddr;    // 지번주소
	private String zipNo;        // 우편번호
	private String addrDetail;   // 상세주소
	private String nickName;     // 닉네임
	private int    point;        // 포인트
	private String regDttm;      // 등록일시
	private String updDttm;      // 수정일시
	private Status status;       // 상태
	
	public UserDTO(){}
	
	public UserDTO(String loginId, String loginPw, String userNm, String hpNum, String email, String roadFullAddr,
			       String jibunAddr, String zipNo, String addrDetail, String regDttm, String updDttm, Status status) {
		this.loginId      = loginId;
		this.loginPw      = loginPw;
		this.userNm       = userNm;
		this.hpNum        = hpNum;
		this.email        = email;
		this.roadFullAddr = roadFullAddr;
		this.jibunAddr    = jibunAddr;
		this.zipNo        = zipNo;
		this.addrDetail   = addrDetail;
		this.regDttm      = regDttm;
		this.updDttm      = updDttm;
		this.status       = status;
	}
	
	/*
	 * 회원가입 전 필수 데이터중 null 값 체크
	 * null값이 존재하면 회원가입 진행 불가능인 false 반환
	 * 검사 후 이상이 없다면 true 반환합니다.
	 * 
	 * @param UserDTO
	 * @return
	 */
	public static boolean hasNullDataBeforeSignup(UserDTO userDTO) {
		return userDTO.getLoginId() == null || userDTO.getLoginPw()      == null || userDTO.getHpNum()     == null ||
			   userDTO.getEmail()   == null || userDTO.getRoadFullAddr() == null || userDTO.getJibunAddr() == null ||
			   userDTO.getZipNo()   == null || userDTO.getAddrDetail()   == null || userDTO.getUserNm()    == null;
	}
}
