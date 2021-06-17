package com.market.server.dao;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class ProductDao {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	/**
	 * 상품의 count 정보를 저장한다.
	 * 
	 * @param gubun
	 * @param itemCd
	 */
	public void addProductCntInfo(String key, String itemCd, int Cnt) {
		redisTemplate.opsForHash().put(key, itemCd, Cnt);
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
