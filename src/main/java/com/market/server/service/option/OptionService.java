package com.market.server.service.option;

import java.util.List;

import com.market.server.dto.option.OptionDTO;

public interface OptionService {
	
	public int InsertOption(List<OptionDTO> optionList);
}
