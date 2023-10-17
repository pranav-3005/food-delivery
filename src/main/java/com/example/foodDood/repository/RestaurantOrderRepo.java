package com.example.foodDood.repository;

import com.example.foodDood.model.Restaurant;
import com.example.foodDood.model.RestaurantOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantOrderRepo extends JpaRepository<RestaurantOrder,Integer> {

    List<RestaurantOrder> findByRestaurant(Restaurant restaurant);
}
