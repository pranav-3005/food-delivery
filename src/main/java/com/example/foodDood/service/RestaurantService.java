package com.example.foodDood.service;

import com.example.foodDood.DTO.foodItemDTO.FoodItemRequest;
import com.example.foodDood.DTO.foodItemDTO.FoodItemResponse;
import com.example.foodDood.DTO.restaurantDTO.RestaurantRequest;
import com.example.foodDood.DTO.restaurantOrderDTO.RestaurantOrderResponse;
import com.example.foodDood.exception.RestaurantNotFoundException;
import com.example.foodDood.model.*;
import com.example.foodDood.model.configModel.User;
import com.example.foodDood.repository.*;
import com.example.foodDood.repository.configRepository.UserRepo;
import com.example.foodDood.transformer.FoodItemTransformer;
import com.example.foodDood.transformer.RestaurantOrderTransformer;
import com.example.foodDood.transformer.RestaurantTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

    @Autowired
    RestaurantRepo restaurantRepo;

    @Autowired
    FoodItemRepo foodItemRepo;

    @Autowired
    CartRepo cartRepo;

    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    CartItemRepo cartItemRepo;

    @Autowired
    CustomerOrderRepo customerOrderRepo;

    @Autowired
    DeliveryPartnerRepo deliveryPartnerRepo;
    @Autowired
    RestaurantOrderRepo restaurantOrderRepo;
    public ResponseEntity addRestaurant(RestaurantRequest restaurantRequest) {
        Optional<Restaurant> isRestaurant= Optional.ofNullable(restaurantRepo.findByrestaurantName(restaurantRequest.getRestaurantName()));

        if(!isRestaurant.isEmpty())
            throw new RestaurantNotFoundException("Restaurant already exists !!!");

        Restaurant restaurant= RestaurantTransformer.RestaurantRequestToRestaurant(restaurantRequest);
        restaurant.setOpened(true);

        Restaurant savedRestaurant = restaurantRepo.save(restaurant);

        return new ResponseEntity<>(RestaurantTransformer.RestaurantToRestaurantResponse(savedRestaurant), HttpStatus.CREATED);

    }

    public ResponseEntity getRestaurant(int id) {

        Optional<Restaurant>restaurant=restaurantRepo.findById(id);

        if(restaurant.isEmpty())
            return new ResponseEntity<>("Invalid restaurant id",HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(RestaurantTransformer.RestaurantToRestaurantResponse(restaurant.get()), HttpStatus.FOUND);
    }

    public ResponseEntity addFood(int restaurantId, FoodItemRequest foodItemRequest) {

        Optional<Restaurant> restaurant= restaurantRepo.findById(restaurantId);
        if ((restaurant.isEmpty()))
            throw new RestaurantNotFoundException("Restaurant not found");

        List<FoodItem> menu=restaurant.get().getMenu();
        for(FoodItem foodItem:menu)
        {
            //if already exists
            if(foodItem.getFoodName().equals(foodItemRequest.getFoodName()))
            {
                int prevQuantity= foodItem.getQuantity();
                foodItem.setQuantity(prevQuantity + foodItemRequest.getQuantity());
                foodItem.setAvailable(true);
                restaurant.get().setMenu(menu);
                restaurantRepo.save(restaurant.get());
                return new ResponseEntity<>("existing food is updated",HttpStatus.CREATED);
            }
        }
        //new item
        FoodItem foodItem= FoodItemTransformer.FoodItemRequestToFoodItem(foodItemRequest);
        foodItem.setAvailable(true);
        foodItem.setRestaurant(restaurant.get());
        foodItemRepo.save(foodItem);

        restaurant.get().getMenu().add(foodItem);
        restaurantRepo.save(restaurant.get());


        return new ResponseEntity<>("food added to the restaurant successfully",HttpStatus.CREATED);

    }

    public ResponseEntity getMenu(String restaurantName) {
        Optional<Restaurant> restaurant= Optional.ofNullable(restaurantRepo.findByrestaurantName(restaurantName));
        if(restaurant.isEmpty())
            throw new RestaurantNotFoundException("Restaurant not found");

        List<FoodItem> menu= restaurant.get().getMenu();
        List<FoodItemResponse> menuResponse=new ArrayList<>();

        for(FoodItem foodItem:menu)
        {
            menuResponse.add(FoodItemTransformer.FoodItemToFoodItemResponse(foodItem));
        }

        return new ResponseEntity<>(menuResponse,HttpStatus.FOUND);
    }

    public ResponseEntity closeOrOpenRestaurant(int restaurantId) {
        Optional<Restaurant> isRestaurant=restaurantRepo.findById(restaurantId);

        if(isRestaurant.isEmpty())
            return new ResponseEntity<>("Invalid restaurant id !!!",HttpStatus.NOT_FOUND);

        Restaurant restaurant=isRestaurant.get();
        //update
        restaurant.setOpened(!restaurant.isOpened());

        //save
        restaurantRepo.save(restaurant);

        if(restaurant.isOpened())
            return new ResponseEntity<>("Updated successfully,\n"+restaurant.getRestaurantName()+" is currently opened now.",HttpStatus.CREATED);
        else
            return new ResponseEntity<>("Updated successfully,\n"+restaurant.getRestaurantName()+" is currently closed now.",HttpStatus.CREATED);

    }

    public ResponseEntity viewRestaurantorders(int restaurantId) {
        Optional<Restaurant> isRestaurant=restaurantRepo.findById(restaurantId);

        if(isRestaurant.isEmpty())
            return new ResponseEntity<>("Invalid restaurant id !!!",HttpStatus.NOT_FOUND);

        Restaurant restaurant=isRestaurant.get();

        List<RestaurantOrder> restaurantOrderList=restaurantOrderRepo.findByRestaurant(restaurant);

        if(restaurantOrderList.isEmpty())
            return new ResponseEntity<>("Order list is empty !!!",HttpStatus.NOT_FOUND);

        List<RestaurantOrderResponse> restaurantOrderResponseList=new ArrayList<>();

        for(RestaurantOrder restaurantOrder:restaurantOrderList)
        {
            restaurantOrderResponseList.add(RestaurantOrderTransformer.restaurantOrderToRestaurantOrderResponse(restaurantOrder));
        }

        return new ResponseEntity<>(restaurantOrderResponseList,HttpStatus.FOUND);
    }

    //config

    @Autowired
    UserRepo userRepo;
    PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
    public ResponseEntity addUser(String userName, String password) {
        User user =new User();

        user.setUserName(userName);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("ROLE_RESTAURANTOWNER");

        userRepo.save(user);
        return new ResponseEntity<>(user,HttpStatus.CREATED);
    }

    //~config



    public ResponseEntity removeRestaurant(int restaurantId) {
        Optional<Restaurant> isRestaurant=restaurantRepo.findById(restaurantId);

        if(isRestaurant.isEmpty())
            return new ResponseEntity<>("Invalid restaurant id !!!",HttpStatus.NOT_FOUND);

        Restaurant restaurant=isRestaurant.get();
        String restaurantName=restaurant.getRestaurantName();

        //1. cart items & cart
        List<Cart> cartList=cartRepo.findByRestaurantName(restaurant.getRestaurantName());

        //1.1 remove carts at customer
        for(Customer customer:customerRepo.findAll())
        {
            for (int i = 0; i < cartList.size() && customer.getCartList().size()>0; i++)
            {
                if (customer.getCartList().contains(cartList.get(i)) )
                    customer.getCartList().remove(cartList.get(i));
            }
            customerRepo.save(customer);
        }

        //1.2 remove cart and cart items
        for(Cart cart:cartList)
        {
            for(CartItem cartItem:cart.getCartItemList())
            {
                cartItemRepo.delete(cartItem);
            }
            cartRepo.delete(cart);
        }



        //2. customer order
        List<CustomerOrder> customerOrderList=customerOrderRepo.findByRestaurantName(restaurant.getRestaurantName());

        for (CustomerOrder customerOrder:customerOrderList)
        {
            customerOrder.setRestaurantName(customerOrder.getRestaurantName()+" (Currently restaurant unavailable)");
            customerOrderRepo.save(customerOrder);
        }



        //3. restaurantOrder & deliveryPartner
        List<RestaurantOrder> restaurantOrderList=restaurantOrderRepo.findByRestaurant(restaurant);

        //3.1 remove the res orders from delivery partners
        for(DeliveryPartner deliveryPartner:deliveryPartnerRepo.findAll())
        {
            for (int i=0;i<restaurantOrderList.size() && deliveryPartner.getRestaurantOrderList().size()>0; i++) {

                if(deliveryPartner.getRestaurantOrderList().contains(restaurantOrderList.get(i)) )
                {
                    //restaurantOrderRepo.delete(restaurantOrder);
                    deliveryPartner.getRestaurantOrderList().remove(restaurantOrderList.get(i));
                }
            }
            deliveryPartnerRepo.save(deliveryPartner);
        }

        //3.2 delete the restaurantOrderList
        restaurantOrderRepo.deleteAll(restaurantOrderList);



        //4.food item & restaurant

        List<FoodItem> foodItemList=foodItemRepo.findByRestaurant(restaurant);

        foodItemRepo.deleteAll(foodItemList);
        restaurantRepo.delete(restaurant);

        //resposne
        return new ResponseEntity("The restaurant - "+restaurantName+" is deleted successfully,\nAll the carts related to this restaurant is also removed suceessfully.",HttpStatus.ACCEPTED);


    }


}
