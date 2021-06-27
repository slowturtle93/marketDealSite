package com.market.server.dao;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class ProductDao {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Value("${expire.goods.cnt}")
	private Long goodsCntExpireSecond;
	
	/**
	 * 상품의 count 정보를 저장한다.
	 * 
	 * @param gubun
	 * @param itemCd
	 */
	public void addProductCntInfo(String key, String itemCd, int Cnt) {
		
		redisTemplate.watch(key);
		
		try {
			redisTemplate.multi();
			redisTemplate.opsForHash().put(key, itemCd, Cnt);
			redisTemplate.expire(key, goodsCntExpireSecond, TimeUnit.SECONDS);
			
			redisTemplate.exec();
			
		}catch (Exception e) {
			redisTemplate.discard(); // 트랜잭션 종료시 unwatch()가 호출된다
		    System.out.println(e.getMessage());
		    throw e;
		}
		
	}
	
	/**
	 * 상품별 카운트 정보를 조회한다.
	 * 
	 * @param key
	 * @param subKey
	 * @return
	 */
	public int getProductCntInfo(String key, String subKey) {
		Integer count = objectMapper.convertValue(redisTemplate.opsForHash().get(key, subKey),Integer.class);
		return count == null ? 0 : count;
	}
	
	/**
	 * 상품의 count 정보를 조회한다.
	 * 
	 * @param key
	 * @return
	 */
	public Map<Object, Object> getProductCntMap(String key) {
		return redisTemplate.opsForHash().entries(key);
    }
	
	/**
	 * 상품의 count 정보를 삭제한다.
	 * 
	 * @param key
	 */
	public void delProductCntInfo(String key) {
		redisTemplate.delete(key);
	}
	
}
