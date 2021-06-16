package com.market.server.service.order.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.market.server.dto.order.OrderStatusDTO;
import com.market.server.mapper.order.OrderStatusMapper;
import com.market.server.service.order.OrderStatusService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class OrderStatusServiceImpl implements OrderStatusService{
	
	@Autowired
	private OrderStatusMapper orderStatusMapper;

	/**
	 * 주문상태코드를 조회한다.
	 */
	@Override
	public List<OrderStatusDTO> getOrderStatus() {
		return orderStatusMapper.getOrderStatus();
	}

	/**
	 * 주문상태코드를 등록한다.
	 */
	@Override
	public void InsertOrderStatus(List<OrderStatusDTO> orderStatusList) {
		
		for(int i = 0; i < orderStatusList.size(); i++) {
			//주문상태등록 시 필수 값 NULL 체크
			if(OrderStatusDTO.hasNullDataBeforeRegister(orderStatusList.get(i))) {
				log.error("Update ERROR! {}", orderStatusList.get(i).getOrderStatusNm());
				throw new RuntimeException("Insert ERROR! 주문상태명을 확인해주세요.\n" + "opCategoryNm[" + i + "] : " + orderStatusList.get(i).getOrderStatusNm());
			}
			
			// 주문상태명 중복여부 확인
			if(isDuplicatedNm(orderStatusList.get(i).getOrderStatusNm())) {
				log.error("Update ERROR! {}", orderStatusList.get(i).getOrderStatusNm());
				throw new RuntimeException("Insert ERROR! 주문상태명이 이미 존재합니다.\n" + "opCategoryNm" + i + "] : " + orderStatusList.get(i).getOrderStatusNm());
			}
		}
		
		orderStatusMapper.InsertOrderStatus(orderStatusList);
	}

	/**
	 * 주문상태코드를 수정한다. 
	 */
	@Override
	public void UpdateOrderStatus(List<OrderStatusDTO> orderStatusList) {
		
		for(int i = 0; i < orderStatusList.size(); i++) {
			//주문상태등록 시 필수 값 NULL 체크
			if(OrderStatusDTO.hasNullDataBeforeRegister(orderStatusList.get(i))) {
				log.error("Insert ERROR! {}", orderStatusList.get(i).getOrderStatusNm());
				throw new RuntimeException("Insert ERROR! 주문상태명을 확인해주세요.\n" + "opCategoryNm[" + i + "] : " + orderStatusList.get(i).getOrderStatusNm());
			}
			
			// 주문상태명 중복여부 확인
			if(isDuplicatedNm(orderStatusList.get(i).getOrderStatusNm())) {
				log.error("Insert ERROR! {}", orderStatusList.get(i).getOrderStatusNm());
				throw new RuntimeException("Insert ERROR! 주문상태명이 이미 존재합니다.\n" + "opCategoryNm" + i + "] : " + orderStatusList.get(i).getOrderStatusNm());
			}
		}
		orderStatusMapper.UpdateOrderStatus(orderStatusList);
	}

	/**
	 * 주문상태명을 중복여부를 체크한다.
	 */
	@Override
	public boolean isDuplicatedNm(String orderStatusNm) {
		return orderStatusMapper.isDuplicatedNm(orderStatusNm) == 1 ? true : false;
	}
	
	

}
