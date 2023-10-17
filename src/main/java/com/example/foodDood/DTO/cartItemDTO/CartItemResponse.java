package com.example.foodDood.DTO.cartItemDTO;

import com.example.foodDood.Enum.CartFoodStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CartItemResponse {

    String foodName;

    boolean veg;

    double price;

    int quantity;
}
