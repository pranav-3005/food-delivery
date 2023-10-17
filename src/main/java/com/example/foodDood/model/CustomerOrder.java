package com.example.foodDood.model;

import com.example.foodDood.Enum.PaymentMethod;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CustomerOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    Date date;

    String restaurantName;

    //food names , req quantity in cartItemList from cart
    //HashMap<String,Integer> itemQuantityMap=new HashMap<>(); // < Item name, req quantity >
    String itemQuantityMap=""; // format to store food and quantity : "foodid1:reqQuantity1,foodid2:reqQuantity,foodid3:reqQuantity3"

    double totalBill;

    @Enumerated(value = EnumType.STRING)
    PaymentMethod paymentMethod;


    @ManyToOne
    @JoinColumn
    Customer customer;

    @ManyToOne
    @JoinColumn
    DeliveryPartner deliveryPartner;
}
