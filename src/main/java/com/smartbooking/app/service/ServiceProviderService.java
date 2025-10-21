package com.smartbooking.app.service;

import com.smartbooking.app.dto.ServiceProviderRequestDto;
import com.smartbooking.app.dto.ServiceProviderResponseDto;

import java.util.List;

public interface ServiceProviderService {

    ServiceProviderResponseDto createProvider(ServiceProviderRequestDto dto);

    ServiceProviderResponseDto updateProvider(Long id, ServiceProviderRequestDto dto);

    void deleteProvider(Long id);

    List<ServiceProviderResponseDto> getProvidersByBusiness(Long businessId);

    List<ServiceProviderResponseDto> getProvidersByOwner(Long ownerId);

    ServiceProviderResponseDto getById(Long id);
}
