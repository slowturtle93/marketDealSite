package com.market.server.service.order.Impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.market.server.dao.OrderLogDao;
import com.market.server.dto.Search;
import com.market.server.dto.order.OrderDTO;
import com.market.server.dto.order.OrderDetailDTO;
import com.market.server.dto.product.ProductDetailDTO;
import com.market.server.error.exception.TotalPriceMismatchException;
import com.market.server.mapper.order.OrderMapper;
import com.market.server.service.order.OrderService;
import com.market.server.service.product.Impl.ProductServiceImpl;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	private OrderMapper orderMapper;

	@Autowired
	private ProductServiceImpl productService;
	
	@Autowired
	private OrderLogDao orderLogDao;

	/**
	 * 상품을 주문한다.
	 */
	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public void doOrder(OrderDTO orderDTO) {
		
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		Date time = new Date();
		// 주문코드 조합 : 주문코드 + 로그인번호 + 현재시간
		String orderCd = "OD" + orderDTO.getLoginNo() + format.format(time);
		orderDTO.setOrderCd(orderCd);
		
		Search search = new Search();
		search.add("itemCd", orderDTO.getItemCd());
		ProductDetailDTO productDTO = productService.productDetail(search);
		
		// 총결제금액 확인
		int orderCnt       = orderDTO.getOrderCnt();                        // 주문수량
		long orderPrice    = productDTO.getProductDTO().getItemPrice();     // 주문금액
		long deliveryPrice = productDTO.getProductDTO().getDeliveryPrice(); // 배송금액
		long discountPrice = orderDTO.getDiscountPrice();                   // 할인금액
		long totalPrice    = (orderCnt * orderPrice) + deliveryPrice - discountPrice; // server total price
		
		if(totalPrice != orderDTO.getTotalPrice()) {
			log.error("Total Price Mismatch! client price : {}, server price : {},",
			          orderDTO.getTotalPrice(), totalPrice);
			throw new TotalPriceMismatchException("Total Price Mismatch!");
		}
		
		int result = orderMapper.doOrder(orderDTO);
		
		if(result != 1) {
			log.error("Insert ERROR! {}", orderDTO);
			throw new RuntimeException("Insert ERROR! 주문정보를 확인해주세요.\n" + "orderDTO : " + orderDTO);
		}
	}

	@Override
	public List<OrderDetailDTO> getOrderList(int loginNo) {
		return orderMapper.getOrderList(loginNo);
	}

	/**
	 * 주문상품의 주문상태코드를 변경한다.
	 */
	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public void updateOrderStatus(String orderCd, String orderStatusCd) {
		
		int result = orderMapper.updateOrderStatus(orderCd, orderStatusCd);
		
		if(result != 1) {
			log.error("Update ERROR! {}", orderCd);
			throw new RuntimeException("Update ERROR! 주문번호를 확인해주세요.\n" + "orderCd : " + orderCd);
		}else {
			//현재시간 계산
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date time = new Date();
			
			orderLogDao.addOrder(new OrderDTO(orderCd, orderStatusCd, format.format(time)));
		}
	}
}
