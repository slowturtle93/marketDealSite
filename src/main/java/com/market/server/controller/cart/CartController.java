package com.market.server.controller.cart;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.market.server.aop.LoginCheck;
import com.market.server.aop.LoginCheck.UserType;
import com.market.server.dto.Search;
import com.market.server.dto.cart.CartDTO;
import com.market.server.dto.cart.CartDetailDTO;
import com.market.server.service.cart.Impl.CartServiceImpl;
import com.market.server.utils.SessionUtil;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;



@RestController
@RequestMapping("/cart/")
@Log4j2
public class CartController {
	
	private final CartServiceImpl cartService;
	
	@Autowired
    public CartController(CartServiceImpl cartService) {
        this.cartService = cartService;
    }
	
	/**
	 * 등록한 찜 목록을 조회한다.
	 * 
	 * @param session
	 * @param cartReqeust
	 * @return
	 */
	@GetMapping("list")
	@LoginCheck(type = UserType.USER)
	public List<CartDetailDTO> getCartList(HttpSession session, @RequestBody CartReqeust cartReqeust) {
		
		Search search = new Search();
		search.add("loginNo", SessionUtil.getLoginUserNo(session));
		
		//페이징
		search.add("pg", cartReqeust.getPg());
		search.add("pgSz", cartReqeust.getPgSz());
		search.setRow();
		
		List<CartDetailDTO> cartList = cartService.getCartList(search);
		
		return cartList;
	}
	
	/**
	 * 상품을 찜 목록에 등록한다.
	 * 
	 * @param session
	 * @param cartDTO
	 * @return
	 */
	@PostMapping("insert")
	@ResponseStatus(HttpStatus.CREATED)
	@LoginCheck(type = UserType.USER)
	public HttpStatus insertCart(HttpSession session, @RequestBody CartDTO cartDTO) {
		cartDTO.setLoginNo(SessionUtil.getLoginUserNo(session));
		cartService.insertCart(cartDTO);
		
		return HttpStatus.CREATED;
	}
	
	/**
	 * 등록한 찜 상품을 삭제한다.
	 * 
	 * @param session
	 * @param likeCd
	 */
	@DeleteMapping("delete/{likeCd}")
	@LoginCheck(type = UserType.USER)
	public void deleteCart(HttpSession session, @PathVariable("likeCd") String likeCd) {
		String loginNo = Integer.toString(SessionUtil.getLoginUserNo(session));
		cartService.deleteCart(loginNo, likeCd);
	}
	
/*======================= reqeust 객체 ======================= */
	
	@Getter
	@Setter
	private static class CartReqeust {
		private int pg;
		private int pgSz;
	}
	
}
