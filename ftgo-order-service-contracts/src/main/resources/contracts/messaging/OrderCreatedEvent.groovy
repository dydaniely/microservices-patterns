package contracts.messaging;

org.springframework.cloud.contract.spec.Contract.make {
    label 'orderCreatedEvent'
    input {
        triggeredBy('orderCreated()')
    }

    outputMessage {
        sentTo('com.ftgo.orderservice.model.Order')
        body('''{"orderDetails":{"lineItems":[{"quantity":5,"menuItemId":"1","name":"Chicken Vindaloo","price":"12.34","total":"61.70"}],"orderTotal":"61.70","restaurantId":1, "consumerId":1511300065921}, "restaurantName" : "Ajanta"}''')
        headers {
            header('event-aggregate-type', 'com.ftgo.orderservice.model.Order')
            header('event-type', 'com.ftgo.orderservice.api.events.OrderCreatedEvent')
            header('event-aggregate-id', '99') // Matches OrderDetailsMother.ORDER_ID
        }
    }
}