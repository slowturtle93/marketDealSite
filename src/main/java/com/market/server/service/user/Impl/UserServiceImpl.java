package com.market.server.service.user.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.market.server.dto.user.UserDTO;
import com.market.server.error.exception.DuplicateIdException;
import com.market.server.mapper.user.UserMapper;
import com.market.server.service.user.UserService;
import com.market.server.utils.SHA256Util;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class UserServiceImpl implements UserService{
	
	@Autowired
	private final UserMapper userMapper;
	
	public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
	
	/**
	 * 회원의 정보를 가져온다.
	 * 
	 * @param loginId
	 * @return UesrDTO 
	 * 
	 */
	@Override
	public UserDTO getUserInfo(String loginId) {
		return userMapper.getUserInfo(loginId);
	}

	/**
	 * 회원가입 시 아이디 중복 체크를 진행한다.
	 * 
	 * @param id 중복체크를 진행 할 ID
	 * @return true : 중복된 아이디, false : 중복되지 않은 아이디
	 */
	@Override
	public boolean isDuplicatedId(String loginId) {
		return userMapper.isDuplicatedId(loginId) == 1;
	}

	/**
	 * 고객 회원가입 메서드 비밀번호를 암호화하여 세팅한다. MyBatis에서 insert return값은 성공시 1이 리턴된다. return값은 검사하여 null값이면
	 * true, null이 아닐시 insert에 실패한 것이니 false를 반환한다
	 * 
	 * @param userDTO 저장할 회원정보
	 */
	@Override
	public int insert(UserDTO userDTO) {
		boolean duplIdResult = isDuplicatedId(userDTO.getLoginId()); // 아이디 중복 체크
		if(duplIdResult) {
			throw new DuplicateIdException("중복된 아이디입니다.");
		}
		
		userDTO.setLoginPw(SHA256Util.encryptSHA256(userDTO.getLoginPw())); //비밀번호 암호화
		return userMapper.insert(userDTO);
	}

	/**
	 * 회원 로그인
	 * 
	 * @param loginId 로그인 아이디
	 * @param loginPw 로그인 비밀번호
	 * @return
	 */
	@Override
	public UserDTO login(String loginId, String loginPw) {
		String cryptoPassword = SHA256Util.encryptSHA256(loginPw); // 비밀번호 암호화
		UserDTO userDTO = userMapper.findByIdAndPassword(loginId, cryptoPassword);
		return userDTO;
	}
	
	
	/**
	 * 회원 비밀번호를 변경한다.
	 * 
	 * @param id 비밀번호를 변경할 아이디
	 * @param passwordAfterChange 변경할 비밀번호
	 * @return
	 */
	@Override
	public int updatePassword(int loginNo, String loginId, String passwordBeforeChange, String passwordAfterChange) {
		passwordMatch(loginId, passwordBeforeChange); //비밀번호 일치 여부 확인
		String cryptoPasswordAfterChange = SHA256Util.encryptSHA256(passwordAfterChange); //비밀번호 암호화
		
		int result = userMapper.updatePassword(loginNo, cryptoPasswordAfterChange);
		
		return result;
	}

	/**
	 * 회원 정보를 수정한다.
	 * 
	 * @param userDTO 변경할 회원 정보
	 * @return
	 * 
	 */
	@Override
	public void update(UserDTO userDTO) {
		int result = userMapper.update(userDTO);
		if(result != 1) {
			log.error("update User Info Error!");
			throw new RuntimeException("update User Info Error!");
		}
	}

	/**
	 * 회원 정보를 삭제한다. status 상태값을 DELETE로 UPDATE
	 * 
	 * @param loginNo 
	 * @return int
	 */
	@Override
	public int delete(int loginNo) {
		return userMapper.delete(loginNo);
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
		
		if(userMapper.findByIdAndPassword(loginId, password) == null) {
			throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
		}
	}

}
