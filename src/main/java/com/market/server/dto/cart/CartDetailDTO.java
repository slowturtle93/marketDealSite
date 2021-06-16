package com.market.server.dto.cart;

import com.market.server.dto.product.ProductDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor // Mybatis에서 기본 생성자가 없으면 예외처리를 한다
public class CartDetailDTO {

	private int likeSeq;           // 예약시퀀스
	private String likeCd;         // 예약코드
	private ProductDTO productDTO; // 상품정보
	
}
