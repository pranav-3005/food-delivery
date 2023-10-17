package com.example.foodDood;

import com.example.foodDood.model.Cart;
import com.example.foodDood.repository.CartRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class FoodDoodApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodDoodApplication.class, args);
	}

}
