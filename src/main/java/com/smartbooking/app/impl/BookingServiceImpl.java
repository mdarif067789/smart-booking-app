package com.smartbooking.app.impl;

import com.smartbooking.app.dto.BookingRequestDTO;
import com.smartbooking.app.dto.BookingResponseDto;
import com.smartbooking.app.entity.*;
import com.smartbooking.app.enums.BookingStatus;
import com.smartbooking.app.exception.BadRequestException;
import com.smartbooking.app.repository.*;
import com.smartbooking.app.service.BookingService;
import com.smartbooking.app.utils.AdapterUtils;
import com.smartbooking.app.utils.AuthUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepo;
    private final UserRepository userRepo;
    private final ServiceRepository serviceRepo;
    private final ServiceProviderRepository providerRepo;
    private final BusinessRepository businessRepo;

    public BookingServiceImpl(
            BookingRepository bookingRepo,
            UserRepository userRepo,
            ServiceRepository serviceRepo,
            ServiceProviderRepository providerRepo,
            BusinessRepository businessRepo) {
        this.bookingRepo = bookingRepo;
        this.userRepo = userRepo;
        this.serviceRepo = serviceRepo;
        this.providerRepo = providerRepo;
        this.businessRepo = businessRepo;
    }

    @Override
    @Transactional
    public BookingResponseDto createBooking(BookingRequestDTO dto) {

        // 1. Slot‑conflict check
        List<Booking> conflicts = bookingRepo.findByProviderIdAndStartTimeBetween(
                dto.getProviderId(), dto.getStartTime(), dto.getEndTime());
        if (!conflicts.isEmpty()) {
            throw new RuntimeException("Slot already booked");
        }

        // 2. Persist new booking
        Booking booking = new Booking();
        booking.setClient(userRepo.findById(dto.getClientId()).orElseThrow( () -> new EntityNotFoundException("Client not found with this Id")));
        booking.setProvider(providerRepo.findById(dto.getProviderId()).orElseThrow( () -> new EntityNotFoundException("Provider not found with this Id")));
        booking.setService(serviceRepo.findById(dto.getServiceId()).orElseThrow(() -> new EntityNotFoundException("Service not found with this Id")));
        booking.setBusiness(businessRepo.findById(dto.getBusinessId()).orElseThrow(() -> new EntityNotFoundException("Business not found with this Id")));;
        booking.setStartTime(dto.getStartTime());
        booking.setEndTime(dto.getEndTime());
        booking.setStatus(BookingStatus.CONFIRMED);
        booking.setAdditionalMessage(dto.getAdditionalMessage());
        Booking saved = bookingRepo.save(booking);

        return AdapterUtils.convertToBookingResponseDto(saved);
    }

    @Override
    public List<BookingResponseDto> getBookingsByClient(Long clientId) {
        return bookingRepo.findByClientId(clientId)
                .stream()
                .map(AdapterUtils::convertToBookingResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingResponseDto> getBookingsByProvider(Long providerId) {
        Long loggedInId = AuthUtil.getCurrentUserId();
        if (!loggedInId.equals(providerId)) {
            throw new BadRequestException("You don't have permission for this operation. Only Service Provider can view their bookings.");
        }
        return bookingRepo.findByProviderId(providerId)
                .stream()
                .map(AdapterUtils::convertToBookingResponseDto)
                .collect(Collectors.toList());
    }


    @Override
    public void cancelBooking(Long bookingId) {
        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepo.save(booking);
    }

}
