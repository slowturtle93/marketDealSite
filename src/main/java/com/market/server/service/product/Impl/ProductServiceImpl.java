package com.market.server.service.product.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.market.server.dto.Search;
import com.market.server.dto.product.ProductDTO;
import com.market.server.dto.product.ProductDetailDTO;
import com.market.server.dto.product.ProductDivisionDTO;
import com.market.server.mapper.product.ProductMapper;
import com.market.server.service.option.Impl.OptionServiceImpl;
import com.market.server.service.product.ProductService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	private ProductMapper productMapper;
	
	@Autowired
	private OptionServiceImpl optionService;
	
	@Autowired
	private TradingAreaServiceImpl tradingAreaService;
	
	@Autowired 
	private ProductDivisionServiceImpl productDivisionService;
	
	
	
	/**
	 * 본인이 등록한 상품을 조회한다.
	 */
	@Override
	public List<ProductDTO> myProductInfo(Search search) {
		return productMapper.myProductInfo(search);
	}
	
	/**
	 * 상품 상세 정보를 조회한다.
	 */
	@Override
	public ProductDetailDTO myProductDetail(Search search) {
		ProductDetailDTO productDetailDTO = new ProductDetailDTO();
		
		productDetailDTO.setProductDTO(productMapper.myProductDetail(search));         // 상품 정보 set
		productDetailDTO.setOptionList(optionService.getOption(search));               // 상품 옵션정보 set
		productDetailDTO.setTradingAreaDTO(tradingAreaService.getTradingArea(search)); // 상품 거래정보 set
		
		return productDetailDTO;
	}
	
	
	/**
	 * 상품 정보를 등록한다.
	 */
	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public void insertProduct(ProductDetailDTO productDetailDTO) {
		
		if(ProductDTO.hasNullDataBeforeRegister(productDetailDTO.getProductDTO())) { // 상품 정보 등록 시 NULL 체크
			log.error("Insert ERROR! {}", productDetailDTO.getProductDTO());
			throw new RuntimeException("Insert ERROR! 상품 정보를 확인해주세요.\n" + "productDTO : " + productDetailDTO.getProductDTO());
		}
		
		// 상품등록
		int result = productMapper.InsertProduct(productDetailDTO.getProductDTO());
		
		if(result == 1) { // 상품등록 성공인 경우
			String itemCd = productMapper.findByItemCd(productDetailDTO.getProductDTO().getItemSeq()); // 방금 등록한 상품의 상품코드 Get
			
			
			if(productDetailDTO.getOptionList() != null) { //상품옵션이 NULL이 아닌경우 상품옵션 등록진행 
				// 상품코드 Set
				for(int i = 0; i < productDetailDTO.getOptionList().size(); i++) {
					productDetailDTO.getOptionList().get(i).setItemCd(itemCd);
				}
				optionService.InsertOption(productDetailDTO.getOptionList()); // 옵션 등록
			}else { // 상품옵션 등록 실패인 경우
				log.error("Insert Product Option Error! {}", productDetailDTO.getOptionList().toString());
			    throw new RuntimeException("Insert Product Option Error");
			}
			
			Search search = new Search();
			search.add("divisionCd", productDetailDTO.getProductDTO().getDivisionCd());
			// 등록한 상품의 상품구분코드가 직거래가 가능 정보 Get
			List<ProductDivisionDTO> productDivisionDTO =  productDivisionService.getDivision(search);
			
			// 거래가능지역이 NULL이 아니고, 등록한 상품의 상품구분코드가 직거래가 가능할 경우
			if(productDetailDTO.getTradingAreaDTO() != null && "Y".equals(productDivisionDTO.get(0).getDirectYn())) {
				
				productDetailDTO.getTradingAreaDTO().setItemCd(itemCd);
				tradingAreaService.insertTradingArea(productDetailDTO.getTradingAreaDTO());
			}else if(!"Y".equals(productDivisionDTO.get(0).getDirectYn())){ // 거래 지역 등록이 불가능 한 경우
				
				log.error("Insert Product TradingArea Not Possible! {}", productDivisionDTO.get(0).getDirectYn());
			    throw new RuntimeException("Insert Product TradingArea Not Possible");
			}else { //거래지역 등록 실패인 경우
				
				log.error("Insert Product TradingArea Error! {}", productDivisionDTO.get(0).getDirectYn());
			    throw new RuntimeException("Insert Product TradingArea Error");
			}
		}else { //상품등록 실패인 경우
			log.error("Insert Product Error! {}", productDetailDTO.toString());
		    throw new RuntimeException("Insert Product Error");
		}
	}

	/**
	 * 상품 정보를 수정한다.
	 */
	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public void updateProduct(ProductDetailDTO productDetailDTO) {
		
		if(ProductDTO.hasNullDataBeforeRegister(productDetailDTO.getProductDTO())) { // 상품 정보 등록 시 NULL 체크
			log.error("Insert ERROR! {}", productDetailDTO);
			throw new RuntimeException("Insert ERROR! 상품 정보를 확인해주세요.\n" + "opCategoryNm : " + productDetailDTO);
		}
		
		// 상품정보 수정
		int result = productMapper.UpdateProduct(productDetailDTO.getProductDTO());
		
		if(result == 1) { // 상품수정 성공인 경우
			
			if(optionService.UpdateOption(productDetailDTO.getOptionList()) < 1) { 
				//상품 옵션 정보 수정 실패인 경우
				log.error("Update Product Option Error! {}", productDetailDTO.getOptionList().toString());
			    throw new RuntimeException("Update Product Option Error");
			}

			Search search = new Search();
			search.add("divisionCd", productDetailDTO.getProductDTO().getDivisionCd());
			// 등록된 상품의 구분코드가 직거래가능 여부 확인
			List<ProductDivisionDTO> productDivisionDTO =  productDivisionService.getDivision(search);
			
			// 거래가능지역이 NULL이 아니고, 등록한 상품의 상품구분코드가 직거래가 가능할 경우
			if(productDetailDTO.getTradingAreaDTO() != null && "Y".equals(productDivisionDTO.get(0).getDirectYn())) {
				
				tradingAreaService.UpdateTradingArea(productDetailDTO.getTradingAreaDTO());
			}else if(!"Y".equals(productDivisionDTO.get(0).getDirectYn())) { // 거래 지역 수정이 불가능 한 경우
				
				log.error("Update Product TradingArea Not Possible! {}", productDivisionDTO.get(0).getDirectYn());
			    throw new RuntimeException("Update Product TradingArea Not Possible");
			}else { //거래지역 등록 실패인 경우
				
				log.error("Update Product TradingArea Error! {}", productDetailDTO.getTradingAreaDTO().toString());
			    throw new RuntimeException("Update Product TradingArea Error");
			}
			
		}else { //상품정보 수정 실패인 경우
			log.error("Insert Product Error! {}", productDetailDTO.toString());
		    throw new RuntimeException("Insert Product Error");
		}
	}

	/**
	 * 등록한 상품정보를 삭제한다.
	 */
	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public void deleteProduct(Search search) {
		
		// 상품 거래지역 정보 삭제
		if(tradingAreaService.DeleteTradingArea(search) != 1) {
			log.error("Delete Trading Area Error! {}", search.get("itemCd"));
		    throw new RuntimeException("Delete Trading Area Error");
		}
		// 상품 옵션 삭제
		if(optionService.DeleteOption(search) < 1) {
			log.error("Delete Product Option Error! {}", search.get("itemCd"));
		    throw new RuntimeException("Delete Product Option Error");
		}
		// 상품 정보 삭제
		if(productMapper.DeleteProduct(search) != 1) {
			log.error("Delete Product Error! {}", search.get("itemCd"));
		    throw new RuntimeException("Delete Product Error");
		}
	}

}
