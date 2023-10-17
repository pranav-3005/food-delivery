package com.example.foodDood.transformer;

import com.example.foodDood.DTO.paymentDTO.PaymentResponse;
import com.example.foodDood.model.Payment;

public class PaymentTransformer {
    public static PaymentResponse PaymentToPaymentResponse(Payment payment)
    {
        return PaymentResponse.builder()
                .transactionId(payment.getTransactionId())
                .customerOrderId(payment.getCustomerOrderId())
                .date(payment.getDate())
                .paymentMethod(payment.getPaymentMethod())
                .totalBill(payment.getTotalBill())
                .paymentStatus(payment.getPaymentStatus())
                .build();
    }
}
