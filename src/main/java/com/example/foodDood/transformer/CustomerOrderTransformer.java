package com.example.foodDood.transformer;

import com.example.foodDood.DTO.customerOrderDTO.CustomerOrderResponse;
import com.example.foodDood.model.CustomerOrder;

public class CustomerOrderTransformer {
    public static CustomerOrderResponse CustomerOrderToCustomerOrderResponse(CustomerOrder customerOrder) {
        return CustomerOrderResponse.builder()
                .date(customerOrder.getDate())
                .restaurantName(customerOrder.getRestaurantName())
                .itemQuantityMap(customerOrder.getItemQuantityMap())
                .totalBill(customerOrder.getTotalBill())
                .deliveryPartnerName(customerOrder.getDeliveryPartner().getDeliverPartnerName())
                .paymentMethod(customerOrder.getPaymentMethod())
                .build();
    }
}
