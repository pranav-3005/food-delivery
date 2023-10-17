package com.example.foodDood.DTO.paymentDTO;

import com.example.foodDood.Enum.PaymentMethod;
import com.example.foodDood.Enum.PaymentStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class PaymentResponse {
    String transactionId;

    int customerOrderId;

    Date date;

    PaymentMethod paymentMethod;

    double totalBill;

    PaymentStatus paymentStatus;
}
