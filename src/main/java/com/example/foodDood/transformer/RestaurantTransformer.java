package com.example.foodDood.transformer;

import com.example.foodDood.DTO.restaurantDTO.RestaurantRequest;
import com.example.foodDood.DTO.restaurantDTO.RestaurantResponse;
import com.example.foodDood.model.FoodItem;
import com.example.foodDood.model.Restaurant;

import java.util.ArrayList;

public class RestaurantTransformer {
    public static Restaurant RestaurantRequestToRestaurant(RestaurantRequest restaurantRequest)
    {
        return Restaurant.builder()
                .restaurantName(restaurantRequest.getRestaurantName())
                .restaurantCategory(restaurantRequest.getRestaurantCategory())
                .contactNumber(restaurantRequest.getContactNumber())
                .menu(new ArrayList<FoodItem>())
                .build();
    }

    public static RestaurantResponse RestaurantToRestaurantResponse(Restaurant restaurant)
    {
        return RestaurantResponse.builder()
                .restaurantName(restaurant.getRestaurantName())
                .restaurantCategory(restaurant.getRestaurantCategory())
                .contactNumber(restaurant.getContactNumber())
                .opened(restaurant.isOpened())
                .build();
    }
}
