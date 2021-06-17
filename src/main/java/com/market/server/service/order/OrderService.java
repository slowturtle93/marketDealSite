package com.market.server.service.order;

import java.util.List;

import com.market.server.dto.order.OrderDTO;
import com.market.server.dto.order.OrderDetailDTO;

public interface OrderService {
	
	public void doOrder(OrderDTO orderDTO);
	
	public List<OrderDetailDTO> getOrderList(int loginNo);
	
	public void updateOrderStatus(OrderDTO orderDTO);
	
}
