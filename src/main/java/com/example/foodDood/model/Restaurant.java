package com.example.foodDood.model;

import com.example.foodDood.Enum.RestaurantCategory;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "restaurant")
@Builder
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(unique = true)
    String restaurantName;

    @Enumerated(value = EnumType.STRING)
    RestaurantCategory restaurantCategory;

    boolean opened; //set true ,while adding restaurant

    @Size(min = 10,max =10)
    @Column(unique = true,nullable = false)
    String contactNumber;

    @OneToMany(mappedBy = "restaurant",cascade = CascadeType.ALL)
    List<FoodItem> menu=new ArrayList<>();

    @OneToMany(mappedBy = "restaurant",cascade = CascadeType.ALL)
    List<RestaurantOrder> restaurantOrderList=new ArrayList<>();


    //int totalOrder;

    //double DiscounttotalPrice;

    //bill model
        // billId,
        // food item list
        // restaurantid
        // bill TotalValue
        // orderDate
        // Deliverypartner
}
