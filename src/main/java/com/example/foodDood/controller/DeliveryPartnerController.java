package com.example.foodDood.controller;

import com.example.foodDood.DTO.DeliveryPartnerDTO.DeliveryPartnerRequest;
import com.example.foodDood.service.DeliveryPartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/deliveryPartner")
public class DeliveryPartnerController {

    @Autowired
    DeliveryPartnerService deliveryPartnerService;

    @PostMapping("/addDeliveryPartner")
    public ResponseEntity addDeliveryPartner(@RequestBody DeliveryPartnerRequest deliveryPartnerRequest)
    {
        return deliveryPartnerService.addDeliveryPartner(deliveryPartnerRequest);
    }

    @GetMapping("/getDeliveryPartner")
    public ResponseEntity getDeliveryPartner(@RequestParam int id)
    {
        return deliveryPartnerService.getDeliveryPartner(id);
    }

    @GetMapping("/view-deliveryPartner-orders")
    public ResponseEntity viewDeliveryPartnerOrders(@RequestParam int deliveryPartnerId)
    {
        return deliveryPartnerService.viewDeliveryPartnerOrders(deliveryPartnerId);
    }


}
