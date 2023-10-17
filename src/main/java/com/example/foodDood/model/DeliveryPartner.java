package com.example.foodDood.model;

import com.example.foodDood.Enum.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class DeliveryPartner {
    //parent class
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String deliverPartnerName;

    int age;

    @Enumerated(value = EnumType.STRING)
    Gender gender;

    @Column(unique = true,nullable = false)
    String contactNumber;

    @Email
    String email;

    @Column(unique = true,nullable = false)
    String vehicleNumber;

    @OneToMany(mappedBy = "deliveryPartner",cascade = CascadeType.ALL)
    List<CustomerOrder> customerOrderList =new ArrayList<>();

    @OneToMany(mappedBy = "deliveryPartner",cascade = CascadeType.ALL)
    List<RestaurantOrder> restaurantOrderList=new ArrayList<>();
}
