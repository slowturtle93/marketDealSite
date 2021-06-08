package com.market.server.service.option;

import java.util.List;

import com.market.server.dto.Search;
import com.market.server.dto.option.OptionCategoryDTO;

public interface OptionCategoryService {
	
	public List<OptionCategoryDTO> getOpCategory(Search search);
	
	public int InsertOpCategory(List<OptionCategoryDTO> optionCategoryList);
	
	public void UpdateOpCategory(List<OptionCategoryDTO> optionCategoryList);
	
	public void DeleteOpCategory(List<OptionCategoryDTO> optionCategoryList);
	
	public boolean isDuplicatedNm(String categoryNm);
}
