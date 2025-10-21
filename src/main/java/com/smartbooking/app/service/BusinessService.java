package com.smartbooking.app.service;

import com.smartbooking.app.dto.BusinessRequestDto;
import com.smartbooking.app.dto.BusinessResponseDto;
import com.smartbooking.app.dto.UserResponseDto;

import java.util.List;

public interface BusinessService {

    BusinessResponseDto createBusiness(BusinessRequestDto dto, Long ownerId);

    List<BusinessResponseDto> getAllBusinesses();

    List<BusinessResponseDto> getBusinessesByOwner(Long ownerId);

    BusinessResponseDto updateBusiness(Long ownerId, Long businessId, BusinessRequestDto dto);

    void deleteBusiness(Long ownerId ,  Long businessId);

    List<UserResponseDto> getAllBusinessOwners();
}
