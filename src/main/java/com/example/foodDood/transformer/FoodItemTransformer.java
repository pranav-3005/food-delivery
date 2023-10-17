package com.example.foodDood.transformer;

import com.example.foodDood.DTO.foodItemDTO.FoodItemRequest;
import com.example.foodDood.DTO.foodItemDTO.FoodItemResponse;
import com.example.foodDood.model.FoodItem;

public class FoodItemTransformer {

    public static FoodItem FoodItemRequestToFoodItem(FoodItemRequest foodItemRequest)
    {
        return FoodItem.builder()
                .foodName(foodItemRequest.getFoodName())
                .veg(foodItemRequest.isVeg())
                .price(foodItemRequest.getPrice())
                .quantity(foodItemRequest.getQuantity())
                .build();
    }

    public static FoodItemResponse FoodItemToFoodItemResponse(FoodItem foodItem)
    {
        return FoodItemResponse.builder()
                .foodItemId(foodItem.getId())
                .foodName(foodItem.getFoodName())
                .price(foodItem.getPrice())
                .availableQuantity(foodItem.getQuantity())
                .veg(foodItem.isVeg())
                .build();
    }
}
