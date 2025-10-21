package com.smartbooking.app.impl;

import com.smartbooking.app.dto.CustomUserDetails;
import com.smartbooking.app.entity.User;
import com.smartbooking.app.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepo;

    public UserDetailsServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user =  userRepo.findByEmail(email)
                .orElseThrow(()-> new EntityNotFoundException("User not found with this ID"));
        return new CustomUserDetails(user.getId(), user.getEmail(), user.getRole() , user.getPassword());
    }
}
