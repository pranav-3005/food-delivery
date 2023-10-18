package com.example.foodDood.repository.configRepository;

import com.example.foodDood.model.configModel.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User,Integer> {

    User findByUserName(String userName);
}
