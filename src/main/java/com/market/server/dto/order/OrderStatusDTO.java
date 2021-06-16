package com.market.server.dto.order;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderStatusDTO {
	
	private int orderStatusSeq;   // 주문상태시퀀스
	private String orderStatusCd; // 주문상태코드
	private String orderStatusNm; // 주문상태명
	private String regDttm;       // 등록일시
	private String updDttm;       // 수정일시
	
	public OrderStatusDTO(){}
	
	/**
	 * 주문상태코드 등록 시 필수 값 NULL 체크
	 * 
	 * @param OrderDTO
	 * @return
	 */
	public static boolean hasNullDataBeforeRegister(OrderStatusDTO orderStatusDTO) {
		return orderStatusDTO.getOrderStatusNm() == null;
	}

}
