package com.ftgo.orderhistoryservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ftgo.orderhistoryservice.controller.model.GetOrderResponse;
import com.ftgo.orderhistoryservice.controller.model.GetOrdersResponse;
import com.ftgo.orderhistoryservice.dao.OrderHistoryDao;
import com.ftgo.orderhistoryservice.domain.OrderHistoryFilter;
import com.ftgo.orderhistoryservice.model.Order;
import com.ftgo.orderhistoryservice.model.OrderHistory;

import static java.util.stream.Collectors.toList;

/**
 * The controller class for defining the external APIs about order history.
 * 
 * @author  Wuyi Chen
 * @date    05/27/2020
 * @version 1.0
 * @since   1.0
 */
@RestController
@RequestMapping(path = "/orders")
public class OrderHistoryServiceController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private OrderHistoryDao orderHistoryDao;

	public OrderHistoryServiceController(OrderHistoryDao orderHistoryDao) {
		this.orderHistoryDao = orderHistoryDao;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<GetOrdersResponse> getOrders(@RequestParam(name = "consumerId") String consumerId) {
		logger.debug("GET /orders?consumerId={consumerId} - Get all the historical orders from the consumer");
		
		OrderHistory orderHistory = orderHistoryDao.findOrderHistory(consumerId, new OrderHistoryFilter());
		return new ResponseEntity<>(new GetOrdersResponse(
				orderHistory.getOrders().stream().map(this::makeGetOrderResponse).collect(toList()),
				orderHistory.getStartKey().orElse(null)), HttpStatus.OK);
	}

	private GetOrderResponse makeGetOrderResponse(Order order) {
		return new GetOrderResponse(order.getOrderId(), order.getStatus(), order.getRestaurantId(),
				order.getRestaurantName());
	}

	@RequestMapping(path = "/{orderId}", method = RequestMethod.GET)
	public ResponseEntity<GetOrderResponse> getOrder(@PathVariable String orderId) {
		logger.debug("GET /orders/{orderId} - Get a historical order by order ID");
		
		return orderHistoryDao.findOrder(orderId)
				.map(order -> new ResponseEntity<>(makeGetOrderResponse(order), HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
}
