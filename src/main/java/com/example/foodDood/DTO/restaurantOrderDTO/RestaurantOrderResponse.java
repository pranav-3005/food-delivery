package com.example.foodDood.DTO.restaurantOrderDTO;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class RestaurantOrderResponse {

    Date date;

    String restaurantName;

    String customerName;

    String itemQuantityMap="";

    double totalBill;

    String deliveryPartnerName;
}
