package com.smartbooking.app.impl;

import com.smartbooking.app.dto.*;
import com.smartbooking.app.entity.*;
import com.smartbooking.app.exception.BadRequestException;
import com.smartbooking.app.repository.*;
import com.smartbooking.app.service.BusinessService;
import com.smartbooking.app.utils.AdapterUtils;
import com.smartbooking.app.utils.AuthUtil;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BusinessServiceImpl implements BusinessService {

    private final BusinessRepository businessRepo;
    private final UserRepository userRepo;

    public BusinessServiceImpl(BusinessRepository businessRepo, UserRepository userRepo) {
        this.businessRepo = businessRepo;
        this.userRepo = userRepo;
    }

    @Override
    public BusinessResponseDto createBusiness(BusinessRequestDto dto, Long ownerId) {
        User owner = userRepo.findById(ownerId)
                .orElseThrow(() -> new EntityNotFoundException("Owner not found"));

        Business business = AdapterUtils.convertToBusinessEntity(dto);
        business.setOwner(owner);

        return AdapterUtils.convertToBusinessResponseDto(businessRepo.save(business));
    }

    @Override
    public List<BusinessResponseDto> getAllBusinesses() {
//        Long loggedInUserId  = AuthUtil.getCurrentUserId();
//        businessRepo.findByOwnerId(loggedInUserId);
        return AdapterUtils.convertToBusinessResponseList(businessRepo.findAll());
    }

    @Override
    public List<BusinessResponseDto> getBusinessesByOwner(Long ownerId) {
        List<BusinessResponseDto>businessListByOwner = new ArrayList<>();
        User owner = userRepo.findById(ownerId)
                .orElseThrow(() -> new EntityNotFoundException("Owner not found"));
        Optional<List<Business>> businessListByOwnerId = businessRepo.findByOwnerId(ownerId);
        if(businessListByOwnerId.isPresent()) {
            businessListByOwner =  AdapterUtils.convertToBusinessResponseList(businessListByOwnerId.get());
        }
        return businessListByOwner;
    }

    @Override
    public BusinessResponseDto updateBusiness(Long ownerId, Long businessId , BusinessRequestDto dto) {

        Business existingBusiness = businessRepo.findById(businessId)
                .orElseThrow(() -> new EntityNotFoundException("Business not found"));

        if(!existingBusiness.getOwner().getId().equals(AuthUtil.getCurrentUserId())) {
            throw new BadRequestException("You don't have permission to update this business, as it is not owner by you.");
        }
        existingBusiness.setName(dto.getName());
        existingBusiness.setCategory(dto.getCategory());
        existingBusiness.setAddress(dto.getAddress());
        existingBusiness.setCity(dto.getCity());
        existingBusiness.setPhone(dto.getPhone());
        existingBusiness.setEmail(dto.getEmail());

        return AdapterUtils.convertToBusinessResponseDto(businessRepo.save(existingBusiness));
    }

    @Override
    public void deleteBusiness(Long ownerId, Long businessId) {

        Business existingBusiness = businessRepo.findById(businessId)
                .orElseThrow(() -> new EntityNotFoundException("Business not found"));

        if(!existingBusiness.getOwner().getId().equals(AuthUtil.getCurrentUserId())) {
            throw new BadRequestException("You don't have permission to update this business, as it is not owner by you.");
        }
        businessRepo.deleteById(businessId);
    }

    @Override
    public List<UserResponseDto> getAllBusinessOwners() {

        List<User> businessOwners = userRepo.findAll()
                .stream()
                .filter(user -> user.getRole().name().equals("OWNER"))
                .toList();
        return businessOwners.stream().map(AdapterUtils::convertToUserResponseDto)
                .collect(Collectors.toList());

    }
}
