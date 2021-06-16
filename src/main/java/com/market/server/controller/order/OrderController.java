package com.market.server.controller.order;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.market.server.aop.LoginCheck;
import com.market.server.aop.LoginCheck.UserType;
import com.market.server.dto.order.OrderDTO;
import com.market.server.dto.order.OrderDetailDTO;
import com.market.server.service.order.Impl.OrderServiceImpl;
import com.market.server.utils.SessionUtil;

import lombok.extern.log4j.Log4j2;



@RestController
@RequestMapping("/order/")
@Log4j2
public class OrderController {
	
	private final OrderServiceImpl orderService;
	
	@Autowired
    public OrderController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }
	
	/**
	 * 상품을 주문한다.
	 * 
	 * @param session
	 * @param orderDTO
	 * @return
	 */
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@LoginCheck(type = UserType.USER)
	public HttpStatus doOrder(HttpSession session, @RequestBody OrderDTO orderDTO) {
		orderDTO.setLoginNo(SessionUtil.getLoginUserNo(session));
		orderService.doOrder(orderDTO);
		return HttpStatus.OK;
	}
	
	/**
	 * 본인이 주문한 상품을 조회한다.
	 * 
	 * @param session
	 * @return
	 */
	@GetMapping("myInfo")
	@LoginCheck(type = UserType.USER)
	public List<OrderDetailDTO> getOrderList(HttpSession session) {
		List<OrderDetailDTO> orderDetailDTO = orderService.getOrderList(SessionUtil.getLoginUserNo(session));
		return orderDetailDTO;
	}
	
	
	/**
	 * 주문상태코드를 변경한다.
	 * 
	 * @param orderCd
	 * @param orderStatusCd
	 */
	@PatchMapping("status/{orderCd}/{orderStatusCd}")
	@LoginCheck(type = UserType.ADMIN)
	public void updateOrderStatus(@PathVariable("orderCd") String orderCd, 
			                      @PathVariable("orderStatusCd") String orderStatusCd) {
		
		orderService.updateOrderStatus(orderCd, orderStatusCd);
	}
}
