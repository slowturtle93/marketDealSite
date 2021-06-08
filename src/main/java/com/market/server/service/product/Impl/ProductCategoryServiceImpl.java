package com.market.server.service.product.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.market.server.dto.Search;
import com.market.server.dto.product.ProductCategoryDTO;
import com.market.server.mapper.product.ProductCategoryMapper;
import com.market.server.service.product.ProductCategoryService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ProductCategoryServiceImpl implements ProductCategoryService{
	
	@Autowired
	private final ProductCategoryMapper categoryMapper;
	
	public ProductCategoryServiceImpl(ProductCategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }
	
	@Override
	public List<ProductCategoryDTO> getCategory(Search search) {
		return categoryMapper.getCategory(search);
	}

	@Override
	public int InsertCategory(List<ProductCategoryDTO> categoryList) {
		
		for(ProductCategoryDTO category : categoryList) {
			if(ProductCategoryDTO.hasNullDataBeforeRegister(category)) { // 상품카테고리명 NULL 값 확인
				log.error("Insert ERROR! {}", category);
				throw new RuntimeException("Insert ERROR! 상품 카테고리 이름을 확인해주세요.\n" + "ctegoryNm : " + category.getCategoryNm());
			}else if(isDuplicatedNm(category.getCategoryNm())) { // 상품카테고리명 중복여부 확인
				log.error("Insert ERROR! {}", category.getCategoryNm());
				throw new RuntimeException("Insert ERROR! 상품 카테고리 이름이 이미 존재합니다.\n" + "ctegoryNm : " + category.getCategoryNm());
			}
		}
		
		return categoryMapper.InsertCategory(categoryList);
	}

	@Override
	public void UpdateCategory(List<ProductCategoryDTO> categoryList) {
		
		for(ProductCategoryDTO category : categoryList) {
			if(ProductCategoryDTO.hasNullDataBeforeRegister(category)) { //상품 카테고리 수정 시 필수 입력 NULL 체크
				log.error("Update ERROR! {}", category);
				throw new RuntimeException("Update ERROR! 상품 카테고리 이름을 확인해주세요.\n" + "categoryNm : " + category.getCategoryNm());
			}else if(isDuplicatedNm(category.getCategoryNm())) { // 상품카테고리명 중복여부 확인
				log.error("Update ERROR! {}", category.getCategoryNm());
				throw new RuntimeException("Update ERROR! 상품 카테고리 이름이 이미 존재합니다.\n" + "ctegoryNm : " + category.getCategoryNm());
			}
		}
		
		// 상품 카테고리 수정
		int result = categoryMapper.UpdateCategory(categoryList);
		
		if(result < 1) {
			log.error("update OptionCategory error! {}", categoryList);
		    throw new RuntimeException("update OptionCategory error!");
		}
	}

	@Override
	public void DeleteCategory(List<ProductCategoryDTO> categoryList) {
		int result = categoryMapper.DeleteCategory(categoryList);
		
		if(result < 1) {
			log.error("delete Category error! {}", categoryList);
		    throw new RuntimeException("delete Category error!");
		}
	}

	/**
	 * 상품 카테고리 명 중복 여부 확인
	 */
	@Override
	public boolean isDuplicatedNm(String categoryNm) {
		int result = categoryMapper.isDuplicatedNm(categoryNm);
		return result == 1 ? true : false;
	}

}
