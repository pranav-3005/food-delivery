package com.example.foodDood.service;

import com.example.foodDood.DTO.cartDTO.CartResponse;
import com.example.foodDood.DTO.cartItemDTO.CartItemRequest;
import com.example.foodDood.DTO.cartItemDTO.CartItemResponse;
import com.example.foodDood.DTO.customerDTO.CustomerRequest;
import com.example.foodDood.DTO.customerOrderDTO.CustomerOrderResponse;
import com.example.foodDood.DTO.foodItemDTO.FoodItemResponse;
import com.example.foodDood.DTO.paymentDTO.PaymentResponse;
import com.example.foodDood.DTO.restaurantDTO.RestaurantResponse;
import com.example.foodDood.Enum.PaymentMethod;
import com.example.foodDood.Enum.PaymentStatus;
import com.example.foodDood.exception.RestaurantNotFoundException;
import com.example.foodDood.exception.CustomerNotFoundException;
import com.example.foodDood.model.*;
import com.example.foodDood.model.configModel.User;
import com.example.foodDood.repository.*;
import com.example.foodDood.repository.configRepository.UserRepo;
import com.example.foodDood.transformer.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomerService {

    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    CartRepo cartRepo;

    @Autowired
    CartItemRepo cartItemRepo;

    @Autowired
    RestaurantRepo restaurantRepo;

    @Autowired
    FoodItemRepo foodItemRepo;

    @Autowired
    PaymentRepo paymentRepo;

    @Autowired
    DeliveryPartnerRepo deliveryPartnerRepo;

    @Autowired
    CustomerOrderRepo customerOrderRepo;

    @Autowired
    RestaurantOrderRepo restaurantOrderRepo;


    public ResponseEntity addCustomer(CustomerRequest customerRequest) {

        Customer customer= CustomerTransformer.CustomerRequestToCustomer(customerRequest);


        //save
        Customer savedCustomer = customerRepo.save(customer);

        // to response

        return new ResponseEntity(CustomerTransformer.CustomerToCustomerResponse(savedCustomer), HttpStatus.CREATED);


    }

    public ResponseEntity addFoodToCart(int customerId, String restaurantName, CartItemRequest cartItemRequest) {

        //failures

        //1
        Optional<Customer>isCustomer=customerRepo.findById(customerId);
        if(isCustomer.isEmpty())
            return new ResponseEntity<>("invalid customer-id !!!",HttpStatus.NOT_FOUND);

        //2
        Optional<Restaurant> isrestaurant= Optional.ofNullable(restaurantRepo.findByrestaurantName(restaurantName));
        if(isrestaurant.isEmpty())
            return new ResponseEntity<>("Restaurant not found",HttpStatus.NOT_FOUND);


        Customer customer=isCustomer.get();
        Restaurant restaurant=isrestaurant.get();

//        //3 adding from different restaurant
//        if(customer.getCart().getRestaurantName()!=null && customer.getCart().getRestaurantName().equals(restaurantName)==false)
//        {
//            return new ResponseEntity<>("Cannot add items to cart from multiple restaurant.\nPlease remove the items from the cart,before adding items from different restaurant",HttpStatus.BAD_REQUEST);
//        }

        boolean found=false;
        FoodItem foodItem1=new FoodItem();
       // System.out.println("given foodId: "+cartItemRequest.getFoodItemId()+"reqQ: "+ cartItemRequest.getRequiredQuantity());
        for(FoodItem foodItem: restaurant.getMenu())
        {
            if(foodItem.getId()==cartItemRequest.getFoodItemId())
            {
                System.out.println("matched");
                found=true;

                //3
                if(cartItemRequest.getRequiredQuantity()>foodItem.getQuantity())
                    return new ResponseEntity<>("required quantity isn't available in restaurant now. \n Available quantity : "+foodItem.getQuantity(),HttpStatus.NOT_FOUND);

                foodItem1=foodItem;
            }

        }

        //4
        if(found==false)
            return new ResponseEntity<>("Entered food item is not found in the restaurant",HttpStatus.NOT_FOUND);

        //5
        if(!restaurant.isOpened())
            return  new ResponseEntity<>("Sorry, this restaurant is closed now, please check out our other restaurants :)",HttpStatus.NOT_FOUND);

        //all failures are covered
        //............................


        //Cart cart =customer.getCart();
        Cart cart=new Cart();
        boolean cartFound=false;
        for(Cart cart1:customer.getCartList())
        {
            //if cart exists
            if(restaurantName.equals(cart1.getRestaurantName()))
            {
                cart=cart1;
                cartFound=true;
            }
        }
        //create cart,if not found
        if(!cartFound)
            cart=Cart.builder()
                    .restaurantName(null)
                    .cartTotalValue(0.0)
                    .customer(customer)
                    .cartItemList(new ArrayList<>())
                    .build();

        //item already exists in cart  //loop will be passes, if cart is empty,so no prblm
        for(CartItem cartItem:cart.getCartItemList())
        {
            if(cartItem.getFoodItemId()==cartItemRequest.getFoodItemId())
            {
                //update cart item
                cartItem.setQuantity(cartItem.getQuantity() + cartItemRequest.getRequiredQuantity());
                //update cart
                cart.setCartTotalValue(cart.getCartTotalValue() + (double)cartItemRequest.getRequiredQuantity() * cartItem.getPrice());

                cartItemRepo.save(cartItem);
                //customer.getCartList().add(cart);
                for(Cart cart1:customer.getCartList())
                {
                    if(cart1.getId()==cart.getId())
                        cart1=cart;
                }
                customerRepo.save(customer);
                return new ResponseEntity<>(CartItemTransformer.cartItemToCartItemResponse(cartItem),HttpStatus.CREATED);
            }
        }


        //update available quantity of foodItem at restaurant  ***
        // //int balanceQuantity = foodItem1.getQuantity() - cartItemRequest.getRequiredQuantity();
//        for(FoodItem foodItem:restaurant.getMenu())         //*** only update fooditem at res, while ordering
//        {
//            if(foodItem.getId()==cartItemRequest.getFoodItemId())
//            {
//                foodItem.setQuantity(balanceQuantity);
//                if(balanceQuantity==0)
//                    foodItem.setAvailable(false);
//            }
//        }
//        restaurantRepo.save(restaurant);

        //add new item

        //req -> cartitem
        CartItem cartItem= CartItemTransformer.cartItemRequestToCartItem(foodItem1,cartItemRequest);

        //update in cart
        cart.setCartTotalValue( cart.getCartTotalValue() + foodItem1.getPrice() * (double)cartItemRequest.getRequiredQuantity() );
        cart.setRestaurantName(restaurant.getRestaurantName());


        //save at cartItem
        cartItem.setCart(cart);
        //CartItem savedCartItem=cartItemRepo.save(cartItem);

        //save at cart
        cart.getCartItemList().add(cartItem);
        Cart savedCart=cartRepo.save(cart);

        //save at customer
        customer.getCartList().add(savedCart);
        customerRepo.save(customer);

        //-> response
        return new ResponseEntity<>(CartItemTransformer.cartItemToCartItemResponse(cartItem),HttpStatus.CREATED);

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



    public ResponseEntity orderCartFood(int customerId,int cartId, String paymentMethodString) {

        PaymentMethod paymentMethod=PaymentMethod.valueOf(paymentMethodString);
        //exceptions
        //1
        Optional<Customer> isCustomer=customerRepo.findById(customerId);
        if(isCustomer.isEmpty())
            throw new CustomerNotFoundException("Customer not found !!!");

        Customer customer=isCustomer.get();


        //2
        boolean found=false;
        for(PaymentMethod paymentMethod1:PaymentMethod.values())
        {
            if(paymentMethod1.equals(paymentMethod))
            {
                found=true;
                break;
            }
        }
        if(!found)
            return new ResponseEntity<>("payment method is not valid, Only UPI,cash and card are accepted !!!",HttpStatus.NOT_FOUND);

        //3 cart empty || not found
        boolean cartFound=false;
        Cart cart=new Cart();
        for(Cart cart1:customer.getCartList())
        {
            if (cart1.getId()==cartId)
            {
                cartFound=true;
                cart=cart1;
                break;
            }
        }
        if(!cartFound || cart.getCartItemList().size()==0)
            return new ResponseEntity<>("No items found in cart, Please add items to cart first !!!",HttpStatus.NOT_FOUND);

        //4. no delivery partners available
        List<DeliveryPartner> deliveryPartnerList=deliveryPartnerRepo.findAll();
        if(deliveryPartnerList.isEmpty())
            return new ResponseEntity<>("Sorry,it seems no delivery agents are available right now,\n Please try again later.",HttpStatus.NOT_FOUND);
        //......................
        //1.payment

        Date date=new Date();
        Payment payment= Payment.builder()
                .transactionId(String.valueOf(UUID.randomUUID()))
                .date(date)
                .paymentMethod(paymentMethod)
                .totalBill(cart.getCartTotalValue())
                .paymentStatus(PaymentStatus.SUCCESS)
                .customer(customer)
                .build();

        Payment savedPayment=paymentRepo.save(payment);
        customer.getPaymentList().add(savedPayment);
        Customer savedCustomer=customerRepo.save(customer);

        //2.customer order
        String itemQuantityMap="";
        for(CartItem cartItem:cart.getCartItemList())
        {
            itemQuantityMap+=cartItem.getFoodItemId()+":"+cartItem.getQuantity()+",";
        }
        itemQuantityMap=itemQuantityMap.substring(0,itemQuantityMap.length()-1);//removing the last comma

        CustomerOrder customerorder= CustomerOrder.builder()
                .restaurantName(cart.getRestaurantName())
                .date(date)
                .totalBill(cart.getCartTotalValue())
                .customer(savedCustomer)
                .deliveryPartner(deliveryPartnerRepo.findByRandom())
                .itemQuantityMap(itemQuantityMap)
                .paymentMethod(paymentMethod)
                .build();

        CustomerOrder savedCustomerOrder=customerOrderRepo.save(customerorder);

        //2.1 delete cart && cart items
        for(CartItem cartItem:cart.getCartItemList())
        {
            cartItemRepo.deleteById(cartItem.getId());
        }
        savedCustomer.getCartList().remove(cart); //remove cart in customer
        cartRepo.deleteById(cart.getId());


        //2.2 save customer
        savedCustomer.getCustomerOrderList().add(savedCustomerOrder); //add customer order
        Customer savedCustomer1=customerRepo.save(savedCustomer);

        //2.3 update at payment
        savedPayment.setCustomerOrderId(savedCustomerOrder.getId());
        paymentRepo.save(savedPayment);


        //3. restaurant order
        RestaurantOrder restaurantOrder=RestaurantOrder.builder()
                .date(date)
                .itemQuantityMap(savedCustomerOrder.getItemQuantityMap())
                .totalBill(savedCustomerOrder.getTotalBill())
                .deliveryPartner(savedCustomerOrder.getDeliveryPartner())
                .customerName(savedCustomerOrder.getCustomer().getCustomerName())
                .restaurant(restaurantRepo.findByrestaurantName(savedCustomerOrder.getRestaurantName()))
                .build();
        //3.1 save at restaurant and res order repos
        RestaurantOrder savedrestaurantOrder=restaurantOrderRepo.save(restaurantOrder);
        Restaurant restaurant=restaurantRepo.findById(savedrestaurantOrder.getRestaurant().getId()).get();
        restaurant.getRestaurantOrderList().add(savedrestaurantOrder);
        restaurantRepo.save(restaurant);


        //4. deliveryPartner
        DeliveryPartner deliveryPartner=savedrestaurantOrder.getDeliveryPartner();
        deliveryPartner.getCustomerOrderList().add(savedCustomerOrder);
        deliveryPartner.getRestaurantOrderList().add(savedrestaurantOrder);

        deliveryPartnerRepo.save(deliveryPartner);

        //5.return
        String message="";
        if(!paymentMethod.equals(PaymentMethod.COD))
        {
            message="Your order has been placed successfully. :) \n" +
                "Our delivery partner "+deliveryPartner.getDeliverPartnerName() + " is been assigned to this order, feel free to reach out the delivery partner - "+deliveryPartner.getContactNumber()+"\n" +
                "Thanks for ordering from fooddood !!!";
        }
        else
        {
            message="Your order has been placed successfully. :) \n" +
                    "Our delivery partner "+deliveryPartner.getDeliverPartnerName() + " is been assigned to this order, feel free to reach out the delivery partner - "+deliveryPartner.getContactNumber()+"\n" +
                    "Please pay your bill amount of "+savedCustomerOrder.getTotalBill()+" to our delivery partner. \n"+
                    "Thanks for ordering from fooddood !!!";
        }

        return new ResponseEntity<>(message,HttpStatus.ACCEPTED);
    }

    public ResponseEntity getCart(int customerId,int cartId) {
        Optional<Customer>isCustomer=customerRepo.findById(customerId);

        //1 customer not found
        if(isCustomer.isEmpty())
            throw new CustomerNotFoundException("Invalid customer id");
        Customer customer=isCustomer.get();

        //2 empty cart
        if(customer.getCartList().isEmpty())
            return new ResponseEntity<>("Your cart is empty !!!",HttpStatus.NOT_FOUND);

        //3 invalid cart id
        Cart cart=new Cart();
        boolean cartFound=false;
        for(Cart cart1: customer.getCartList())
            if(cart1.getId()==cartId)
            {
                cart=cart1;
                cartFound=true;
                break;
            }
        if(!cartFound)
            return new ResponseEntity<>("Invalid cart id !!!",HttpStatus.NOT_FOUND);

        //****************

        CartResponse cartResponse=CartResponse.builder()
                .cartTotalValue(cart.getCartTotalValue())
                .restaurantName(cart.getRestaurantName())
                .cartItemResponseList(new ArrayList<>())
                .build();

        for(CartItem cartItem:cart.getCartItemList())
        {
            cartResponse.getCartItemResponseList().add(CartItemTransformer.cartItemToCartItemResponse(cartItem));
        }

        return new ResponseEntity<>(cartResponse,HttpStatus.FOUND);
    }

    public ResponseEntity deleteCart(int customerId,int cardId) {
        Optional<Customer> isCustomer=customerRepo.findById(customerId);

        if(isCustomer.isEmpty())
            throw new CustomerNotFoundException("Invalid Customer Id  !!!");

        Customer customer=isCustomer.get();

        boolean cartFound=false;
        Cart cart=new Cart();
        for(Cart cart1:customer.getCartList())
        {
            if(cardId==cart1.getId())
            {
                cartFound=true;
                cart=cart1;
            }
        }
        if(!cartFound)
            return new ResponseEntity<>("Cart not found !!!",HttpStatus.NOT_FOUND);

        //*******
        customer.getCartList().remove(cart);
        for(CartItem cartItem:cart.getCartItemList())
        {
            cartItemRepo.delete(cartItem);
        }
        cartRepo.delete(cart);
        customerRepo.save(customer);

        return new ResponseEntity<>("The cart with id:"+cardId+" is discarded successfully !!!",HttpStatus.OK);
    }

    public ResponseEntity viewAllCarts(int customerId) {
        Optional<Customer> isCustomer=customerRepo.findById(customerId);

        if(isCustomer.isEmpty())
            return new ResponseEntity<>("Invalid customer id !!!",HttpStatus.NOT_FOUND);
        Customer customer=isCustomer.get();

        if(customer.getCartList().size()==0)
            return new ResponseEntity<>("Cart list is empty,\n Please add some food to view cart :)",HttpStatus.NOT_FOUND);

        //response
        List<CartResponse> cartResponseList=new ArrayList<>();
        for(Cart cart:customer.getCartList())
        {
            List<CartItemResponse> cartItemResponseList=new ArrayList<>();
            for(CartItem cartItem:cart.getCartItemList())
            {
                cartItemResponseList.add(CartItemTransformer.cartItemToCartItemResponse(cartItem));
            }

            cartResponseList.add(CartResponse.builder()
                            .restaurantName(cart.getRestaurantName())
                            .cartTotalValue(cart.getCartTotalValue())
                            .cartItemResponseList(cartItemResponseList)
                            .build());
        }
        return new ResponseEntity<>(cartResponseList,HttpStatus.FOUND);
    }

    public ResponseEntity viewAllRestaurants() {
        List<Restaurant> restaurantList = restaurantRepo.findAll();

        if(restaurantList.isEmpty())
            return new ResponseEntity<>("Currently,no restaurant available !!!",HttpStatus.NOT_FOUND);

        List<RestaurantResponse> restaurantResponseList=new ArrayList<>();

        for(Restaurant restaurant:restaurantList)
            restaurantResponseList.add(RestaurantTransformer.RestaurantToRestaurantResponse(restaurant));

        return new ResponseEntity<>(restaurantResponseList,HttpStatus.FOUND);
    }

    public ResponseEntity viewCustomerOrders(int customerId) {

        Optional<Customer> isCustomer=customerRepo.findById(customerId);

        if(isCustomer.isEmpty())
            return new ResponseEntity<>("Invalid customer id !!!",HttpStatus.NOT_FOUND);
        Customer customer=isCustomer.get();

        List<CustomerOrder> customerOrderList=customerOrderRepo.findByCustomer(customer);

        List<CustomerOrderResponse> customerOrderResponseList=new ArrayList<>();

        for(CustomerOrder customerOrder:customerOrderList)
        {
            customerOrderResponseList.add(CustomerOrderTransformer.CustomerOrderToCustomerOrderResponse(customerOrder));
        }

        return new ResponseEntity<>(customerOrderResponseList,HttpStatus.FOUND);
    }

    public ResponseEntity viewPaymentHistory(int customerId) {
        Optional<Customer> isCustomer=customerRepo.findById(customerId);

        if(isCustomer.isEmpty())
            return new ResponseEntity<>("Invalid customer id !!!",HttpStatus.NOT_FOUND);
        Customer customer=isCustomer.get();

        List<Payment> paymentList=paymentRepo.findByCustomer(customer);

        if(paymentList.isEmpty())
            return new ResponseEntity<>("No payment history so far !!!",HttpStatus.NOT_FOUND);

        //........

        List<PaymentResponse> paymentResponseList=new ArrayList<>();

        for(Payment payment:paymentList)
            paymentResponseList.add(PaymentTransformer.PaymentToPaymentResponse(payment));

        return new ResponseEntity<>(paymentResponseList,HttpStatus.FOUND);
    }





    //config

    @Autowired
    UserRepo userRepo;
    PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
    public ResponseEntity addUser(String userName, String password) {
        User user =new User();

        user.setUserName(userName);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("ROLE_CUSTOMER");

        userRepo.save(user);
        return new ResponseEntity<>(user,HttpStatus.CREATED);
    }

    //~config
}
