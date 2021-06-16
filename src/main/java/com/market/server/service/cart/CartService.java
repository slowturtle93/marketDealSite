package com.market.server.service.cart;

import java.util.List;

import com.market.server.dto.Search;
import com.market.server.dto.cart.CartDTO;
import com.market.server.dto.cart.CartDetailDTO;

public interface CartService {
	
	public List<CartDetailDTO> getCartList(Search search);
	
	public void insertCart(CartDTO cartDTO);
	
	public void deleteCart(String loginNo, String likeCd);
}
