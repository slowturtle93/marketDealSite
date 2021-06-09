package com.market.server.controller.product;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.market.server.aop.LoginCheck;
import com.market.server.aop.LoginCheck.UserType;
import com.market.server.dto.Search;
import com.market.server.dto.product.ProductCategoryDTO;
import com.market.server.service.product.Impl.ProductCategoryServiceImpl;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;



@RestController
@RequestMapping("/product/category/")
@Log4j2
public class ProductCategoryController {
	
	private final ProductCategoryServiceImpl ctegoryService;
	
	@Autowired
    public ProductCategoryController(ProductCategoryServiceImpl ctegoryService) {
        this.ctegoryService = ctegoryService;
    }
	
	/**
	 * 상품 카테고리를 조회한다.
	 * 
	 * @return
	 */
	@GetMapping("select")
	public List<ProductCategoryDTO> getCategory(@RequestBody CategoryRequest categoryRequest){
		Search search = new Search();
		
		search.add("delYn"  , categoryRequest.getDelYn());  // 삭제여부
		search.add("dispYn" , categoryRequest.getDispYn()); // 전시여부
		
		search.add("pg"  , categoryRequest.getPg());   // 현재페이제
		search.add("pgSz", categoryRequest.getPgSz()); // 한 페이지당 Row 수
		search.setRow();                               // 페이지 계산
		
		return ctegoryService.getCategory(search);
	}
	
	/**
	 * 상품 카테고리를 등록한다.
	 * 
	 * @param OpCategoryRequest
	 * @return
	 */
	@PostMapping("insert")
	@ResponseStatus(HttpStatus.CREATED)
	@LoginCheck(type = UserType.ADMIN)
	public HttpStatus InsertCategory(@RequestBody @NotNull List<ProductCategoryDTO> categoryList){
		// 상품 카테고리 등록
		ctegoryService.InsertCategory(categoryList);
		return HttpStatus.CREATED;
	}
	
	/**
	 * 상품 카테고리를 수정한다.
	 * 
	 * @param optionCategoryDTO
	 */
	@PatchMapping("update")
	@LoginCheck(type = UserType.ADMIN)
	public void UpdateCategory(@RequestBody @NotNull List<ProductCategoryDTO> categoryList){
		// 상품 카테고리 수정
		ctegoryService.UpdateCategory(categoryList);
	}
	
	
	/**
	 * 상품 카테고리를 삭제한다.
	 * 
	 * @param optionCategoryDTO
	 */
	@DeleteMapping("delete")
	@LoginCheck(type = UserType.ADMIN)
	public void DeleteCategory( @RequestBody @NotNull List<ProductCategoryDTO> categoryList){
		// 상품 카테고리 삭제
		ctegoryService.DeleteCategory(categoryList);
	}
	
	
	/*======================= request 객체 ======================= */
	
	@Getter
	@Setter
	private static class CategoryRequest {
		private String delYn;
		private String dispYn;
		private int pg;
		private int pgSz;
	}
	
}
