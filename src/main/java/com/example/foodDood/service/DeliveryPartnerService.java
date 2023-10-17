package com.example.foodDood.service;

import com.example.foodDood.DTO.DeliveryPartnerDTO.DeliveryPartnerRequest;
import com.example.foodDood.DTO.deliveryPartnerOrderDTO.DeliveryPartnerOrderResponse;
import com.example.foodDood.model.CustomerOrder;
import com.example.foodDood.model.DeliveryPartner;
import com.example.foodDood.repository.DeliveryPartnerRepo;
import com.example.foodDood.transformer.DeliveryPartnerOrderTransformer;
import com.example.foodDood.transformer.DeliveryPartnerTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DeliveryPartnerService {

    @Autowired
    DeliveryPartnerRepo deliveryPartnerRepo;

    public ResponseEntity addDeliveryPartner(DeliveryPartnerRequest deliveryPartnerRequest) {

        DeliveryPartner deliveryPartner= DeliveryPartnerTransformer.DeliveryPartnerRequestToDeliveryPartner(deliveryPartnerRequest);

        deliveryPartnerRepo.save(deliveryPartner);

        return new ResponseEntity<>("Hey "+deliveryPartner.getDeliverPartnerName()+",\n your delivery partner profile have been created successfully !!!", HttpStatus.CREATED);
    }

    public ResponseEntity getDeliveryPartner(int id) {
        Optional<DeliveryPartner> isDeliveryPartner= deliveryPartnerRepo.findById(id);

        if(isDeliveryPartner.isEmpty())
            return new ResponseEntity<>("Invalid id !!!",HttpStatus.NOT_FOUND);

        return new ResponseEntity(DeliveryPartnerTransformer.DeliveryPartnerToDeliveryPartnerResponse(isDeliveryPartner.get()),HttpStatus.FOUND);
    }

    public ResponseEntity viewDeliveryPartnerOrders(int deliveryPartnerId) {
        Optional<DeliveryPartner> isDeliveryPartner= deliveryPartnerRepo.findById(deliveryPartnerId);

        if(isDeliveryPartner.isEmpty())
            return new ResponseEntity<>("Invalid id !!!",HttpStatus.NOT_FOUND);
        DeliveryPartner deliveryPartner=isDeliveryPartner.get();

        List<CustomerOrder> customerOrderList=deliveryPartner.getCustomerOrderList();

        List<DeliveryPartnerOrderResponse> deliveryPartnerOrderResponseList=new ArrayList<>();

        for(CustomerOrder customerOrder:customerOrderList)
        {
            deliveryPartnerOrderResponseList.add(DeliveryPartnerOrderTransformer.CustomerOrderToDeliveryPartnerOrderResponse(customerOrder));
        }

        return new ResponseEntity<>(deliveryPartnerOrderResponseList,HttpStatus.FOUND);


    }
}
