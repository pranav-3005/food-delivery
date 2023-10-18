package com.example.foodDood.config;

import com.example.foodDood.model.configModel.User;
import com.example.foodDood.repository.configRepository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepo.findByUserName(username);
        if(user ==null)
        {
            throw new UsernameNotFoundException("Invalid username !!!");
        }

        //step 2
        return new UserDetailsCreator(user);
    }
}
