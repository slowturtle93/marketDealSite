package com.market.server.controller.option;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.market.server.aop.LoginCheck;
import com.market.server.aop.LoginCheck.UserType;
import com.market.server.dto.Search;
import com.market.server.dto.option.OptionCategoryDTO;
import com.market.server.service.option.Impl.OptionCategoryServiceImpl;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;



@RestController
@RequestMapping("/option/category/")
@Log4j2
public class OptionCategoryController {
	
	private final OptionCategoryServiceImpl optionCategoryService;
	
	@Autowired
    public OptionCategoryController(OptionCategoryServiceImpl optionCategoryService) {
        this.optionCategoryService = optionCategoryService;
    }
	
	/**
	 * 옵션 카테고리를 조회한다.
	 * 
	 * @return
	 */
	@GetMapping("select")
	@LoginCheck(type = UserType.ADMIN)
	public List<OptionCategoryDTO> getOpCategory(@RequestBody OptionCategoryReqeust optionCategoryReqeust){
		Search search = new Search();
		
		search.add("delYn"  , optionCategoryReqeust.getDelYn());  // 삭제여부
		search.add("dispYn" , optionCategoryReqeust.getDispYn()); // 전시여부d
		
		search.add("pg"  , optionCategoryReqeust.getPg());   // 현재페이제
		search.add("pgSz", optionCategoryReqeust.getPgSz()); // 한 페이지당 Row 수
		search.setRow();                                     // 페이지 계산
		
		return optionCategoryService.getOpCategory(search);
	}
	
	/**
	 * 옵션 카테고리를 등록한다.
	 * 
	 * @param List<OptionCategoryDTO>
	 * @return HttpStatus
	 */
	@PostMapping("insert")
	@ResponseStatus(HttpStatus.CREATED)
	@LoginCheck(type = UserType.ADMIN)
	public HttpStatus InsertOpCategory(@RequestBody @NotNull List<OptionCategoryDTO> opCategoryList){
		// 옵션 카테고리 등록
		optionCategoryService.InsertOpCategory(opCategoryList);
		return HttpStatus.CREATED;
	}
	
	/**
	 * 옵션 카테고리를 수정한다.
	 * 
	 * @param List<OptionCategoryDTO>
	 */
	@PatchMapping("update")
	@LoginCheck(type = UserType.ADMIN)
	public void UpdateOpCategory(@RequestBody @NotNull List<OptionCategoryDTO> opCategoryList){
		// 옵션 카테고리 수정
		optionCategoryService.UpdateOpCategory(opCategoryList);
	}
	
	
	/**
	 * 옵션 카테고리를 삭제한다.
	 * 
	 * @param List<OptionCategoryDTO>
	 */
	@DeleteMapping("delete")
	@LoginCheck(type = UserType.ADMIN)
	public void DeleteOpCategory( @RequestBody @NotNull List<OptionCategoryDTO> opCategoryList){
		// 옵션 카테고리 삭제
		optionCategoryService.DeleteOpCategory(opCategoryList);
	}
	
	/*======================= reqeust 객체 ======================= */
	
	@Getter
	@Setter
	private static class OptionCategoryReqeust {
		private String delYn;
		private String dispYn;
		@NonNull
		private int pg;
		@NonNull
		private int pgSz;
	}
	
}
