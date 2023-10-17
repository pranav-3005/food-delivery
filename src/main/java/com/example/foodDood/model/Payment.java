package com.example.foodDood.model;

import com.example.foodDood.Enum.PaymentMethod;
import com.example.foodDood.Enum.PaymentStatus;
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
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    //uuid
    @Column(unique = true,nullable = false)
    String transactionId;

    int customerOrderId;

    Date date;

    @Enumerated(value = EnumType.STRING)
    PaymentMethod paymentMethod;

    double totalBill;

    @Enumerated(value = EnumType.STRING)
    PaymentStatus paymentStatus;

    @ManyToOne
    @JoinColumn
    Customer customer;
}
