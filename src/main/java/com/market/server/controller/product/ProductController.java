package com.market.server.controller.product;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.market.server.aop.LoginCheck;
import com.market.server.aop.LoginCheck.UserType;
import com.market.server.dto.Search;
import com.market.server.dto.option.OptionDTO;
import com.market.server.dto.product.ProductDTO;
import com.market.server.dto.product.ProductDetailDTO;
import com.market.server.dto.product.TradingAreaDTO;
import com.market.server.service.product.Impl.ProductServiceImpl;
import com.market.server.utils.SessionUtil;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;



@RestController
@RequestMapping("/product/")
@Log4j2
public class ProductController {
	
	private final ProductServiceImpl productService;
	
	@Autowired
    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }
	
	/**
	 * 본인이 등록한 상품을 조회한다.
	 * 
	 * @param session
	 * @param productRequest
	 * @return
	 */
	@GetMapping("myProducts")
	@LoginCheck(type = UserType.USER)
	public ResponseEntity<ProductsResponse> myProductInfo(HttpSession session, @RequestBody ProductRequest productRequest){
		
		Search search = new Search();
		search.add("loginNo", SessionUtil.getLoginUserNo(session)); // 로그인번호
		
		//조회조건
		search.add("delYn",       productRequest.getDelYn());       // 삭제여부
		search.add("dispYn",      productRequest.getDispYn());      // 전시여부
		search.add("status",      productRequest.getStatus());      // 상태
		search.add("categoryCd",  productRequest.getCategoryCd());  // 상품카테고리
		search.add("divisionCd",  productRequest.getDivisionCd());  // 상품구분코드
		search.add("fromRegDate", productRequest.getFromRegDate()); // from 등록일시 
		search.add("toRegDate",   productRequest.getToRegDate());   // to 등록일시
		
		//정렬조건
		search.add("sortStatus",    productRequest.getSortStatus()); 
		
		//페이징
		search.add("pg",   productRequest.getPg());   // 현재페이지
		search.add("pgSz", productRequest.getPgSz()); // 페이지 당 row 수
		search.setRow();
    	
		List<ProductDTO> productList = productService.myProductInfo(search);
		ProductsResponse productsResponse = new ProductsResponse(productList);
		
		return new ResponseEntity<ProductsResponse>(productsResponse, HttpStatus.OK);
	}
	
	/**
	 * 본인이 등록한 특정 상품의 상세 정보를 조회한다.
	 * 
	 * @param itemCd
	 * @param session
	 * @return
	 */
	@GetMapping("myProducts/{itemCd}/detail")
	@LoginCheck(type = UserType.USER)
	public ResponseEntity<ProductDetailDTO> myProductDetail(@PathVariable("itemCd") String itemCd, HttpSession session){
		
		Search search = new Search();
		search.add("loginNo", SessionUtil.getLoginUserNo(session)); // 로그인번호
		search.add("itemCd", itemCd); // 상품코드
		
		ProductDetailDTO productDetailDTO = productService.myProductDetail(search);
		
		return new ResponseEntity<ProductDetailDTO>(productDetailDTO, HttpStatus.OK);
	}
	
	
	/**
	 * 싱픔을 등록한다.
	 * 
	 * @param session
	 * @param productDTO
	 */
	@PostMapping("insert")
	@ResponseStatus(HttpStatus.CREATED)
	@LoginCheck(type = UserType.USER)
	public HttpStatus insertProduct(HttpSession session, @RequestBody ProductDetailDTO productDetailDTO) {
		productDetailDTO.getProductDTO().setLoginNo(SessionUtil.getLoginUserNo(session));
		productService.insertProduct(productDetailDTO);
		
		return HttpStatus.CREATED;
	}
	
	/**
	 * 상품 정보를 수정한다.
	 * 
	 * @param session
	 * @param productDetailDTO
	 */
	@PatchMapping("update")
	@LoginCheck(type = UserType.USER)
	public void updateProduct(HttpSession session, @RequestBody ProductDetailDTO productDetailDTO) {
		productDetailDTO.getProductDTO().setLoginNo(SessionUtil.getLoginUserNo(session));
		productService.updateProduct(productDetailDTO);
	}
	
	/**
	 * 등록한 상품 정보를 삭제한다.
	 * 
	 * @param session
	 * @param itemCd
	 */
	@DeleteMapping("delete/{itemCd}")
	@LoginCheck(type = UserType.USER)
	public void deleteProduct(HttpSession session, @PathVariable("itemCd") String itemCd) {
		
		Search search = new Search();
		search.add("itemCd", itemCd);
		search.add("loginNo", SessionUtil.getLoginUserNo(session));
		
		productService.deleteProduct(search);
	}
	
	// -------------- response 객체 --------------
	
	@Getter
    @AllArgsConstructor
    private static class ProductsResponse {
        private List<ProductDTO> productDTO;
    }
	
	// -------------- request 객체 --------------

    @Setter
    @Getter
    private static class ProductRequest {
    	//조회조건
    	private String delYn;
    	private String dispYn;
    	private String status;
    	private String categoryCd;
    	private String divisionCd;
    	private String fromRegDate;
    	private String toRegDate;
    	
    	//정렬조건
    	private String sortStatus;
    	
    	// 페이징
    	private int pg;
    	private int pgSz;
    }
	
}
