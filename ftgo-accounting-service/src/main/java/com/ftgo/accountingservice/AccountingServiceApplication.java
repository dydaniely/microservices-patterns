package com.ftgo.accountingservice;

import io.eventuate.javaclient.driver.EventuateDriverConfiguration;
import io.eventuate.jdbckafka.TramJdbcKafkaConfiguration;
import io.eventuate.tram.commands.common.ChannelMapping;
import io.eventuate.tram.commands.common.DefaultChannelMapping;
import io.eventuate.tram.commands.producer.TramCommandProducerConfiguration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.ftgo.accountingservice.domain.AccountingWebConfiguration;
import com.ftgo.accountingservice.message.AccountingServiceMessageConfiguration;

/**
 * The bootstrap class for the accounting service.
 * 
 * @author  Wuyi Chen
 * @date    09/16/2019
 * @version 1.0
 * @since   1.0
 */
@Configuration
@EnableAutoConfiguration
@Import({
	AccountingServiceMessageConfiguration.class, 
	AccountingWebConfiguration.class,
	TramCommandProducerConfiguration.class, 
	EventuateDriverConfiguration.class, 
	TramJdbcKafkaConfiguration.class})
public class AccountingServiceApplication {
	@Bean
	public ChannelMapping channelMapping() {
		return new DefaultChannelMapping.DefaultChannelMappingBuilder().build();
	}

	public static void main(String[] args) {
		SpringApplication.run(AccountingServiceApplication.class, args);
	}
}