package com.smartbooking.app.utils;

import com.smartbooking.app.dto.*;
import com.smartbooking.app.entity.*;

import java.util.List;
import java.util.stream.Collectors;

/** Utility for converting between DTOs and Entities. */
public final class AdapterUtils {

    private AdapterUtils() {
        // Utility class, no instantiation
    }

    /* ───────────── USER MAPPERS ───────────── */

    public static User convertToUserEntity(UserRequestDto userDto) {
        if (userDto == null) return null;

        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPhone(userDto.getPhone());
        user.setRole(userDto.getRole());
        user.setPassword(userDto.getPassword()); // Will be encoded in service
        return user;
    }

    public static UserResponseDto convertToUserResponseDto(User user) {
        if (user == null) return null;

        UserResponseDto response = new UserResponseDto();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setRole(user.getRole());
        response.setBusinessOwned(
                user.getBusiness()
                        .stream()
                        .map(AdapterUtils::convertToBusinessResponseDto)
                        .collect(Collectors.toList())
        );
        return response;
    }

    public static List<UserResponseDto> convertToUserResponseList(List<User> users) {
        return users.stream().map(AdapterUtils::convertToUserResponseDto).collect(Collectors.toList());
    }


    public static Business convertToBusinessEntity(BusinessRequestDto businessDto) {
        if (businessDto == null) return null;

        Business business = new Business();
        business.setName(businessDto.getName());
        business.setCategory(businessDto.getCategory());
        business.setAddress(businessDto.getAddress());
        business.setCity(businessDto.getCity());
        business.setPhone(businessDto.getPhone());
        business.setEmail(businessDto.getEmail());
        return business;
    }

    public static BusinessResponseDto convertToBusinessResponseDto(Business business) {
        if (business == null) return null;

        BusinessResponseDto dto = new BusinessResponseDto();
        dto.setId(business.getId());
        dto.setName(business.getName());
        dto.setCategory(business.getCategory());
        dto.setAddress(business.getAddress());
        dto.setCity(business.getCity());
        dto.setPhone(business.getPhone());
        dto.setEmail(business.getEmail());
        dto.setOwnerId(business.getOwner() != null ? business.getOwner().getId() : null);
        return dto;
    }

    public static List<BusinessResponseDto> convertToBusinessResponseList(List<Business> businesses) {
        return businesses.stream()
                .map(AdapterUtils::convertToBusinessResponseDto)
                .collect(Collectors.toList());
    }

    /* ───────────── SERVICE PROVIDER MAPPERS ───────────── */

    public static ServiceProvider convertToServiceProviderEntity(ServiceProviderRequestDto providerDto, ServiceDetails serviceDetails,  Business business) {
        if (providerDto == null) return null;

        ServiceProvider provider = new ServiceProvider();
        provider.setName(providerDto.getName());
        provider.setEmail(providerDto.getEmail());
        provider.setPhone(providerDto.getPhone());
        provider.setSpecialization(providerDto.getSpecialization());
        provider.setBusiness(business);
        provider.setServices(serviceDetails);
        return provider;
    }

    public static void updateServiceProviderFromDto(ServiceProviderRequestDto providerDto, ServiceProvider existingProvider) {
        if (providerDto.getName() != null) existingProvider.setName(providerDto.getName());
        if (providerDto.getEmail() != null) existingProvider.setEmail(providerDto.getEmail());
        if (providerDto.getPhone() != null) existingProvider.setPhone(providerDto.getPhone());
        if (providerDto.getSpecialization() != null) existingProvider.setSpecialization(providerDto.getSpecialization());
    }

    public static ServiceProviderResponseDto convertToServiceProviderResponseDto(ServiceProvider provider) {
        if (provider == null) return null;

        ServiceProviderResponseDto dto = new ServiceProviderResponseDto();
        dto.setId(provider.getId());
        dto.setName(provider.getName());
        dto.setEmail(provider.getEmail());
        dto.setPhone(provider.getPhone());
        dto.setSpecialization(provider.getSpecialization());

        if (provider.getBusiness() != null) {
            dto.setBusinessId(provider.getBusiness().getId());
            dto.setBusinessName(provider.getBusiness().getName());
        }

        return dto;
    }

    public static List<ServiceProviderResponseDto> convertToServiceProviderResponseList(List<ServiceProvider> providers) {
        return providers.stream()
                .map(AdapterUtils::convertToServiceProviderResponseDto)
                .collect(Collectors.toList());
    }

    /* ───────────── BOOKING MAPPERS ───────────── */

    public static BookingResponseDto convertToBookingResponseDto(Booking booking) {
        BookingResponseDto dto = new BookingResponseDto();

        dto.setId(booking.getId());
        dto.setStatus(booking.getStatus().name());
        dto.setStartTime(booking.getStartTime());
        dto.setEndTime(booking.getEndTime());
        dto.setAdditionalMessage(booking.getAdditionalMessage());
        if (booking.getClient() != null) {
            dto.setClientName(booking.getClient().getName());
        }

        if (booking.getService() != null) {
            dto.setServiceName(booking.getService().getName());
        }

        if (booking.getProvider() != null) {
            dto.setProviderName(booking.getProvider().getName());
        }

        if (booking.getBusiness() != null) {
            dto.setBusinessName(booking.getBusiness().getName());
        }

        return dto;
    }

    /* ───────────── SERVICE MAPPERS ───────────── */

    public static ServiceDetails convertToServiceDetailsEntity(ServiceRequestDto serviceDto) {
        if (serviceDto == null) return null;

        ServiceDetails service = new ServiceDetails();
        service.setName(serviceDto.getName());
        service.setDescription(serviceDto.getDescription());
        service.setPrice(serviceDto.getPrice());
        service.setDuration(serviceDto.getDurationMinutes());
        return service;
    }

    public static ServiceResponseDto convertToServiceResponseDto(ServiceDetails service) {
        if (service == null) return null;

        ServiceResponseDto dto = new ServiceResponseDto();
        dto.setId(service.getId());
        dto.setName(service.getName());
        dto.setDescription(service.getDescription());
        dto.setPrice(service.getPrice());
        dto.setDurationMinutes(service.getDuration());

        if (service.getBusiness() != null) {
            dto.setBusinessId(service.getBusiness().getId());
            dto.setBusinessName(service.getBusiness().getName());
        }

        return dto;
    }

    public static void copyToExisting(ServiceProviderRequestDto dto, ServiceProvider target) {
        if (dto.getName() != null)          target.setName(dto.getName());
        if (dto.getEmail() != null)         target.setEmail(dto.getEmail());
        if (dto.getPhone() != null)         target.setPhone(dto.getPhone());
        if (dto.getSpecialization() != null)target.setSpecialization(dto.getSpecialization());
    }

    // Entity → DTO

    public static ReviewResponseDto toReviewResponseDto(Review review) {
        ReviewResponseDto dto = new ReviewResponseDto();
        dto.setBookingId(review.getBooking() != null ? review.getBooking().getId() : null);
        dto.setRating(review.getRating());
        dto.setComment(review.getComment());
        return dto;
    }

    // DTO → Entity
    public static Review toReviewEntity(ReviewRequestDto dto) {
        Review review = new Review();
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());
        return review;
    }

}
