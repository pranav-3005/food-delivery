package com.example.foodDood.exception;

public class RestaurantNotFoundException extends RuntimeException{
    public RestaurantNotFoundException(String message)
    {
        super(message);
    }
}
