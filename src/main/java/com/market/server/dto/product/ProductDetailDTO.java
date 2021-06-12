package com.market.server.dto.product;

import java.util.List;

import com.market.server.dto.option.OptionDTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductDetailDTO {
	
	private ProductDTO productDTO;          // 상품정보
	private List<OptionDTO> optionList;     // 상품옵션 
	private TradingAreaDTO tradingAreaDTO;  // 거래가능지역
	
	public ProductDetailDTO(){}
	
}
