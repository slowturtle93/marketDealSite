package com.market.server.mapper.product;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.market.server.dto.Search;
import com.market.server.dto.product.ProductDTO;

@Mapper
public interface ProductMapper {
	
	public List<ProductDTO> myProductInfo(Search search);
	
	public ProductDTO myProductDetail(Search search);
	
	public int InsertProduct(ProductDTO productDTO);
	
	public int UpdateProduct(ProductDTO productDTO);
	
	public String findByItemCd(@Param("itemSeq") int itemSeq);
	
}
