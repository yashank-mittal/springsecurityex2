package com.example.springsecurityex2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.springsecurityex2.Config.UserPrinciple;
import com.example.springsecurityex2.Entity.User;
import com.example.springsecurityex2.repo.UserRepo;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        User user = userRepo.findByUsername(username);

        if(user == null)
        {
            System.out.println("User Not found");
            throw new UsernameNotFoundException("User not found");
        }

        return new UserPrinciple(user);
    }
    
}
