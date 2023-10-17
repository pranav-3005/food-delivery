package com.example.foodDood.repository;

import com.example.foodDood.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepo extends JpaRepository<Restaurant,Integer> {

    Restaurant findByrestaurantName(String restaurantName);
}
