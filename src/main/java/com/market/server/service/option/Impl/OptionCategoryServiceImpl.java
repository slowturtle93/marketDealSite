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
	private final OptionCategoryMapper optionMapper;
	
	public OptionCategoryServiceImpl(OptionCategoryMapper optionMapper) {
        this.optionMapper = optionMapper;
    }
	
	@Override
	public List<OptionCategoryDTO> getOpCategory(Search search) {
		return optionMapper.getOpCategory(search);
	}

	@Override
	public int InsertOpCategory(List<OptionCategoryDTO> optionCategoryList) {
		// 옵션 카테고리 등록 시 필수 입력 NULL 체크
		for(OptionCategoryDTO optionCategory : optionCategoryList) {
			if(OptionCategoryDTO.hasNullDataBeforeRegister(optionCategory)) {
				log.error("Insert ERROR! {}", optionCategory);
				throw new RuntimeException("Insert ERROR! 옵션 카테고리 이름을 확인해주세요.\n" + "opCategoryNm : " + optionCategory.getOpCategoryNm());
			}
		}
		
		return optionMapper.InsertOpCategory(optionCategoryList);
	}

	@Override
	public void UpdateOpCategory(List<OptionCategoryDTO> optionCategoryList) {
		// 옵션 카테고리 수정 시 필수 입력 NULL 체크
		for(OptionCategoryDTO optionCategory : optionCategoryList) {
			if(OptionCategoryDTO.hasNullDataBeforeRegister(optionCategory)) {
				log.error("Insert ERROR! {}", optionCategory);
				throw new RuntimeException("Update ERROR! 옵션 카테고리 이름을 확인해주세요.\n" + "opCategoryNm : " + optionCategory.getOpCategoryNm());
			}
		}
		
		// 옵션 카테고리 수정
		int result = optionMapper.UpdateOpCategory(optionCategoryList);
		
		if(result < 1) {
			log.error("update OptionCategory error! {}", optionCategoryList);
		    throw new RuntimeException("update OptionCategory error!");
		}
	}

	@Override
	public void DeleteOpCategory(List<OptionCategoryDTO> optionCategoryList) {
		int result = optionMapper.DeleteOpCategory(optionCategoryList);
		
		if(result < 1) {
			log.error("delete OptionCategory error! {}", optionCategoryList);
		    throw new RuntimeException("delete OptionCategory error!");
		}
	}

}
