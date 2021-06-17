package com.market.server.dto.product;

import java.util.List;

import com.market.server.dto.option.OptionDTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductDTO {
	// 기본, 품절
	public enum Status {
		DEFAULT, SOLDOUT
    }
	
	private Integer itemSeq;    // 상품시퀀스
	private String itemCd;      // 상품코드
	private Integer loginNo;    // 로그인번호
	private String itemNm;      // 상품명
	private Long itemPrice;     // 상품가격
	private Integer viewCnt;    // 조회수
	private Integer likeCnt;    // 좋아요수
	private Integer orderCnt;   // 주문수
	private String categoryCd;  // 상품카테고리코드
	private String dispYn;      // 노출여부
	private String delYn;       // 삭제여부
	private String image1;      // 이미지1
	private String image2;      // 이미지2
	private String image3;      // 이미지3
	private String image4;      // 이미지4
	private String description; // 상품설명
	private Long deliveryPrice; // 배송가격
	private String title;       // 상품제목
	private String divisionCd;  // 상품구분코드
	private Status status;      // 상태
	private String regDttm;     // 등록일시
	private String updDttm;     // 수정일시
	
	public ProductDTO(){}
	
	/**
	 * 상품등록 시 필수 값 NULL 체크
	 * 
	 * @param ProductCategoryDTO
	 * @return
	 */
	public static boolean hasNullDataBeforeRegister(ProductDTO productDTO) {
		return productDTO.getItemNm()        == null || productDTO.getItemPrice() == null || productDTO.getDivisionCd()  == null ||
			   productDTO.getCategoryCd()    == null || productDTO.getImage1()    == null || productDTO.getDescription() == null ||
			   productDTO.getDeliveryPrice() == null || productDTO.getTitle()     == null; 
	}

}
