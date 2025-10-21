package com.smartbooking.app.impl;

import com.smartbooking.app.dto.ServiceRequestDto;
import com.smartbooking.app.dto.ServiceResponseDto;
import com.smartbooking.app.entity.*;
import com.smartbooking.app.repository.*;
import com.smartbooking.app.service.ServiceDetailsService;
import com.smartbooking.app.utils.AdapterUtils;
import com.smartbooking.app.utils.AuthUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServiceDetailsImpl implements ServiceDetailsService {

    private final ServiceRepository serviceRepository;
    private final BusinessRepository businessRepository;
    private final ServiceProviderRepository providerRepository;

    public ServiceDetailsImpl(ServiceRepository serviceRepository,
                              BusinessRepository businessRepository,
                              ServiceProviderRepository providerRepository) {
        this.serviceRepository = serviceRepository;
        this.businessRepository = businessRepository;
        this.providerRepository = providerRepository;
    }

    private Business fetchOwnedBusiness(Long businessId) {
        Business b = businessRepository.findById(businessId)
                .orElseThrow(() -> new RuntimeException("Business not found"));
        if (!b.getOwner().getId().equals(AuthUtil.getCurrentUserId())) {
            throw new RuntimeException("Forbidden: business does not belong to owner");
        }
        return b;
    }

    @Override
    public ServiceResponseDto createService(ServiceRequestDto req) {

        Business business = fetchOwnedBusiness(req.getBusinessId());

        ServiceDetails entity = AdapterUtils.convertToServiceDetailsEntity(req);
        entity.setBusiness(business);
        return AdapterUtils.convertToServiceResponseDto(serviceRepository.save(entity));
    }

    @Override
    public List<ServiceResponseDto> getAllServicesByOwner() {
        return serviceRepository
                .findAllByBusinessOwnerId(AuthUtil.getCurrentUserId())
                .stream()
                .map(AdapterUtils::convertToServiceResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ServiceResponseDto> getServicesByBusinessId(Long businessId) {
        fetchOwnedBusiness(businessId);
        return serviceRepository.findByBusinessId(businessId)
                .stream()
                .map(AdapterUtils::convertToServiceResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ServiceResponseDto> getServicesByProvider(Long providerId) {
        ServiceProvider provider = providerRepository.findById(providerId)
                .orElseThrow(() -> new RuntimeException("Provider not found"));
        if (!provider.getBusiness().getOwner().getId().equals(AuthUtil.getCurrentUserId())) {
            throw new RuntimeException("Forbidden: provider not under owner’s business");
        }
        return serviceRepository.findByServiceProviderId(providerId)
                .stream()
                .map(AdapterUtils::convertToServiceResponseDto)
                .collect(Collectors.toList());
    }
    @Override
    public ServiceResponseDto updateService(Long serviceId, ServiceRequestDto req) {
        ServiceDetails service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        if (!service.getBusiness().getOwner().getId().equals(AuthUtil.getCurrentUserId())) {
            throw new RuntimeException("Forbidden: service not under owner’s business");
        }

        service.setName(req.getName());
        service.setDescription(req.getDescription());
        service.setPrice(req.getPrice());
        service.setDuration(req.getDurationMinutes());

        /* re‑assign provider if changed */
//        if (!service.getServiceProvider().getId().equals(req.getProviderId())) {
//            ServiceProvider newProvider = providerRepository.findById(req.getProviderId())
//                    .orElseThrow(() -> new RuntimeException("Provider not found"));
//            if (!newProvider.getBusiness().getId().equals(service.getBusiness().getId())) {
//                throw new RuntimeException("Provider does not belong to this business");
//            }
//            service.setServiceProvider(newProvider);
//        }
        //find the provider ;

//        Optional<ServiceProvider> providerById = providerRepository.findById(req.getProviderId());
//
//        Optional<ServiceProvider> serviceProviderOptional = service.getServiceProvider()
//                .stream().
//                filter(serviceProvider -> serviceProvider.getId().equals(req.getProviderId()))
//                .findFirst();
//        if(serviceProviderOptional.isPresent()) {
//
//        }

        return AdapterUtils.convertToServiceResponseDto(serviceRepository.save(service));
    }

    @Override
    public void deleteService(Long serviceId) {
        ServiceDetails service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        if (!service.getBusiness().getOwner().getId().equals(AuthUtil.getCurrentUserId())) {
            throw new RuntimeException("Forbidden: service not under owner’s business");
        }
        serviceRepository.delete(service);
    }

    @Override
    public ServiceResponseDto fetchServiceById(Long serviceId) {

        ServiceDetails serviceDetails = serviceRepository
                .findById(serviceId)
                .orElseThrow(()-> new EntityNotFoundException("Service not found with this ID"));
        return AdapterUtils.convertToServiceResponseDto(serviceDetails);
    }
}
