package com.ftgo.orderhistoryservice.domain;

import org.joda.time.DateTime;

import com.ftgo.orderservice.api.model.OrderState;

import java.util.Optional;
import java.util.Set;

import static java.util.Collections.emptySet;

/**
 * Filter for historical orders
 * 
 * @author  Wuyi Chen
 * @date    04/16/2019
 * @version 1.0
 * @since   1.0
 */
public class OrderHistoryFilter {
	private DateTime             since         = DateTime.now().minusDays(30);
	private Optional<OrderState> status        = Optional.empty();
	private Set<String>          keywords      = emptySet();
	private Optional<String>     startKeyToken = Optional.empty();
	private Optional<Integer>    pageSize      = Optional.empty();

	public DateTime getSince() {
		return since;
	}

	public OrderHistoryFilter withStatus(OrderState status) {
		this.status = Optional.of(status);
		return this;
	}

	public Optional<OrderState> getStatus() {
		return status;
	}

	public OrderHistoryFilter withStartKeyToken(Optional<String> startKeyToken) {
		this.startKeyToken = startKeyToken;
		return this;
	}

	public OrderHistoryFilter withKeywords(Set<String> keywords) {
		this.keywords = keywords;
		return this;
	}

	public Set<String> getKeywords() {
		return keywords;
	}

	public Optional<String> getStartKeyToken() {
		return startKeyToken;
	}

	public OrderHistoryFilter withPageSize(int pageSize) {
		this.pageSize = Optional.of(pageSize);
		return this;
	}

	public Optional<Integer> getPageSize() {
		return pageSize;
	}
}
