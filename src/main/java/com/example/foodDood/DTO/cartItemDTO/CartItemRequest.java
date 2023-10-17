package com.example.foodDood.DTO.cartItemDTO;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CartItemRequest {

    int foodItemId;

    int requiredQuantity;

}
