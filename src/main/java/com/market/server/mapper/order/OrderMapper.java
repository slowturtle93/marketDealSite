package com.market.server.mapper.order;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.market.server.dto.order.OrderDTO;
import com.market.server.dto.order.OrderDetailDTO;

@Mapper
public interface OrderMapper {
	
	public int doOrder(OrderDTO orderDTO);
	
	public List<OrderDetailDTO> getOrderList(@Param("loginNo") int loginNo);
}
