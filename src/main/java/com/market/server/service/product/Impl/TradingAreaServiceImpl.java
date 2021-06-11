package com.market.server.service.product.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.market.server.dto.Search;
import com.market.server.dto.product.TradingAreaDTO;
import com.market.server.mapper.product.TradingAreaMapper;
import com.market.server.service.product.TradingAreaService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class TradingAreaServiceImpl implements TradingAreaService{
	
	@Autowired
	private TradingAreaMapper tradingAreaMapper;

	/**
	 * 상품 거래가능 지역을 조회한다.
	 */
	@Override
	public TradingAreaDTO getTradingArea(Search search) {
		return tradingAreaMapper.getTradingArea(search);
	}
	
	/**
	 * 상품 거래지역 정보를 등록한다.(상품구분코드 중 직거래가능한 경우만 해당) 
	 */
	@Override
	public int insertTradingArea(TradingAreaDTO tradingAreaDTO) {
		return tradingAreaMapper.InsertTradingArea(tradingAreaDTO);
	}

}
