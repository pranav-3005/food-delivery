package com.example.foodDood.controller;

import com.example.foodDood.DTO.foodItemDTO.FoodItemRequest;
import com.example.foodDood.DTO.restaurantDTO.RestaurantRequest;
import com.example.foodDood.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    @Autowired
    RestaurantService restaurantService;

    @PostMapping("/add-restaurant")
    public ResponseEntity addRestaurant(@RequestBody RestaurantRequest restaurantRequest)
    {
        return restaurantService.addRestaurant(restaurantRequest);
    }

    @GetMapping("/get-restaurant")
    public ResponseEntity getRestaurant(@RequestParam(name = "id")int id)
    {
        return restaurantService.getRestaurant(id);
    }

    @PostMapping("/add-food")
    public ResponseEntity addFood(@RequestParam(name = "restaurantId")int restaurantId, @RequestBody FoodItemRequest foodItemRequest)
    {
        return restaurantService.addFood(restaurantId,foodItemRequest);
    }

    @GetMapping("/get-menu/{restaurantName}")
    public ResponseEntity getMenu(@PathVariable("restaurantName") String restaurantName)
    {
        return restaurantService.getMenu(restaurantName);
    }

    @PutMapping("/close-or-open-restaurant")
    public ResponseEntity closeOrOpenRestaurant(@RequestParam int restaurantId)
    {
        return restaurantService.closeOrOpenRestaurant(restaurantId);
    }

    @GetMapping("/view-restaurant-orders")
    public  ResponseEntity viewRestaurantorders(@RequestParam int restaurantId)
    {
        return restaurantService.viewRestaurantorders(restaurantId);
    }

    // config
    @PostMapping("/add-user")
    public ResponseEntity addUser(@RequestParam String userName,@RequestParam String password)
    {
        return restaurantService.addUser(userName,password);
    }

    //~config

    //GetMapping("/get_total_coupons-and_get-total-coupon-amnt)
    //ResponseEntity  getCoupon(@Requestparams int restaurantid)
    /*
    return service.getCoupon(id)*/

    //ResponseEntity getCoupons(int id)
    /*
    List<Bill> totalBills= billRepo.findAllById

    int totalOrder=totalBill.size()
    int totalCoupons=totalOrder/100
    int totalCouponAmount=0

    if(totalOrder<100)
     return new responseEntity(0,0)
    for()*/


}
