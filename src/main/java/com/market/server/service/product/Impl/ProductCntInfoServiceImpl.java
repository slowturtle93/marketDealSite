package com.market.server.service.product.Impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.market.server.dao.ProductDao;
import com.market.server.dto.order.OrderDTO;
import com.market.server.mapper.product.ProductMapper;
import com.market.server.utils.RedisKeyFactory;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ProductCntInfoServiceImpl{
	
	@Autowired
	private ProductMapper productMapper;
	
	@Autowired
	private ProductDao productDao;
	
	
	/**
	 * 일정시간마다 주문상태를 log 테이블에 insert 및 주문정보이력 삭제
	 */
	@Scheduled(cron = "0 */5 * * * *") //  5분마다 실행
	public void ProductCntInfoBySchedule() {
		
		// 좋아요 수 count 업데이트
		Map<Object, Object> likeMap  = productDao.getProductCntMap(RedisKeyFactory.LIKE_CNT_KEY);
		if(likeMap.size() > 0) {
			productDao.delProductCntInfo(RedisKeyFactory.LIKE_CNT_KEY);
			productMapper.updateLikeCnt(likeMap);
		}
		
		// 조회수 count 업데이트
		Map<Object, Object> viewMap  = productDao.getProductCntMap(RedisKeyFactory.VIEW_CNT_KEY);
		if(viewMap.size() > 0) {
			productDao.delProductCntInfo(RedisKeyFactory.VIEW_CNT_KEY);
			productMapper.updateViewCnt(viewMap);
		}
			
		// 주문수 count 업데이트
		Map<Object, Object> orderMap = productDao.getProductCntMap(RedisKeyFactory.ORDER_CNT_KEY);
		if(orderMap.size() > 0) {
			productDao.delProductCntInfo(RedisKeyFactory.ORDER_CNT_KEY);
			productMapper.updateOrderCnt(orderMap);
		}
		
	}
	
}
