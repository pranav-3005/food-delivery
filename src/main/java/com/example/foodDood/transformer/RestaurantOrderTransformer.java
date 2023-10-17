package com.example.foodDood.transformer;

import com.example.foodDood.DTO.restaurantOrderDTO.RestaurantOrderResponse;
import com.example.foodDood.model.RestaurantOrder;

public class RestaurantOrderTransformer {

    public static RestaurantOrderResponse restaurantOrderToRestaurantOrderResponse(RestaurantOrder restaurantOrder)
    {
        return RestaurantOrderResponse.builder()
                .date(restaurantOrder.getDate())
                .restaurantName(restaurantOrder.getRestaurant().getRestaurantName())
                .customerName(restaurantOrder.getCustomerName())
                .itemQuantityMap(restaurantOrder.getItemQuantityMap())
                .totalBill(restaurantOrder.getTotalBill())
                .deliveryPartnerName(restaurantOrder.getDeliveryPartner().getDeliverPartnerName())
                .build();
    }
}
