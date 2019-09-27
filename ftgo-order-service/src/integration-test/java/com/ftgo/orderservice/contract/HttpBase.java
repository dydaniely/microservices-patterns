package com.ftgo.orderservice.contract;

import io.eventuate.common.json.mapper.JSonMapper;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

import org.junit.Before;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import com.ftgo.common.domain.CommonJsonMapperInitializer;
import com.ftgo.orderservice.OrderDetailsMother;
import com.ftgo.orderservice.controller.OrderController;
import com.ftgo.orderservice.repository.OrderRepository;
import com.ftgo.orderservice.service.OrderService;

import java.util.Optional;

import static java.util.Optional.empty;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public abstract class HttpBase {

	private StandaloneMockMvcBuilder controllers(Object... controllers) {
		CommonJsonMapperInitializer.registerMoneyModule();
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(JSonMapper.objectMapper);
		return MockMvcBuilders.standaloneSetup(controllers).setMessageConverters(converter);
	}

	@Before
	public void setup() {
		OrderService orderService = mock(OrderService.class);
		OrderRepository orderRepository = mock(OrderRepository.class);
		OrderController orderController = new OrderController(orderService, orderRepository);

		when(orderRepository.findById(OrderDetailsMother.ORDER_ID)).thenReturn(Optional.of(OrderDetailsMother.CHICKEN_VINDALOO_ORDER));
		when(orderRepository.findById(555L)).thenReturn(empty());
		RestAssuredMockMvc.standaloneSetup(controllers(orderController));
	}
}