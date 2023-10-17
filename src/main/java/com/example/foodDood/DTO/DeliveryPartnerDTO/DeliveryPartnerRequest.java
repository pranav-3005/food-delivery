package com.example.foodDood.DTO.DeliveryPartnerDTO;


import com.example.foodDood.Enum.Gender;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class DeliveryPartnerRequest {

    String deliverPartnerName;

    int age;

    Gender gender;

    @Column(unique = true,nullable = false)
    @Size(min = 10,max = 10)
    String contactNumber;

    @Email
    String email;

    @Column(unique = true,nullable = false)
    String vehicleNumber;
}
