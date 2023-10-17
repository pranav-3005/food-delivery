package com.example.foodDood.transformer;

import com.example.foodDood.DTO.cartItemDTO.CartItemRequest;
import com.example.foodDood.DTO.cartItemDTO.CartItemResponse;
import com.example.foodDood.DTO.foodItemDTO.FoodItemRequest;
import com.example.foodDood.model.CartItem;
import com.example.foodDood.model.FoodItem;
import com.example.foodDood.repository.FoodItemRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class CartItemTransformer {


    public static CartItem cartItemRequestToCartItem(FoodItem foodItem, CartItemRequest cartItemRequest)
    {
        return CartItem.builder()
                .foodName(foodItem.getFoodName())
                .veg(foodItem.isVeg())
                .price(foodItem.getPrice())
                .quantity(cartItemRequest.getRequiredQuantity())
                .foodItemId(cartItemRequest.getFoodItemId())
                .build();
        //status set at service class
    }

    public static CartItemResponse cartItemToCartItemResponse(CartItem cartItem)
    {
        return CartItemResponse.builder()
                .foodName(cartItem.getFoodName())
                .veg(cartItem.isVeg())
                .price(cartItem.getPrice())
                .quantity(cartItem.getQuantity())
                .build();
    }
}
