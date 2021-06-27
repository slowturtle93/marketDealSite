package com.market.server.service.push;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.market.server.dao.FcmDao;
import com.market.server.dto.push.PushMessage;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class PushServiceImpl{
	
	
	/*
	 * 하나의 아이디로 여러 기기에서 접속할 수 있기때문에 아이디와 토큰은 1 : N의 관계를 가진다.
	 * 그렇기 때문에 토큰을 List형태로 저장하였다.
	 */
	  
	@Autowired
	private FcmDao fcmDao;
	  
	@Value("${fcm.key.path}")
	private String FCM_PRIVATE_KEY_PATH;

	/**
	 * FCM 기본 설정을 진행한다.
	 */
	@PostConstruct
	public void init() {
	    try {
	    	FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredentials(
							GoogleCredentials
							.fromStream(new ClassPathResource(FCM_PRIVATE_KEY_PATH).getInputStream()))
					.build();
			  
            if(FirebaseApp.getApps().isEmpty()) {
				FirebaseApp.initializeApp(options);
				log.info("Firebase application has been initialized");
			}
				  
		}catch (Exception e) {
		    log.error(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 고객 아이디로 로그인한 기기에 메세지를 보낸다.
	 * 
	 * @param messageInfo 푸시 정보
	 * @param userId 고객 아이디
	 */
	@Async("asyncTask")
	public void sendMessageToUser(PushMessage messageInfo, String userId) {
		List<String> tokens = fcmDao.getUserTokens(userId);
		
		if(tokens.size() == 0) { // token 개수가 0이면 오류 발생
			log.debug("해당 회원의 FCM 토큰이 없습니다. 회원 아이디 : {}, 메세지 정보 : {}", userId, messageInfo);
			return;
		}
		
		List<Message> message = tokens.stream().map(token -> Message.builder()
				.putData("title", messageInfo.getTitle())
				.putData("message", messageInfo.getMessage())
				.putData("tiem", LocalDateTime.now().toString())
				.setToken(token)
				.build()).collect(Collectors.toList());
		
		BatchResponse response;
		
		try {
			response = FirebaseMessaging.getInstance().sendAll(message);
			log.info("Sent message : " + response);
		}catch (FirebaseMessagingException e) {
			log.error("cannot send to user push message. error info : {}", e.getMessage());
			addErrorUserPush(userId, message);
		}
	}
	
	/**
	 * 회원 토큰정보를 저장한다.
	 * 동일 토큰이 존재할시 추가하지 않는다.
	 * 
	 * @param userId 고객 아이디
	 * @param token 토큰 정보
	 */
	public void addUserToken(String userId, String token) {
		fcmDao.addUserToken(userId, token);
	}
	  
	  
	/**
	 * 고객 토큰 정보를 조회한다.
	 * 
	 * @param userId 고객 아이디
	 * @return
	 */
	public List<String> getUserTokens(String userId) {
		return fcmDao.getUserTokens(userId);
	}
	  
	/**
	 * 에러 정보 푸쉬
	 *   
	 * @param userId
	 * @param messages
	 */
	public void addErrorUserPush(String userId, List<Message> messages) {
		fcmDao.addUserErrorPush(userId, messages);
	}
	  
}
