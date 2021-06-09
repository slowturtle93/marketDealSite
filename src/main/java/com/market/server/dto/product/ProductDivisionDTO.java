package com.market.server.dto.product;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductDivisionDTO {
	
	private int divisionSeq;   // 상품구분시퀀스
	private String divisionCd; // 상품구분코드
	private String divisionNm; // 상품구분명
	private String directYn;   // 직거래가능여부
	private String delYn;      // 삭제여부
	private String dispYn;     // 노출여부
	private String regDttm;    // 등록일시
	private String updDttm;    // 수정일시
	
	public ProductDivisionDTO(){}
	
	public ProductDivisionDTO(String divisionNm) {
		this.divisionNm  = divisionNm;
	}
	
	/**
	 * 상품 구분 필수 값 NULL 체크
	 * 
	 * @param ProductCategoryDTO
	 * @return
	 */
	public static boolean hasNullDataBeforeRegister(ProductDivisionDTO productDivisionDTO) {
		return productDivisionDTO.getDivisionNm() == null;
	}
	
}
