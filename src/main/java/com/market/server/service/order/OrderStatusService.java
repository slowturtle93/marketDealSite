package com.market.server.service.order;

import java.util.List;

import com.market.server.dto.order.OrderStatusDTO;

public interface OrderStatusService {
	
	public List<OrderStatusDTO> getOrderStatus();
	
	public void InsertOrderStatus(List<OrderStatusDTO> orderStatusList);
	
	public void UpdateOrderStatus(List<OrderStatusDTO> orderStatusList);
	
	public boolean isDuplicatedNm(String orderStatusNm);
	
}
