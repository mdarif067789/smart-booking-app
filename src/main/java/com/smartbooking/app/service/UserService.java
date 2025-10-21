package com.smartbooking.app.service;

import com.smartbooking.app.dto.UserRequestDto;
import com.smartbooking.app.dto.UserResponseDto;

import java.util.List;

public interface UserService {
    UserResponseDto createUser(UserRequestDto dto);
    UserResponseDto getUserById(Long id);
    List<UserResponseDto> getUsersByBusiness(Long businessId);
    UserResponseDto updateUser(Long id, UserRequestDto dto);
    void deleteUser(Long id);
    List<UserResponseDto> getAllUsers();
}
