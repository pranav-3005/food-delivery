package com.example.foodDood.repository;

import com.example.foodDood.model.FoodItem;
import com.example.foodDood.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodItemRepo extends JpaRepository<FoodItem,Integer> {

    List<FoodItem> findByRestaurant(Restaurant restaurant);
}
