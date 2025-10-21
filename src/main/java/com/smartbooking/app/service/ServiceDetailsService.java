package com.smartbooking.app.service;

import com.smartbooking.app.dto.ServiceRequestDto;
import com.smartbooking.app.dto.ServiceResponseDto;

import java.util.List;

public interface ServiceDetailsService {

    ServiceResponseDto createService(ServiceRequestDto req);

    List<ServiceResponseDto> getAllServicesByOwner();

    List<ServiceResponseDto> getServicesByBusinessId(Long businessId);

    List<ServiceResponseDto> getServicesByProvider(Long providerId);

    ServiceResponseDto updateService(Long serviceId, ServiceRequestDto req);

    void deleteService(Long serviceId);

    ServiceResponseDto fetchServiceById(Long serviceId);
}
