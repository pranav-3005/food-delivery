package com.example.foodDood.DTO.customerDTO;

import com.example.foodDood.Enum.Gender;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "customer")
@Builder
public class CustomerResponse {

    String customerName;

    Gender gender;

    String contactNo;

    String email;
}
