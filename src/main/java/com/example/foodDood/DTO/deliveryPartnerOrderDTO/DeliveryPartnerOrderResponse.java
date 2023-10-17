package com.example.foodDood.DTO.deliveryPartnerOrderDTO;

import com.example.foodDood.Enum.OrderType;
import com.example.foodDood.Enum.PaymentMethod;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class DeliveryPartnerOrderResponse {

    Date date;

    OrderType orderType;

    String customerName;

    String customerContactNo;

    String restaurantName;

    double totalBill;

}
