package com.market.server.service.cart.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.market.server.dto.Search;
import com.market.server.dto.cart.CartDTO;
import com.market.server.dto.cart.CartDetailDTO;
import com.market.server.mapper.cart.CartMapper;
import com.market.server.service.cart.CartService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class CartServiceImpl implements CartService{
	
	@Autowired
	private CartMapper cartMapper;

	
	/**
	 * 등록한 찜 목록을 조회한다.
	 */
	@Override
	public List<CartDetailDTO> getCartList(Search search) {
		return cartMapper.getCartList(search);
	}
	
	/**
	 * 상품을 찜 목록에 등록한다.
	 */
	@Override
	public void insertCart(CartDTO cartDTO) {
		
		// 찜 등록 시 필수값 체크
		if(CartDTO.hasNullDataBeforeRegister(cartDTO)) {
			log.error("Insert ERROR! {}", cartDTO);
			throw new RuntimeException("Insert ERROR! 찜 등록 정보를 확인해주세요.\n" + "cartDTO : " + cartDTO);
		}
		
		int result = cartMapper.insertCart(cartDTO);
		
		if(result != 1) { // 등록 실패인 경우
			log.error("Insert ERROR! {}", cartDTO);
			throw new RuntimeException("Insert ERROR! 찜 정보를 확인해주세요.\n" + "cartDTO : " + cartDTO);
		}
	}

	/**
	 * 등록한 찜 상품을 삭제한다.
	 */
	@Override
	public void deleteCart(String loginNo, String likeCd) {
		int result = cartMapper.deleteCart(loginNo, likeCd);
		
		if(result != 1) { // 수정 실패인 경우
			log.error("Update ERROR! {}", likeCd);
			throw new RuntimeException("Update ERROR! 찜코드정보를 확인해주세요.\n" + "likeCd : " + likeCd);
		}
	}

}
