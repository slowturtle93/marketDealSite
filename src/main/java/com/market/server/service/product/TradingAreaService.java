package com.market.server.service.product;

import com.market.server.dto.Search;
import com.market.server.dto.product.TradingAreaDTO;

public interface TradingAreaService {
	
	public TradingAreaDTO getTradingArea(Search search);
	
	public int insertTradingArea(TradingAreaDTO tradingAreaDTO);
}
