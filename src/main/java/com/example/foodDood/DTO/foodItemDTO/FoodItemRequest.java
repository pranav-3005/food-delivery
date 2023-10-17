package com.example.foodDood.DTO.foodItemDTO;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class FoodItemRequest {

    String foodName;

    double price;

    boolean veg;

    int quantity;

}
