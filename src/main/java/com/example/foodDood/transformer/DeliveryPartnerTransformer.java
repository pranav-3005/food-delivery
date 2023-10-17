package com.example.foodDood.transformer;

import com.example.foodDood.DTO.DeliveryPartnerDTO.DeliveryPartnerRequest;
import com.example.foodDood.DTO.DeliveryPartnerDTO.DeliveryPartnerResponse;
import com.example.foodDood.model.DeliveryPartner;

public class DeliveryPartnerTransformer {

    public static DeliveryPartner DeliveryPartnerRequestToDeliveryPartner(DeliveryPartnerRequest deliveryPartnerRequest)
    {
        return DeliveryPartner.builder()
                .deliverPartnerName(deliveryPartnerRequest.getDeliverPartnerName())
                .age(deliveryPartnerRequest.getAge())
                .gender(deliveryPartnerRequest.getGender())
                .contactNumber(deliveryPartnerRequest.getContactNumber())
                .email(deliveryPartnerRequest.getEmail())
                .vehicleNumber(deliveryPartnerRequest.getVehicleNumber())
                .build();  //handle the lists in service layer
    }

    public static DeliveryPartnerResponse DeliveryPartnerToDeliveryPartnerResponse(DeliveryPartner deliveryPartner)
    {
        return DeliveryPartnerResponse.builder()
                .deliverPartnerName(deliveryPartner.getDeliverPartnerName())
                .age(deliveryPartner.getAge())
                .gender(deliveryPartner.getGender())
                .contactNumber(deliveryPartner.getContactNumber())
                .email(deliveryPartner.getEmail())
                .vehicleNumber(deliveryPartner.getVehicleNumber())
                .build();
    }
}
