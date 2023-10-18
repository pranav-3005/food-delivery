package com.example.foodDood.controller;

import com.example.foodDood.DTO.cartItemDTO.CartItemRequest;
import com.example.foodDood.DTO.customerDTO.CustomerRequest;
import com.example.foodDood.DTO.foodItemDTO.FoodItemResponse;
import com.example.foodDood.Enum.PaymentMethod;
import com.example.foodDood.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @PostMapping("/add-customer")
    public ResponseEntity addCustomer(@RequestBody CustomerRequest customerRequest)
    {
        return customerService.addCustomer(customerRequest);
    }

    @PostMapping("/add-food-to-cart")
    public ResponseEntity addFoodToCart(@RequestParam int customerId, @RequestParam String restaurantName, @RequestBody CartItemRequest cartItemRequest)
    {
        return customerService.addFoodToCart(customerId,restaurantName,cartItemRequest);
    }

    @GetMapping("/get-menu/{restaurantName}")
    public ResponseEntity getMenu(@PathVariable("restaurantName") String restaurantName)
    {
        return customerService.getMenu(restaurantName);
    }
    @GetMapping("/view-cart")
    public ResponseEntity getCart(@RequestParam int customerId,@RequestParam int cartId)
    {
        return customerService.getCart(customerId,cartId);
    }

    @PostMapping("/order")
    public ResponseEntity orderCartFood(@RequestParam int customerId,@RequestParam int cartId , @RequestParam String paymentMethod)
    {

        return customerService.orderCartFood(customerId,cartId,paymentMethod);
    }

    @DeleteMapping("/delete-cart")
    public ResponseEntity deleteCart(@RequestParam int customerId,@RequestParam int cartId)
    {
        return customerService.deleteCart(customerId,cartId);
    }

    @GetMapping("/view-all-carts")
    public ResponseEntity viewAllCarts(@RequestParam int customerId)
    {
        return customerService.viewAllCarts(customerId);
    }

    @GetMapping("/view-all-restaurants")
    public ResponseEntity viewAllRestaurants()
    {
        return customerService.viewAllRestaurants();
    }

    @GetMapping("/view-customer-orders")
    public ResponseEntity viewCustomerOrders(@RequestParam int customerId)
    {
        return customerService.viewCustomerOrders(customerId);
    }

    @GetMapping("/view-payment-history")
    public ResponseEntity viewPaymentHistory(@RequestParam int customerId)
    {
        return customerService.viewPaymentHistory(customerId);
    }


    // config APIs

    @PostMapping("/add-user")
    public ResponseEntity addUser(@RequestParam String userName,@RequestParam String password)
    {
        return customerService.addUser(userName,password);
    }
}
