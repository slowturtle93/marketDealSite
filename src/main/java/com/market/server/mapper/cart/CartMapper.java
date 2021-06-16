package com.market.server.mapper.cart;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.market.server.dto.Search;
import com.market.server.dto.cart.CartDTO;
import com.market.server.dto.cart.CartDetailDTO;

@Mapper
public interface CartMapper {
	
	public List<CartDetailDTO> getCartList(Search search);
	
	public int insertCart(CartDTO cartDTO);
	
	public int deleteCart(@Param("loginNo") String loginNo, @Param("likeCd") String likeCd);
}
