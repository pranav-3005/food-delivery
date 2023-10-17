package com.example.foodDood.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
        UserDetails admin1= User.withUsername("pranav")
                .password(getPasswordEncoder().encode("pranav123"))
                .roles("admin")
                .build();

        UserDetails customer1=User.withUsername("sanjay")
                .password(getPasswordEncoder().encode("sanjay123"))
                .roles("customer")
                .build();

        UserDetails restaurantOwner1=User.withUsername("kohli")
                .password(getPasswordEncoder().encode("kohli123"))
                .roles("restaurantOwner")
                .build();
        UserDetails deliveryPartner1=User.withUsername("tony")
                .password(getPasswordEncoder().encode("tony123"))
                .roles("deliveryPartner")
                .build();

        return new InMemoryUserDetailsManager(admin1,customer1,restaurantOwner1,deliveryPartner1);
    }

    //authentication for APIs
    @SuppressWarnings("removal")
    @Bean
    public SecurityFilterChain getSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable().authorizeHttpRequests()
                .requestMatchers("/restaurant/get-menu/**")
                .permitAll()
                .requestMatchers("customer/get-menu/**")
                .permitAll()
                .requestMatchers("/customer/**")
                .hasAnyRole("customer","admin")
                .requestMatchers("/restaurant/**")
                .hasRole("restaurantOwner")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin();

        return httpSecurity.build();
    }
}
