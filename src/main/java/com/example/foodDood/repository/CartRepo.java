package com.example.foodDood.repository;

import com.example.foodDood.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepo extends JpaRepository<Cart,Integer> {

    List<Cart> findByRestaurantName(String restaurantName);
}
