package com.example.foodDood.DTO.foodItemDTO;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class FoodItemResponse {

    int foodItemId;

    String foodName;

    boolean veg;

    int availableQuantity;

    double price;


}
