package com.market.server.dto.option;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OptionDTO {
	
	private int    optionSeq;    // 옵션시퀀스
	private String optionCd;     // 옵션코드
	private String optionNm;     // 옵션명
	private String itemCd;       // 상품코드
	private String opCategoryCd; // 옵션카테고리코드
	private String opCategoryNm;  // 옵션카테고리명
	private String delYn;        // 삭제여부
	private String dispYn;       // 노출여부
	private String regDttm;      // 등록일시
	private String updDttm;      // 수정일시
	
	public OptionDTO(){}
	
	public OptionDTO(String optionNm) {
		this.optionNm  = optionNm;
	}
	
	/**
	 * 옵션 카테고리 필수 값 NULL 체크
	 * 
	 * @param ProductCategoryDTO
	 * @return
	 */
	public static boolean hasNullDataBeforeRegister(OptionDTO optionDTO) {
		return optionDTO.getOptionNm() == null;
	}
	
}
