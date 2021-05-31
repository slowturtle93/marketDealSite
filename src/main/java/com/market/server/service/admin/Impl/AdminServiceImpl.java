package com.market.server.service.admin.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.market.server.dto.admin.AdminDTO;
import com.market.server.error.exception.DuplicateIdException;
import com.market.server.mapper.admin.AdminMapper;
import com.market.server.service.admin.AdminService;
import com.market.server.utils.SHA256Util;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class AdminServiceImpl implements AdminService{
	
	@Autowired
	private final AdminMapper adminMapper;
	
	public AdminServiceImpl(AdminMapper adminMapper) {
        this.adminMapper = adminMapper;
    }
	
	/**
	 * 관리자 로그인 아이디 중복 체크
	 * 
	 *  @param loginId
	 *  @return boolean
	 */
	@Override
	public boolean isDuplicatedId(String loginId) {
		return adminMapper.isDuplicatedId(loginId) == 1;
	}
	
	/**
	 * 관리자 등록
	 * 
	 * @param adminDTO
	 * @return int
	 */
	@Override
	public int insert(AdminDTO adminDTO) {
		boolean duplIdResult = isDuplicatedId(adminDTO.getLoginId());
		
		if(duplIdResult) {
			throw new DuplicateIdException("중복된 아이디입니다.");
		}
		
		adminDTO.setLoginPw(SHA256Util.encryptSHA256(adminDTO.getLoginPw())); // 비밀번호 암호화
		
		return adminMapper.insert(adminDTO);
	}

	/**
	 * 관리자 로그인
	 * 
	 * @param loginId
	 * @param loginPw
	 * @return AdminDTO
	 */
	@Override
	public AdminDTO login(String loginId, String loginPw) {
		String cryptoPassword = SHA256Util.encryptSHA256(loginPw); //비밀번호 암호화
		AdminDTO adminDTO = adminMapper.findByIdAndPassword(loginId, cryptoPassword);
		return adminDTO;
	}

	/**
	 * 관리자 비밀번호 변경
	 * 
	 * @param loingId
	 * @param passwordBeforeChange
	 * @param passwordAfterChange
	 * @return int
	 */
	@Override
	public int updatePassword(int adminNo, String loginId, String passwordBeforeChange, String passwordAfterChange) {
		passwordMatch(loginId, passwordBeforeChange); //비밀번호 일치여부 확인
		
		String cryptoPasswordAfterChange = SHA256Util.encryptSHA256(passwordAfterChange); //비밀번호 암호화
		int result = adminMapper.updatePassword(adminNo, cryptoPasswordAfterChange);
		
		return result;
	}

	/**
	 * 관리자 정보 변경
	 * 
	 * @param adminDTO
	 */
	@Override
	public void update(AdminDTO adminDTO) {
		int result = adminMapper.update(adminDTO);
		if(result != 1) {
			log.error("update admin Info Error!");
			throw new RuntimeException("update admin Info Error!");
		}
	}

	/**
	 * 관리자 정보 삭제
	 * 
	 * @param adminNo
	 * @return int
	 */
	@Override
	public int delete(int adminNo) {
		return adminMapper.delete(adminNo);
	}
	
	/**
	 * 로그인 아이디와 비밀번호 일치 여부 확인
	 * 
	 * @param loginId 
	 * @param loginPw 
	 */
	@Override
	public void passwordMatch(String loginId, String loginPw) {
		String password = SHA256Util.encryptSHA256(loginPw); //비밀번호 암호화
		
		if(adminMapper.findByIdAndPassword(loginId, password) == null) {
			throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
		}
	}
	
}
