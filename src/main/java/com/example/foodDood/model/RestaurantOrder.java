package com.example.foodDood.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class RestaurantOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    Date date;

    String customerName;

    //HashMap<String,Integer> itemQuantityMap=new HashMap<>(); // < Item name, req quantity >
    String itemQuantityMap=""; // format to store food and quantity : "foodid1:reqQuantity1,foodid2:reqQuantity,foodid3:reqQuantity3"


    double totalBill;

    @ManyToOne
    @JoinColumn
    Restaurant restaurant;

    @ManyToOne
    @JoinColumn
    DeliveryPartner deliveryPartner;


}
