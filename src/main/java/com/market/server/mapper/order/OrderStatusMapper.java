package com.market.server.mapper.order;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.market.server.dto.order.OrderStatusDTO;

@Mapper
public interface OrderStatusMapper {
	
	public List<OrderStatusDTO> getOrderStatus();
	
	public void InsertOrderStatus(@Param("orderStatusList") List<OrderStatusDTO> orderStatusList);
	
	public void UpdateOrderStatus(@Param("orderStatusList") List<OrderStatusDTO> orderStatusList);
	
	public int isDuplicatedNm(@Param("orderStatusNm") String orderStatusNm);
	
}
