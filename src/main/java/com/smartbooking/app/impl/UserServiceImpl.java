package com.smartbooking.app.impl;

import com.smartbooking.app.dto.*;
import com.smartbooking.app.entity.*;
import com.smartbooking.app.enums.Role;
import com.smartbooking.app.exception.*;
import com.smartbooking.app.repository.*;
import com.smartbooking.app.service.UserService;
import com.smartbooking.app.utils.AdapterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BusinessRepository businessRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           BusinessRepository businessRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.businessRepository = businessRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserResponseDto createUser(UserRequestDto dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent())
            throw new BadRequestException("Email already in use");

        User user = AdapterUtils.convertToUserEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(dto.getRole() != null ? dto.getRole() : Role.CLIENT);

        if (dto.getBusinessId() != null) {
            Business business = businessRepository.findById(dto.getBusinessId())
                    .orElseThrow(() -> new ResourceNotFoundException("Business not found"));
            user.getBusiness().add(business);
        }
        userRepository.save(user);
        return AdapterUtils.convertToUserResponseDto(userRepository.save(user));
    }

    @Override
    public UserResponseDto getUserById(Long id) {
        return AdapterUtils.convertToUserResponseDto(
                userRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found")));
    }

    @Override
    public List<UserResponseDto> getUsersByBusiness(Long businessId) {
        if (!businessRepository.existsById(businessId))
            throw new ResourceNotFoundException("Business not found");
        return AdapterUtils.convertToUserResponseList(userRepository.findByBusinessId(businessId));
    }

    @Override
    @Transactional
    public UserResponseDto updateUser(Long id, UserRequestDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (dto.getEmail() != null && !dto.getEmail().equals(user.getEmail())) {
            if (userRepository.findByEmail(dto.getEmail()).isPresent())
                throw new BadRequestException("Email already in use");
            user.setEmail(dto.getEmail());
        }

        if (dto.getName() != null) user.setName(dto.getName());
        if (dto.getPhone() != null) user.setPhone(dto.getPhone());
        if (dto.getPassword() != null) user.setPassword(passwordEncoder.encode(dto.getPassword()));
        if (dto.getRole() != null) user.setRole(dto.getRole());

        if (dto.getBusinessId() != null) {
            Business business = businessRepository.findById(dto.getBusinessId())
                    .orElseThrow(() -> new ResourceNotFoundException("Business not found"));
            user.getBusiness().add(business);
        }
        return AdapterUtils.convertToUserResponseDto(userRepository.save(user));
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        return AdapterUtils.convertToUserResponseList(userRepository.findAll());
    }
}
