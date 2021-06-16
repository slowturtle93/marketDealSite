package com.market.server.service.order.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.market.server.dao.OrderLogDao;
import com.market.server.dto.order.OrderDTO;
import com.market.server.mapper.order.OrderMapper;
import com.market.server.utils.RedisKeyFactory;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class OrderLogServiceImpl{
	
	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired
	private OrderLogDao orderLogDao;
	
	/**
	 * 일정시간마다 주문상태를 log 테이블에 insert 및 주문정보이력 삭제
	 */
	@Scheduled(cron = "0 0 0/1 * * *") //1시간마다 실행
	public void InsertLogBySchedule() {
		List<OrderDTO> orderList = orderLogDao.findAll(RedisKeyFactory.ORDER_KEY);
		if(orderList.size() > 0) {
			orderLogDao.deleteByOrder(RedisKeyFactory.ORDER_KEY);
			orderMapper.insertOrderLog(orderList);
		}
	}
	
}
