package com.example.foodDood.DTO.DeliveryPartnerDTO;

import com.example.foodDood.Enum.Gender;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class DeliveryPartnerResponse {

    String deliverPartnerName;

    int age;

    Gender gender;

    String contactNumber;

    String email;

    String vehicleNumber;
}
