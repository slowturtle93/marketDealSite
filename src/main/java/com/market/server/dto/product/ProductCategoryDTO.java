package com.market.server.dto.product;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductCategoryDTO {
	
	private int categorySeq;   // 상품카테고리시퀀스
	private String categoryCd; // 상품카테고리코드
	private String categoryNm; // 상품카테고리명
	private String delYn;      // 삭제여부
	private String dispYn;     // 노출여부
	private String regDttm;    // 등록일시
	private String updDttm;    // 수정일시
	
	public ProductCategoryDTO(){}
	
	public ProductCategoryDTO(String categoryNm) {
		this.categoryNm  = categoryNm;
	}
	
	/**
	 * 상품 카테고리 필수 값 NULL 체크
	 * 
	 * @param ProductCategoryDTO
	 * @return
	 */
	public static boolean hasNullDataBeforeRegister(ProductCategoryDTO productCategoryDTO) {
		return productCategoryDTO.getCategoryNm() == null;
	}
	
}
