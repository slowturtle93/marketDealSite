package com.market.server.dao;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.market.server.dto.chat.ChatMessageDTO;
import com.market.server.utils.RedisKeyFactory;

import lombok.extern.log4j.Log4j2;

@Repository
@Log4j2
public class ChatDao {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Value("${expire.chat}")
	private Long chatExpireSecond;
	
	/**
	 * 발송된 message를 Redis에 저장한다.
	 * 
	 * @param chatRoom
	 * @param key
	 * @param userId
	 */
	public void addChatMessage(ChatMessageDTO message, String simpSessionId) {
		String key = RedisKeyFactory.generateChatUserKey(simpSessionId);

		redisTemplate.watch(key);
		
		try {
			redisTemplate.multi();
			redisTemplate.opsForList().rightPush(key, message);
			redisTemplate.expire(key, chatExpireSecond, TimeUnit.SECONDS);
			
			redisTemplate.exec();
			
		}catch (Exception e) {
			redisTemplate.discard(); // 트랜잭션 종료시 unwatch()가 호출된다
		    System.out.println(e.getMessage());
		    throw e;
		}
	}
	
	/**
	 * Redis에서 message 내용을 가져온다.
	 * 
	 * @param userId
	 * @return
	 */
	public List<ChatMessageDTO> getChatMessage(String simpSessionId){
		return redisTemplate.opsForList().range(RedisKeyFactory.generateChatUserKey(simpSessionId), 0, -1)
				  .stream()
				  .map(e -> objectMapper.convertValue(e, ChatMessageDTO.class))
				  .collect(Collectors.toList());
	}
	
	/**
	 * message 내용을 Redis에서 삭제한다.
	 * 
	 * @param userId
	 */
	public void delChatMessage(String simpSessionId) {
		redisTemplate.delete(RedisKeyFactory.generateChatUserKey(simpSessionId));
	}
	
	
}
