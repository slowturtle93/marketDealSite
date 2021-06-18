package com.market.server.dao;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.Message;
import com.market.server.utils.RedisKeyFactory;

import lombok.extern.log4j.Log4j2;

@Repository
@Log4j2
public class FcmDao {

	  @Autowired
	  private RedisTemplate<String, Object> redisTemplate;
	  
	  @Autowired
	  private ObjectMapper objectMapper;

	  
	  @Value("${expire.fcm.user}")
	  private Long userTokenExpireSecond;
	  
	  
	  /**
	   * 고객이 발급받은 토큰을 저장한다.
	   * 
	   * @param memberId 고객 아이디
	   * @param token 토큰 정보
	   */
	  public void addUserToken(String userId, String token) {
		  
		  String key = RedisKeyFactory.generateFcmUserKey(userId);
		  redisTemplate.watch(key);
		  
		  try {
			  
			  if(getUserTokens(userId).contains(token)) {
				  return;
			  }
			  
			  redisTemplate.multi();
			  
			  redisTemplate.opsForList().rightPush(key, token);
			  redisTemplate.expire(key, userTokenExpireSecond, TimeUnit.SECONDS);
			  
			  redisTemplate.exec();
			  
		  }catch (Exception e) {
			  log.error("Redis Add User Token ERROR! key : {}", key);
			  log.error("ERROR Info : {}", e.getMessage());
			  redisTemplate.discard();
			  throw new RuntimeException("Cannot add User token key : " + key + ", ERROR Info " + e.getMessage());
		  }
	  }
	  
	  /**
	   * 해당 고객의 토큰 리스트를 조회한다.
	   * 
	   * @param memberId 고객 아이디
	   * @return
	   */
	  public List<String> getUserTokens(String userId) {
		  
		  return redisTemplate.opsForList().range(RedisKeyFactory.generateFcmUserKey(userId), 0, -1)
				  .stream()
				  .map(e -> objectMapper.convertValue(e, String.class))
				  .collect(Collectors.toList());
	  }
	  
	  
	  /**
	   * 에러 시 에레내용 푸시
	   * 
	   * @param userId
	   * @param messages
	   */
	  public void addUserErrorPush(String userId , List<Message> messages) {
		  redisTemplate.opsForList().rightPush(RedisKeyFactory.generateFcmUserErrorKey(userId), messages);
	  }

}
