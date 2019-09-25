package com.ftgo.orderservice.api.event;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.ftgo.orderservice.api.model.OrderDetails;

public class OrderCreatedEvent implements OrderDomainEvent {
	private OrderDetails orderDetails;
	private String       restaurantName;

	public OrderCreatedEvent(OrderDetails orderDetails, String restaurantName) {
		this.orderDetails = orderDetails;
		this.restaurantName = restaurantName;
	}

	public OrderDetails getOrderDetails()                          { return orderDetails;                  }
	public void         setOrderDetails(OrderDetails orderDetails) { this.orderDetails = orderDetails;     }
	public String       getRestaurantName()                        { return restaurantName;                }
	public void         setRestaurantName(String restaurantName)   { this.restaurantName = restaurantName; }

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
}