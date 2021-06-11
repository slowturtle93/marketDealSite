package com.market.server.mapper.product;

import org.apache.ibatis.annotations.Mapper;

import com.market.server.dto.Search;
import com.market.server.dto.product.TradingAreaDTO;

@Mapper
public interface TradingAreaMapper {
	
	public TradingAreaDTO getTradingArea(Search search);
	
	public int InsertTradingArea(TradingAreaDTO tradingAreaDTO);
	
}
