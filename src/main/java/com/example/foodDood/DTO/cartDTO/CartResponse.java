package com.example.foodDood.DTO.cartDTO;

import com.example.foodDood.DTO.cartItemDTO.CartItemResponse;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CartResponse {
    String restaurantName;
    double cartTotalValue;
    List<CartItemResponse>cartItemResponseList=new ArrayList<>();
}
