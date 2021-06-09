package com.market.server.mapper.product;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.market.server.dto.Search;
import com.market.server.dto.product.ProductDivisionDTO;

@Mapper
public interface ProductDivisionMapper {
	
	public List<ProductDivisionDTO> getDivision(Search search);
	
	public int InsertDivision(@Param("divisionList") List<ProductDivisionDTO> divisionList);
	
	public int UpdateDivision(@Param("divisionList") List<ProductDivisionDTO> divisionList);
	
	public int DeleteDivision(@Param("divisionList") List<ProductDivisionDTO> divisionList);
	
	public int isDuplicatedNm(@Param("divisionNm") String divisionNm);
}
