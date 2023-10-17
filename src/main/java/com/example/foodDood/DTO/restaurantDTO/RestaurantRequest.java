package com.example.foodDood.DTO.restaurantDTO;

import com.example.foodDood.Enum.RestaurantCategory;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class RestaurantRequest {

    String restaurantName;

    RestaurantCategory restaurantCategory;

    @Size(min =10,max = 10)
    String contactNumber;
}
