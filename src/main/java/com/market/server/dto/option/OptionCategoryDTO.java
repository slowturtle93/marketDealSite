package com.market.server.dto.option;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OptionCategoryDTO {
	
	private int    opCategorySeq; // 옵션카테고리시퀀스
	private String opCategoryCd;  // 옵션카테고리코드
	private String opCategoryNm;  // 옵션카테고리명
	private String delYn;         // 삭제여부
	private String dispYn;        // 전시여부
	private String regDttm;       // 등록일시
	private String updDttm;       // 수정일시
	
	public OptionCategoryDTO(){}
	
	public OptionCategoryDTO(String opCategoryNm) {
		this.opCategoryNm  = opCategoryNm;
	}
	
	/**
	 * 옵션 카테고리 필수 값 NULL 체크
	 * 
	 * @param ProductCategoryDTO
	 * @return
	 */
	public static boolean hasNullDataBeforeRegister(OptionCategoryDTO optionCategoryDTO) {
		return optionCategoryDTO.getOpCategoryNm() == null;
	}
	
}
