package com.market.server.dto.cart;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CartDTO {

	private int likeSeq;     // 예약시퀀스
	private String likeCd;   // 예약코드
	private Integer loginNo; // 로그인번호
	private String itemCd;   // 상품코드
	private String optionCd; // 옵션코드
	private String delYn;    // 삭제여부
	private String regDttm;  // 등록일시
	private String updDttm;  // 수정일시
	
	
	public CartDTO(){}
	
	/**
	 * 상품예약 등록 시 필수 값 NULL 체크
	 * 
	 * @param ProductCategoryDTO
	 * @return
	 */
	public static boolean hasNullDataBeforeRegister(CartDTO cartDTO) {
		return cartDTO.getLoginNo() == null || cartDTO.getItemCd() == null || cartDTO.getOptionCd() == null; 
	}

}
