package com.market.server.mapper.product;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.market.server.dto.product.ProductDTO;

@Mapper
public interface ProductMapper {
	
	public int InsertProduct(ProductDTO productDTO);
	
	public String findByItemCd(@Param("itemSeq") int itemSeq);
	
}
