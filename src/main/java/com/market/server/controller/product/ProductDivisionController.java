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
import com.market.server.dto.product.ProductDivisionDTO;
import com.market.server.service.product.Impl.ProductDivisionServiceImpl;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;



@RestController
@RequestMapping("/product/division/")
@Log4j2
public class ProductDivisionController {
	
private final ProductDivisionServiceImpl productDivisionService;
	
	@Autowired
    public ProductDivisionController(ProductDivisionServiceImpl productDivisionService) {
        this.productDivisionService = productDivisionService;
    }
	
	/**
	 * 상품 구분정보를 조회한다.
	 * 
	 * @return
	 */
	@GetMapping("select")
	@LoginCheck(type = UserType.ADMIN)
	public List<ProductDivisionDTO> getDivision(@RequestBody DivisionRequest divisionRequest){
		Search search = new Search();
		
		search.add("delYn"  , divisionRequest.getDelYn());  // 삭제여부
		search.add("dispYn" , divisionRequest.getDispYn()); // 전시여부
		
		search.add("pg"  , divisionRequest.getPg());   // 현재페이제
		search.add("pgSz", divisionRequest.getPgSz()); // 한 페이지당 Row 수
		search.setRow();                               // 페이지 계산
		
		return productDivisionService.getDivision(search);
	}
	
	/**
	 * 상품 구분정보를 등록한다.
	 * 
	 * @param List<ProductDivisionDTO>
	 * @return
	 */
	@PostMapping("insert")
	@ResponseStatus(HttpStatus.CREATED)
	@LoginCheck(type = UserType.ADMIN)
	public HttpStatus InsertDivision(@RequestBody @NotNull List<ProductDivisionDTO> divisionList){
		// 상품 구분정보 등록
		productDivisionService.InsertDivision(divisionList);
		return HttpStatus.CREATED;
	}
	
	/**
	 * 상품 구분정보를 수정한다.
	 * 
	 * @param List<ProductDivisionDTO>
	 */
	@PatchMapping("update")
	@LoginCheck(type = UserType.ADMIN)
	public void UpdateDivision(@RequestBody @NotNull List<ProductDivisionDTO> divisionList){
		// 상품 구분정보 수정
		productDivisionService.UpdateDivision(divisionList);
	}
	
	
	/**
	 * 상품 구분정보를 삭제한다.
	 * 
	 * @param List<ProductDivisionDTO>
	 */
	@DeleteMapping("delete")
	@LoginCheck(type = UserType.ADMIN)
	public void DeleteDivision( @RequestBody @NotNull List<ProductDivisionDTO> divisionList){
		// 상품 구분정보 삭제
		productDivisionService.DeleteDivision(divisionList);
	}
	
	
	/*======================= request 객체 ======================= */
	
	@Getter
	@Setter
	private static class DivisionRequest {
		private String delYn;
		private String dispYn;
		@NonNull
		private int pg;
		@NonNull
		private int pgSz;
	}
	
	
	
}
