package com.market.server.mapper.product;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.market.server.dto.Search;
import com.market.server.dto.product.ProductCategoryDTO;

@Mapper
public interface ProductCategoryMapper {
	
	public List<ProductCategoryDTO> getCategory(Search search);
	
	public int InsertCategory(@Param("categoryList") List<ProductCategoryDTO> categoryList);
	
	public int UpdateCategory(@Param("categoryList") List<ProductCategoryDTO> categoryList);
	
	public int DeleteCategory(@Param("categoryList") List<ProductCategoryDTO> categoryList);
	
	public int isDuplicatedNm(@Param("categoryNm") String categoryNm);
}
