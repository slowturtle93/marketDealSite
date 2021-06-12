package com.market.server.service.product.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.market.server.dto.Search;
import com.market.server.dto.product.ProductDivisionDTO;
import com.market.server.mapper.product.ProductDivisionMapper;
import com.market.server.service.product.ProductDivisionService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ProductDivisionServiceImpl implements ProductDivisionService{
	
	@Autowired
	private final ProductDivisionMapper productDivisionMapper;
	
	public ProductDivisionServiceImpl(ProductDivisionMapper productDivisionMapper) {
        this.productDivisionMapper = productDivisionMapper;
    }
	
	@Override
	public List<ProductDivisionDTO> getDivision(Search search) {
		return productDivisionMapper.getDivision(search);
	}

	@Override
	public int InsertDivision(List<ProductDivisionDTO> divisionList) {
		
		for(ProductDivisionDTO division : divisionList) {
			if(ProductDivisionDTO.hasNullDataBeforeRegister(division)) { // 상품구분정보명 NULL 값 확인
				log.error("Insert ERROR! {}", division);
				throw new RuntimeException("Insert ERROR! 상품 구분정보 이름을 확인해주세요.\n" + "ctegoryNm : " + division);
			}else if(isDuplicatedNm(division.getDivisionNm())) { // 상품구분정보명 중복여부 확인
				log.error("Insert ERROR! {}", division.getDivisionNm());
				throw new RuntimeException("Insert ERROR! 상품 구분정보 이름이 이미 존재합니다.\n" + "ctegoryNm : " + division.getDivisionNm());
			}
		}
		
		return productDivisionMapper.InsertDivision(divisionList);
	}

	@Override
	public void UpdateDivision(List<ProductDivisionDTO> divisionList) {
		
		for(ProductDivisionDTO division : divisionList) {
			if(ProductDivisionDTO.hasNullDataBeforeRegister(division)) { // 상품구분정보명 NULL 값 확인
				log.error("Update ERROR! {}", division);
				throw new RuntimeException("Update ERROR! 상품 구분정보 이름을 확인해주세요.\n" + "ctegoryNm : " + division);
			}else if(isDuplicatedNm(division.getDivisionNm())) { // 상품구분정보명 중복여부 확인
				log.error("Update ERROR! {}", division.getDivisionNm());
				throw new RuntimeException("Update ERROR! 상품 구분정보 이름이 이미 존재합니다.\n" + "ctegoryNm : " + division.getDivisionNm());
			}
		}
		
		// 상품 카테고리 수정
		int result = productDivisionMapper.UpdateDivision(divisionList);
		
		if(result < 1) {
			log.error("update Division error! {}", divisionList);
		    throw new RuntimeException("update Division error!");
		}
	}

	@Override
	public void DeleteDivision(List<ProductDivisionDTO> categoryList) {
		int result = productDivisionMapper.DeleteDivision(categoryList);
		
		if(result < 1) {
			log.error("delete Division error! {}", categoryList);
		    throw new RuntimeException("delete Division error!");
		}
	}

	/**
	 * 상품 카테고리 명 중복 여부 확인
	 */
	@Override
	public boolean isDuplicatedNm(String categoryNm) {
		int result = productDivisionMapper.isDuplicatedNm(categoryNm);
		return result == 1 ? true : false;
	}
	
}
