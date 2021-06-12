package com.market.server.service.option;

import java.util.List;

import com.market.server.dto.Search;
import com.market.server.dto.option.OptionDTO;

public interface OptionService {
	
	public List<OptionDTO> getOption(Search search);  
	
	public int InsertOption(List<OptionDTO> optionList);
	
	public int UpdateOption(List<OptionDTO> optionList);
	
	public int DeleteOption(Search search);
	
}
