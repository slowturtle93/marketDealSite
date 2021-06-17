package com.market.server.mapper.product;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.market.server.dto.Search;
import com.market.server.dto.product.ProductDTO;

@Mapper
public interface ProductMapper {
	
	public List<ProductDTO> productInfo(Search search);
	
	public ProductDTO productDetail(Search search);
	
	public int InsertProduct(ProductDTO productDTO);
	
	public int UpdateProduct(ProductDTO productDTO);
	
	public int DeleteProduct(Search search);
	
	public String findByItemCd(@Param("itemSeq") int itemSeq);
	
	public int updateLikeCnt(@Param("likeMap") Map<Object, Object> likeMap);
	
	public int updateViewCnt(@Param("viewMap") Map<Object, Object> viewMap);
	
	public int updateOrderCnt(@Param("orderMap") Map<Object, Object> orderMap);
	
}
