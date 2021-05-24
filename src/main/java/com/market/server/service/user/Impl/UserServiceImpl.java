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
	private UserMapper userMapper;

	/**
	 * 회원가입 시 아이디 중복 체크를 진행한다.
	 * 
	 * @param id 중복체크를 진행 할 ID
	 * @return true : 중복된 아이디, false : 중복되지 않은 아이디
	 */
	@Override
	public boolean isDuplicatedId(String id) {
		return userMapper.isDuplicatedId(id) == 1;
	}

	/**
	 * 고객 회원가입 메서드 비밀번호를 암호화하여 세팅한다. MyBatis에서 insert return값은 성공시 1이 리턴된다. return값은 검사하여 null값이면
	 * true, null이 아닐시 insert에 실패한 것이니 false를 반환한다
	 * 
	 * @param userDTO 저장할 회원정보
	 */
	@Override
	public void insert(UserDTO userDTO) {
		boolean duplIdResult = isDuplicatedId(userDTO.getLoginId());
		if(duplIdResult) {
			throw new DuplicateIdException("중복된 아이디입니다.");
		}
		
		userDTO.setLoginPw(SHA256Util.encryptSHA256(userDTO.getLoginPw()));
		int result = userMapper.insert(userDTO);
		
		if(result != 1) {
			log.error("insertMember ERROR! {}", userDTO);
			throw new RuntimeException(
				"insertUser Error 회원가입 메서드를 확인해주세요.\n" + "params : " + userDTO);
		}
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
		String cryptoPassword = SHA256Util.encryptSHA256(loginPw);
		UserDTO userDTO = userMapper.login(loginId, cryptoPassword);
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
	public void updatePassword(String id, String passwordBeforeChange, String passwordAfterChange) {
		String cryptoPasswordBeforeChange = SHA256Util.encryptSHA256(passwordBeforeChange);
		
		if(userMapper.login(id, cryptoPasswordBeforeChange) == null) {
			throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
		}
		
		String cryptoPasswordAfterChange = SHA256Util.encryptSHA256(passwordAfterChange);
		int result = userMapper.updatePassword(id, cryptoPasswordAfterChange);
		if(result != 1) {
			log.error("update Password Error id : {}, pw : {}", id, passwordAfterChange);
			throw new RuntimeException("update Password Error!");
		}
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
			log.error("update User address Error!");
			throw new RuntimeException("update User address Error!");
		}
	}

	@Override
	public void delete(String loginId) {
		int result = userMapper.delete(loginId);
		if(result != 1) {
			log.error("delete User Error! id : {}", loginId);
			throw new RuntimeException("delete User Error!");
		}
	}
	
}
