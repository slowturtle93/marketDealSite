package com.market.server.service.product;

import java.util.List;

import com.market.server.dto.Search;
import com.market.server.dto.product.ProductCategoryDTO;

public interface ProductCategoryService {
	
	public List<ProductCategoryDTO> getCategory(Search search);
	
	public int InsertCategory(List<ProductCategoryDTO> categoryList);
	
	public void UpdateCategory(List<ProductCategoryDTO> categoryList);
	
	public void DeleteCategory(List<ProductCategoryDTO> categoryList);
	
	public boolean isDuplicatedNm(String categoryNm);
	
}
