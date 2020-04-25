package com.ftgo.orderservice.message;

import io.eventuate.tram.events.subscriber.DomainEventDispatcher;
import io.eventuate.tram.events.subscriber.DomainEventDispatcherFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.ftgo.orderservice.domain.OrderServiceWithRepositoriesConfiguration;
import com.ftgo.orderservice.event.OrderServiceEventConsumer;
import com.ftgo.orderservice.service.OrderService;

@Configuration
@Import({ OrderServiceWithRepositoriesConfiguration.class, DomainEventDispatcherFactory.class })
public class OrderServiceMessageConfiguration {
	@Bean
	public OrderServiceEventConsumer orderEventConsumer(OrderService orderService) {
		return new OrderServiceEventConsumer(orderService);
	}

	@Bean
	public DomainEventDispatcher domainEventDispatcher(OrderServiceEventConsumer orderEventConsumer, DomainEventDispatcherFactory domainEventDispatcherFactory) {
	    return domainEventDispatcherFactory.make("orderServiceEvents", orderEventConsumer.domainEventHandlers());
	}
}
