package com.example.foodDood.repository;

import com.example.foodDood.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepo extends JpaRepository<CartItem,Integer> {
}
