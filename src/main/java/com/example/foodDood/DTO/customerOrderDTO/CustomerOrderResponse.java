package com.example.foodDood.DTO.customerOrderDTO;

import com.example.foodDood.Enum.PaymentMethod;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CustomerOrderResponse {
    Date date;

    String restaurantName;

    String itemQuantityMap="";

    double totalBill;

    PaymentMethod paymentMethod;

    String deliveryPartnerName;
}
