package com.ftgo.restaurantservice.configuration;

import io.eventuate.tram.events.publisher.TramEventsPublisherConfiguration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.ftgo.common.configuration.CommonConfiguration;
import com.ftgo.restaurantservice.service.RestaurantService;

/**
 * The configuration class to instantiate and wire the domain service class.
 * 
 * @author  Wuyi Chen
 * @date    05/14/2020
 * @version 1.0
 * @since   1.0
 */
@Configuration
@EnableJpaRepositories
@EnableTransactionManagement
@EntityScan
@Import({ TramEventsPublisherConfiguration.class, CommonConfiguration.class })
public class RestaurantServiceConfiguration {
	@Bean
	public RestaurantService restaurantService() {
		return new RestaurantService();
	}
}