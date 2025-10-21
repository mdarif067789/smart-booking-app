package com.smartbooking.app.impl;

import com.smartbooking.app.dto.ServiceProviderRequestDto;
import com.smartbooking.app.dto.ServiceProviderResponseDto;
import com.smartbooking.app.entity.Business;
import com.smartbooking.app.entity.ServiceDetails;
import com.smartbooking.app.entity.ServiceProvider;
import com.smartbooking.app.exception.BadRequestException;
import com.smartbooking.app.repository.BusinessRepository;
import com.smartbooking.app.repository.ServiceProviderRepository;
import com.smartbooking.app.repository.ServiceRepository;
import com.smartbooking.app.service.ServiceProviderService;
import com.smartbooking.app.utils.AdapterUtils;
import com.smartbooking.app.utils.AuthUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceProviderServiceImpl implements ServiceProviderService {

    private final ServiceProviderRepository providerRepo;
    private final BusinessRepository businessRepo;

    private final ServiceRepository serviceRepository;

    public ServiceProviderServiceImpl(ServiceProviderRepository providerRepo, BusinessRepository businessRepo, ServiceRepository serviceRepository) {
        this.providerRepo = providerRepo;
        this.businessRepo = businessRepo;
        this.serviceRepository = serviceRepository;
    }


    @Override
    public ServiceProviderResponseDto createProvider(ServiceProviderRequestDto dto) {
        ServiceDetails serviceDetails = serviceRepository.findById(dto.getServiceId())
                .orElseThrow(() -> new EntityNotFoundException("Service not found"));

        Business business = businessRepo.findById(dto.getBusinessId())
                .orElseThrow(() -> new EntityNotFoundException("Business not found"));


        //check if it ia a owner role or not :
        if(!AuthUtil.getCurrentUserId().equals(business.getOwner().getId())) {
            throw new BadRequestException("Don't have permission to register service Provider , only Business Owners have permission for this.");
        }

        ServiceProvider sp = AdapterUtils.convertToServiceProviderEntity(dto, serviceDetails, business);
        return AdapterUtils.convertToServiceProviderResponseDto(providerRepo.save(sp));
    }

    @Override
    public ServiceProviderResponseDto updateProvider(Long id, ServiceProviderRequestDto dto) {
        ServiceProvider serviceProvider = providerRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Provider not found"));

        if(!AuthUtil.getCurrentUserId().equals(serviceProvider.getBusiness().getOwner().getId())) {
            throw new BadRequestException("Don't have permission to update service Provider , only Business Owners have permission for this.");
        }

        AdapterUtils.copyToExisting(dto, serviceProvider);
        return AdapterUtils.convertToServiceProviderResponseDto(providerRepo.save(serviceProvider));
    }

    @Override
    public void deleteProvider(Long id) {
        ServiceProvider serviceProvider = providerRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Provider not found"));

        if(!AuthUtil.getCurrentUserId().equals(serviceProvider.getBusiness().getOwner().getId())) {
            throw new BadRequestException("Don't have permission to delete service Provider , only Business Owners have permission for this.");
        }
        providerRepo.delete(serviceProvider);
    }

    @Override
    public List<ServiceProviderResponseDto> getProvidersByBusiness(Long businessId) {
        Business existingBusiness = businessRepo.findById(businessId)
                .orElseThrow(() -> new EntityNotFoundException("Business not found"));

        //check if it ia a owner role or not :
        if(!AuthUtil.getCurrentUserId().equals(existingBusiness.getOwner().getId())) {
            throw new BadRequestException("Don't have permission to fetch service Providers which are not under your business");
        }

        return AdapterUtils.convertToServiceProviderResponseList(providerRepo.findByBusinessId(businessId));
    }

    @Override
    public List<ServiceProviderResponseDto> getProvidersByOwner(Long ownerId) {
        return AdapterUtils.convertToServiceProviderResponseList(providerRepo.findByBusinessOwnerId(ownerId));
    }

    @Override
    public ServiceProviderResponseDto getById(Long id) {

        ServiceProvider serviceProvider = providerRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Provider not found"));
        return AdapterUtils.convertToServiceProviderResponseDto(serviceProvider);
    }
}
