package com.market.server.service.option.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.market.server.dto.Search;
import com.market.server.dto.option.OptionCategoryDTO;
import com.market.server.mapper.option.OptionCategoryMapper;
import com.market.server.service.option.OptionCategoryService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class OptionCategoryServiceImpl implements OptionCategoryService{
	
	@Autowired
	private final OptionCategoryMapper optionCategoryMapper;
	
	public OptionCategoryServiceImpl(OptionCategoryMapper optionCategoryMapper) {
        this.optionCategoryMapper = optionCategoryMapper;
    }
	
	@Override
	public List<OptionCategoryDTO> getOpCategory(Search search) {
		return optionCategoryMapper.getOpCategory(search);
	}

	@Override
	public int InsertOpCategory(List<OptionCategoryDTO> optionCategoryList) {
		
		for(OptionCategoryDTO optionCategory : optionCategoryList) {
			if(OptionCategoryDTO.hasNullDataBeforeRegister(optionCategory)) { // 옵션 카테고리 등록 시 필수 입력 NULL 체크
				log.error("Insert ERROR! {}", optionCategory.getOpCategoryNm());
				throw new RuntimeException("Insert ERROR! 옵션 카테고리 이름을 확인해주세요.\n" + "opCategoryNm : " + optionCategory.getOpCategoryNm());
			}else if(isDuplicatedNm(optionCategory.getOpCategoryNm())) { // 옵션 카테고리명 중복여부 확인
				log.error("Insert ERROR! {}", optionCategory.getOpCategoryNm());
				throw new RuntimeException("Insert ERROR! 상품 카테고리 이름이 이미 존재합니다.\n" + "ctegoryNm : " + optionCategory.getOpCategoryNm());
			}
		}
		
		return optionCategoryMapper.InsertOpCategory(optionCategoryList);
	}

	@Override
	public void UpdateOpCategory(List<OptionCategoryDTO> optionCategoryList) {
		
		for(OptionCategoryDTO optionCategory : optionCategoryList) { // 옵션 카테고리 수정 시 필수 입력 NULL 체크
			if(OptionCategoryDTO.hasNullDataBeforeRegister(optionCategory)) {
				log.error("Update ERROR! {}", optionCategory);
				throw new RuntimeException("Update ERROR! 옵션 카테고리 이름을 확인해주세요.\n" + "opCategoryNm : " + optionCategory.getOpCategoryNm());
			}else if(isDuplicatedNm(optionCategory.getOpCategoryNm())) { // 옵션 카테고리명 중복여부 확인
				log.error("Update ERROR! {}", optionCategory.getOpCategoryNm());
				throw new RuntimeException("Update ERROR! 상품 카테고리 이름이 이미 존재합니다.\n" + "ctegoryNm : " + optionCategory.getOpCategoryNm());
			}
		}
		
		// 옵션 카테고리 수정
		int result = optionCategoryMapper.UpdateOpCategory(optionCategoryList);
		
		if(result < 1) {
			log.error("update OptionCategory error! {}", optionCategoryList);
		    throw new RuntimeException("update OptionCategory error!");
		}
	}

	@Override
	public void DeleteOpCategory(List<OptionCategoryDTO> optionCategoryList) {
		int result = optionCategoryMapper.DeleteOpCategory(optionCategoryList);
		
		if(result < 1) {
			log.error("delete OptionCategory error! {}", optionCategoryList);
		    throw new RuntimeException("delete OptionCategory error!");
		}
	}
	
	/**
	 * 상품 카테고리 명 중복 여부 확인
	 */
	@Override
	public boolean isDuplicatedNm(String categoryNm) {
		int result = optionCategoryMapper.isDuplicatedNm(categoryNm);
		return result == 1 ? true : false;
	}

}
