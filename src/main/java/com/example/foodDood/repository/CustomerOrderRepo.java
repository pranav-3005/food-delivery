package com.example.foodDood.repository;

import com.example.foodDood.model.Customer;
import com.example.foodDood.model.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerOrderRepo extends JpaRepository<CustomerOrder,Integer> {

    List<CustomerOrder> findByCustomer(Customer customer);

    List<CustomerOrder> findByRestaurantName(String restaurantName);
}
