package com.market.server.mapper.option;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.market.server.dto.Search;
import com.market.server.dto.option.OptionDTO;

@Mapper
public interface OptionMapper {
	
	public List<OptionDTO> getOption(Search search);
	
	public int InsertOption(@Param("optionList") List<OptionDTO> optionList);
	
}
