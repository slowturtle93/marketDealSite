package com.market.server.dto.product;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TradingAreaDTO {
	
	private String itemCd;    // 상품코드
	private String area1;     // 거래가능지역1
	private String area2;     // 거래가능지역2
	private String area3;     // 거래가능지역3
	
	public TradingAreaDTO(){}
	
	public TradingAreaDTO(String itemCd, String area1) {
		this.itemCd  = itemCd;
		this.area1   = area1;
	}
	
	/**
	 * 거래가능지역 필수 값 NULL 체크
	 * 
	 * @param ProductCategoryDTO
	 * @return
	 */
	public static boolean hasNullDataBeforeRegister(TradingAreaDTO tradingAreaDTO) {
		return tradingAreaDTO.getItemCd() == null || tradingAreaDTO.getArea1() == null;
	}
	
}
