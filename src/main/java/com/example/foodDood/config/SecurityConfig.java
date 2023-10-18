package com.example.foodDood.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    //encrypting password
    @Bean
    public BCryptPasswordEncoder getPasswordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    //users with roles
    @Bean
    public UserDetailsService getUserDetailsService()
    {
//        UserDetails admin1= User.withUsername("pranav")
//                .password(getPasswordEncoder().encode("pranav123"))
//                .roles("ADMIN")
//                .build();
//
//        UserDetails customer1=User.withUsername("sanjay")
//                .password(getPasswordEncoder().encode("sanjay123"))
//                .roles("CUSTOMER")
//                .build();
//
//        UserDetails restaurantOwner1=User.withUsername("kohli")
//                .password(getPasswordEncoder().encode("kohli123"))
//                .roles("RESTAURANTOWNER")
//                .build();
//        UserDetails deliveryPartner1=User.withUsername("tony")
//                .password(getPasswordEncoder().encode("tony123"))
//                .roles("DELIVERYPARTNER")
//                .build();
//
//        return new InMemoryUserDetailsManager(admin1,customer1,restaurantOwner1,deliveryPartner1);
        //step 1
        return new CustomUserDetailsService();
    }

    //authentication for APIs
    @SuppressWarnings("removal")
    @Bean
    public SecurityFilterChain getSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable().authorizeHttpRequests()
                .requestMatchers("/customer/add-user/**") //to add student
                .permitAll()
                .requestMatchers("/restaurant/add-user/**") //add restaurant owner
                .permitAll()
                .requestMatchers("/deliveryPartner/add-user/**") //add delivery partner
                .permitAll()

                //menu can be accessed by all
                .requestMatchers("/restaurant/get-menu/**")
                .permitAll()
                .requestMatchers("/customer/get-menu/**")
                .permitAll()

                //profile based accesses
                .requestMatchers("/customer/**")
                .hasAnyRole("CUSTOMER","ADMIN")
                .requestMatchers("/restaurant/**")
                .hasAnyRole("RESTAURANTOWNER","ADMIN")
                .requestMatchers("/deliveryPartner/**")
                .hasAnyRole("DELIVERYPARTNER","ADMIN")


                .anyRequest()
                .authenticated()
                .and()
                .formLogin();

        return httpSecurity.build();
    }

    @Bean
    public DaoAuthenticationProvider getDaoAuthenticationProvider()
    {
        DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();

        daoAuthenticationProvider.setUserDetailsService(getUserDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(getPasswordEncoder());

        return daoAuthenticationProvider;
    }
}
