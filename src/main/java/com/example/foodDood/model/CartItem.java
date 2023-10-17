package com.example.foodDood.model;

import com.example.foodDood.Enum.CartFoodStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "cartItem")
@Builder
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    //present in foodItem
    String foodName;

    int foodItemId;

    boolean veg;

    double price;



    //get from user
    int quantity;


    //set in b.logic at service class
//    @Enumerated(value = EnumType.STRING)
//    CartFoodStatus status;

    @ManyToOne
    @JoinColumn
    Cart cart;


}
