package com.market.server.controller.product;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.market.server.aop.LoginCheck;
import com.market.server.aop.LoginCheck.UserType;
import com.market.server.dto.option.OptionDTO;
import com.market.server.dto.product.ProductDTO;
import com.market.server.dto.product.TradingAreaDTO;
import com.market.server.service.product.Impl.ProductServiceImpl;
import com.market.server.utils.SessionUtil;

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
	 * 싱픔을 등록한다.
	 * 
	 * @param session
	 * @param productDTO
	 */
	@PostMapping("insert")
	@ResponseStatus(HttpStatus.CREATED)
	@LoginCheck(type = UserType.USER)
	public HttpStatus insertProduct(HttpSession session, @RequestBody ProductDTO productDTO) {
		productDTO.setLoginNo(SessionUtil.getLoginUserNo(session));
		productService.insertProduct(productDTO);
		
		return HttpStatus.CREATED;
	}
	
}
