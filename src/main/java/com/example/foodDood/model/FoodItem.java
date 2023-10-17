package com.example.foodDood.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "foodItem")
@Builder
public class FoodItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String foodName;

    double price;

    boolean veg;

    int quantity;

                        //set available true at adding a food item
    boolean available; //if quantity==0, available=false

    @ManyToOne
    @JoinColumn
    Restaurant restaurant;
}
