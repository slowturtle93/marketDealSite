package com.market.server.service.option.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.market.server.dto.Search;
import com.market.server.dto.option.OptionDTO;
import com.market.server.mapper.option.OptionMapper;
import com.market.server.service.option.OptionService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class OptionServiceImpl implements OptionService{
	
	@Autowired
	private OptionMapper optionMapper;
	
	/**
	 * 상품 옵션을 조회한다.
	 */
	@Override
	public List<OptionDTO> getOption(Search search) {
		return optionMapper.getOption(search);
	}
	
	/**
	 * 상품 옵션을 등록한다.
	 */
	@Override
	public int InsertOption(List<OptionDTO> optionList) {
		return optionMapper.InsertOption(optionList);
	}

	/**
	 * 상품 옵션을 수정한다.
	 */
	@Override
	public int UpdateOption(List<OptionDTO> optionList) {
		return optionMapper.UpdateOption(optionList);
	}

	/**
	 * 상품 옵션을 삭제한다.
	 */
	@Override
	public int DeleteOption(Search search) {
		return optionMapper.DeleteOption(search);
	}

}
