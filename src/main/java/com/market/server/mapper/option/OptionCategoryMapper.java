package com.market.server.mapper.option;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.market.server.dto.Search;
import com.market.server.dto.option.OptionCategoryDTO;

@Mapper
public interface OptionCategoryMapper {
	
	public List<OptionCategoryDTO> getOpCategory(Search search);
	
	public int InsertOpCategory(@Param("optionCategoryList") List<OptionCategoryDTO> optionCategoryList);
	
	public int UpdateOpCategory(@Param("optionCategoryList") List<OptionCategoryDTO> optionCategoryList);
	
	public int DeleteOpCategory(@Param("optionCategoryList") List<OptionCategoryDTO> optionCategoryList);
	
	public int isDuplicatedNm(@Param("opCategoryNm") String opCategoryNm);
}
