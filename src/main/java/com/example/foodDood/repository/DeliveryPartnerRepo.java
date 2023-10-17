package com.example.foodDood.repository;

import com.example.foodDood.model.DeliveryPartner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryPartnerRepo extends JpaRepository<DeliveryPartner,Integer> {

    @Query(value = "select * from delivery_partner order by rand() limit 1;",nativeQuery = true)
    public DeliveryPartner findByRandom();
}
