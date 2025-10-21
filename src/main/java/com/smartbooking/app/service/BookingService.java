package com.smartbooking.app.service;

import com.smartbooking.app.dto.BookingRequestDTO;
import com.smartbooking.app.dto.BookingResponseDto;

import java.util.List;

public interface BookingService {
    BookingResponseDto createBooking(BookingRequestDTO bookingRequest);
    List<BookingResponseDto> getBookingsByClient(Long clientId);
    List<BookingResponseDto> getBookingsByProvider(Long providerId);
    void cancelBooking(Long bookingId);
}
