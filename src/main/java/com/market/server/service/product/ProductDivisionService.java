package com.market.server.service.product;

import java.util.List;

import com.market.server.dto.Search;
import com.market.server.dto.product.ProductDivisionDTO;

public interface ProductDivisionService {
	
	public List<ProductDivisionDTO> getDivision(Search search);
	
	public int InsertDivision(List<ProductDivisionDTO> divisionList);
	
	public void UpdateDivision(List<ProductDivisionDTO> divisionList);
	
	public void DeleteDivision(List<ProductDivisionDTO> divisionList);
	
	public boolean isDuplicatedNm(String divisionNm);
}
