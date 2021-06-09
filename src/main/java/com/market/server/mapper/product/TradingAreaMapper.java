package com.market.server.mapper.product;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.market.server.dto.product.ProductDTO;
import com.market.server.dto.product.TradingAreaDTO;

@Mapper
public interface TradingAreaMapper {
	
	public int InsertTradingArea(TradingAreaDTO tradingAreaDTO);
	
}
