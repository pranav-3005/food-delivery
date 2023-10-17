package com.example.foodDood.transformer;


import com.example.foodDood.DTO.customerDTO.CustomerRequest;
import com.example.foodDood.DTO.customerDTO.CustomerResponse;
import com.example.foodDood.model.Customer;

import java.util.ArrayList;

public class CustomerTransformer {

    public static Customer CustomerRequestToCustomer(CustomerRequest customerRequest)
    {
        return Customer.builder()
                .customerName(customerRequest.getCustomerName())
                .contactNo(customerRequest.getContactNo())
                .email(customerRequest.getEmail())
                .gender(customerRequest.getGender())
                .cartList(new ArrayList<>())
                .build();
    }

    public static CustomerResponse CustomerToCustomerResponse(Customer customer)
    {
        return CustomerResponse.builder()
                .customerName(customer.getCustomerName())
                .gender(customer.getGender())
                .contactNo(customer.getContactNo())
                .email(customer.getEmail())
                .build();

    }
}
