package com.market.server.service.product;

import java.util.List;

import com.market.server.dto.Search;
import com.market.server.dto.product.ProductDTO;

public interface ProductService {
	
	public List<ProductDTO> myProductInfo(Search search);
	
	public void insertProduct(ProductDTO productDTO);
}
