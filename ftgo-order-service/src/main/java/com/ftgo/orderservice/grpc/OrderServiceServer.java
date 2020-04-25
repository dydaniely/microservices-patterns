package com.ftgo.orderservice.grpc;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ftgo.common.model.Address;
import com.ftgo.orderservice.controller.model.MenuItemIdAndQuantity;
import com.ftgo.orderservice.model.DeliveryInformation;
import com.ftgo.orderservice.model.Order;
import com.ftgo.orderservice.service.OrderService;

import org.apache.commons.lang.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * The gRPC server of the order service.
 * 
 * @author  Wuyi Chen
 * @date    04/24/2020
 * @version 1.0
 * @since   1.0
 */
public class OrderServiceServer {
	private static final Logger logger = LoggerFactory.getLogger(OrderServiceServer.class);

	private int port = 50051;
	private Server server;
	private OrderService orderService;

	public OrderServiceServer(OrderService orderService) {
		this.orderService = orderService;
	}

	@PostConstruct
	public void start() throws IOException {
		server = ServerBuilder.forPort(port).addService((BindableService) new OrderServiceImpl()).build().start();
		logger.info("Server started, listening on {}", port);
	}

	@PreDestroy
	public void stop() {
		if (server != null) {
			logger.info("*** shutting down gRPC server since JVM is shutting down");
			server.shutdown();
			logger.info("*** server shut down");
		}
	}

	/**
	 * The implementation of the remote methods.
	 * 
	 * @author  Wuyi Chen
	 * @date    04/24/2020
	 * @version 1.0
	 * @since   1.0
	 */
	public class OrderServiceImpl extends OrderServiceGrpc.OrderServiceImplBase {
		@Override
		public void createOrder(OrderServiceProto.CreateOrderRequest request, StreamObserver<OrderServiceProto.CreateOrderReply> responseObserver) {
			List<com.ftgo.orderservice.grpc.OrderServiceProto.LineItem> lineItemsList = request.getLineItemsList();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); 
			LocalDateTime deliveryTime = LocalDateTime.parse(request.getDeliveryTime(), formatter);

			Order order = orderService.createOrder(request.getConsumerId(), request.getRestaurantId(),
					new DeliveryInformation(deliveryTime, convertAddress(request.getDeliveryAddress())),
					lineItemsList.stream().map(x -> new MenuItemIdAndQuantity(x.getMenuItemId(), x.getQuantity())).collect(toList()));
			OrderServiceProto.CreateOrderReply reply = OrderServiceProto.CreateOrderReply.newBuilder().setOrderId(order.getId()).build();
			responseObserver.onNext(reply);
			responseObserver.onCompleted();
		}

		@Override
		public void cancelOrder(OrderServiceProto.CancelOrderRequest request, StreamObserver<OrderServiceProto.CancelOrderReply> responseObserver) {
			OrderServiceProto.CancelOrderReply reply = OrderServiceProto.CancelOrderReply.newBuilder().setMessage("Hello " + request.getName()).build();
			responseObserver.onNext(reply);
			responseObserver.onCompleted();
		}

		@Override
		public void reviseOrder(com.ftgo.orderservice.grpc.OrderServiceProto.ReviseOrderRequest request, StreamObserver<OrderServiceProto.ReviseOrderReply> responseObserver) {
			OrderServiceProto.ReviseOrderReply reply = OrderServiceProto.ReviseOrderReply.newBuilder().setMessage("Hello " + request.getName()).build();
			responseObserver.onNext(reply);
			responseObserver.onCompleted();
		}
	}

	/**
	 * Convert OrderServiceProto.Address to com.ftgo.common.model.Address.
	 * 
	 * @param  address
	 *         The object of {@code OrderServiceProto.Address}
	 *         
	 * @return  The object of {@code com.ftgo.common.model.Address}
	 */
	private Address convertAddress(OrderServiceProto.Address address) {
		return new Address(address.getStreet1(), nullIfBlank(address.getStreet2()), address.getCity(), address.getState(), address.getZip());
	}
	
	private String nullIfBlank(String s) {
		return StringUtils.isBlank(s) ? null : s;
	}
}