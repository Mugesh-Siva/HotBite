package com.hexaware.hotbyte.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hexaware.hotbyte.entity.User;
import com.hexaware.hotbyte.repository.UserRepository;
import com.hexaware.hotbyte.security.UserDetailsImpl;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repo.findByEmail(username).orElse(null);
        
        if(user == null) {
            throw new UsernameNotFoundException(username);
        }
        
        return new UserDetailsImpl(user);
    }
}
