package com.market.server.dao;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.market.server.dto.order.OrderDTO;
import com.market.server.utils.RedisKeyFactory;

@Repository
public class OrderLogDao {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Value("${expire.orderLog}")
	private long orderLogExpireSecond;
	
	
	/**
	 * redis list에 변경된 주문상태를 추가한다.
	 * RedisKeyFactory로 주문코드, 내부 키를 이용해 키를 생산산 후 주문정보이력을 저장시킨다.
	 * 
	 * @param orderDTO
	 * @param orderCd
	 */
	public void addOrder(OrderDTO orderDTO) {
		final String key = RedisKeyFactory.ORDER_KEY;
		
		redisTemplate.watch(key);
		
		try {
			redisTemplate.multi();
			redisTemplate.opsForList().rightPush(key, orderDTO);
			redisTemplate.expire(key, orderLogExpireSecond, TimeUnit.SECONDS);
			
			redisTemplate.exec();
			
		}catch (Exception e) {
			redisTemplate.discard(); // 트랜잭션 종료시 unwatch()가 호출된다
		    System.out.println(e.getMessage());
		    throw e;
		}
	}
	
	/**
	 * 주문이력 정보를 모두 조회한다.
	 * 
	 * @param key
	 * @return
	 */
	public List<OrderDTO> findAll(String key) {
		List<OrderDTO> orderList = redisTemplate.opsForList()
			.range(key, 0, -1)
			.stream()
			.map(item -> objectMapper.convertValue(item, OrderDTO.class))
			.collect(Collectors.toList());
				
	    return orderList;
    }
	
	/**
	 * 주문이력을 전부 삭제한다. rddis에서 해당 키에있는 내용을 모두 삭제한다.
	 * 
	 * @param key
	 * @return
	 */
	public boolean deleteByOrder(String key) {
		return redisTemplate.delete(key);
	}
}
