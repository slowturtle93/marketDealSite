package com.market.server.dto.order;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderDTO {
	
	private int orderSeq;         // 주문시퀀스
	private String orderCd;       // 주문코드
	private Integer loginNo;      // 로그인번호
	private String itemCd;        // 상품코드
	private String optionCd;      // 옵션코드
	private String orderStatusCd; // 주문상태코드
	                              /* 
	                               *  OSC001 - 접수, OSC002 - 상품준비중, OSC003 - 잡화처리, OSC004 - 간선하차, OSC005 - 배송출고, OSC006 - 배송중, OSC007 -배송완료
	                               *  OSC008 - 환불, OSC009 - 환불완료
	                               */
	private Integer orderCnt;     // 주문수량
	private Long orderPrice;      // 주문금액
	private Long deliveryPrice;   // 배송금액
	private Long discountPrice;   // 할인금액
	private Long totalPrice;      // 총결제금액
	private String orderDttm;     // 주문일시
	private String regDttm;       // 등록일시
	private String updDttm;       // 수정일시
	
	public OrderDTO(){}
	
	public OrderDTO(String orderCd, String orderStatusCd, String regDttm){
		this.orderCd       = orderCd;
		this.orderStatusCd = orderStatusCd;
		this.regDttm       = regDttm;
	}
	
	/**
	 * 주문상태코드 등록 시 필수 값 NULL 체크
	 * 
	 * @param OrderDTO
	 * @return
	 */
	public static boolean hasNullDataBeforeRegister(OrderDTO orderDTO) {
		return orderDTO.getLoginNo()        == null || orderDTO.getItemCd()     == null || orderDTO.getOptionCd()      == null ||
				orderDTO.getOrderCnt()      == null || orderDTO.getOrderPrice() == null || orderDTO.getDeliveryPrice() == null ||
				orderDTO.getDiscountPrice() == null || orderDTO.getTotalPrice() == null;
	}

}
