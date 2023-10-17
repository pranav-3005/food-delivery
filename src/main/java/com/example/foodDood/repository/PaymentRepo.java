package com.example.foodDood.repository;

import com.example.foodDood.model.Customer;
import com.example.foodDood.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepo extends JpaRepository<Payment,Integer> {

    List<Payment> findByCustomer(Customer customer);
}
