package com.example.foodDood.DTO.restaurantDTO;

import com.example.foodDood.Enum.RestaurantCategory;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class RestaurantResponse {

    String restaurantName;

    RestaurantCategory restaurantCategory;

    String contactNumber;

    boolean opened;
}
