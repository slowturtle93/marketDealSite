package com.market.server.controller.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.market.server.dto.Search;
import com.market.server.dto.product.ProductDTO;
import com.market.server.dto.product.ProductDetailDTO;
import com.market.server.service.product.Impl.ProductServiceImpl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;



@RestController
@RequestMapping("/product/search/")
@Log4j2
public class ProductSearchController {
	
	@Autowired
	private ProductServiceImpl productService;
	
	
	/**
	 * 상품을 조회한다.
	 * 
	 * @param categoryCd
	 * @param divisionCd
	 * @param productSearchRequest
	 * @return
	 */
	@GetMapping("{categoryCd}/{divisionCd}")
	public ResponseEntity<SearchResponse> getProductList(@PathVariable("categoryCd") String categoryCd, 
			                                             @PathVariable("divisionCd") String divisionCd,
			                                             @RequestBody ProductSearchRequest productSearchRequest) {
		
		Search search = new Search();
		search.add("categoryCd", categoryCd);
		search.add("divisionCd", divisionCd);
		search.add("dispYn", "Y");
		search.add("delYn" , "N");
		search.add("sortStatus", productSearchRequest.getSortStatus());
		
		search.add("pg"  , productSearchRequest.getPg());
		search.add("pgSz", productSearchRequest.getPgSz());
		search.setRow();
		
		List<ProductDTO> productList  = productService.productInfo(search);
		SearchResponse searchResponse = new SearchResponse(productList);
		
		return new ResponseEntity<SearchResponse>(searchResponse, HttpStatus.OK);
	}
	
	/**
	 * 상품 상세 정보를 조회한다.
	 * 
	 * @param itemCd
	 * @return
	 */
	@GetMapping("{itemCd}/deteil")
	public ResponseEntity<ProductDetailDTO> getProductDetail(@PathVariable("itemCd") String itemCd){
		
		Search search = new Search();
		search.add("itemCd", itemCd); // 상품코드
		
		ProductDetailDTO productDetailDTO = productService.productDetail(search);
		
		return new ResponseEntity<ProductDetailDTO>(productDetailDTO, HttpStatus.OK);
	}
	
	// -------------- response 객체 --------------
	
	@Getter
    @AllArgsConstructor
    private static class SearchResponse {
        private List<ProductDTO> productDTO;
    }
	
	// -------------- request 객체 --------------

    @Setter
    @Getter
    private static class ProductSearchRequest {
    	//정렬조건
    	private String sortStatus;
    	
    	// 페이징
    	private int pg;
    	private int pgSz;
    }
	
}
