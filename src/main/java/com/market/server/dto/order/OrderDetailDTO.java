package com.market.server.dto.order;

import com.market.server.dto.option.OptionDTO;
import com.market.server.dto.product.ProductDTO;
import com.market.server.dto.user.UserDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor // Mybatis에서 기본 생성자가 없으면 예외처리를 한다
public class OrderDetailDTO {
	
	private OrderDTO orderInfo;             //주문정보
	private OrderStatusDTO orderStatusInfo; // 주문상태정보 
	private UserDTO userInfo;               // 사용자정보
	private ProductDTO productInfo;         // 상품정보
	private OptionDTO optionInfo;           // 옵션정보
	
}
