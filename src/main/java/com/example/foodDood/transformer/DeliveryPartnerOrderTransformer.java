package com.example.foodDood.transformer;

import com.example.foodDood.DTO.deliveryPartnerOrderDTO.DeliveryPartnerOrderResponse;
import com.example.foodDood.Enum.OrderType;
import com.example.foodDood.Enum.PaymentMethod;
import com.example.foodDood.model.CustomerOrder;
import com.example.foodDood.model.DeliveryPartner;

public class DeliveryPartnerOrderTransformer {

    public static DeliveryPartnerOrderResponse CustomerOrderToDeliveryPartnerOrderResponse(CustomerOrder customerOrder)
    {

        OrderType orderType=OrderType.PREPAID;
        if(customerOrder.getPaymentMethod().equals(PaymentMethod.COD))
            orderType=OrderType.POSTPAID;


        return DeliveryPartnerOrderResponse.builder()
                .orderType(orderType)
                .date(customerOrder.getDate())
                .customerName(customerOrder.getCustomer().getCustomerName())
                .restaurantName(customerOrder.getRestaurantName())
                .totalBill(customerOrder.getTotalBill())
                .customerContactNo(customerOrder.getCustomer().getContactNo())
                .build();
    }
}
