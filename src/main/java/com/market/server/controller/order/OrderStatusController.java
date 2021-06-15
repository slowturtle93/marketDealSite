package com.market.server.controller.order;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.market.server.aop.LoginCheck;
import com.market.server.aop.LoginCheck.UserType;
import com.market.server.dto.order.OrderStatusDTO;
import com.market.server.service.order.Impl.OrderStatusServiceImpl;

import lombok.extern.log4j.Log4j2;



@RestController
@RequestMapping("/order/status")
@Log4j2
public class OrderStatusController {
	
	private final OrderStatusServiceImpl orderStatusService;
	
	@Autowired
    public OrderStatusController(OrderStatusServiceImpl orderStatusService) {
        this.orderStatusService = orderStatusService;
    }
	
	/**
	 * 주문상태코드를 조회한다.
	 * 
	 * @return
	 */
	@GetMapping("select")
	@LoginCheck(type = UserType.ADMIN)
	public List<OrderStatusDTO> getOrderStatus(){
		return orderStatusService.getOrderStatus();
	}
	
	/**
	 * 주문상태코드를 등록한다.
	 * 
	 * @param List<OrderStatusDTO>
	 * @return HttpStatus
	 */
	@PostMapping("insert")
	@ResponseStatus(HttpStatus.CREATED)
	@LoginCheck(type = UserType.ADMIN)
	public HttpStatus InsertOrderStatus(@RequestBody @NotNull List<OrderStatusDTO> orderStatusList){
		// 주문상태코드 등록
		orderStatusService.InsertOrderStatus(orderStatusList);
		return HttpStatus.CREATED;
	}
	
	/**
	 * 옵션 카테고리를 수정한다.
	 * 
	 * @param List<OrderStatusDTO>
	 */
	@PatchMapping("update")
	@LoginCheck(type = UserType.ADMIN)
	public void UpdateOrderStatus(@RequestBody @NotNull List<OrderStatusDTO> orderStatusList){
		// 주문상태코드 수정
		orderStatusService.UpdateOrderStatus(orderStatusList);
	}
	
}
