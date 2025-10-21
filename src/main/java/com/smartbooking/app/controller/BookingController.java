package com.smartbooking.app.controller;

import com.smartbooking.app.dto.BookingRequestDTO;
import com.smartbooking.app.dto.BookingResponseDto;
import com.smartbooking.app.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@Tag(name = "Booking", description = "Create and manage bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Operation(summary = "Create booking with dynamic answers")
    @PostMapping()
    public ResponseEntity<BookingResponseDto> createBooking(
            @RequestBody BookingRequestDTO dto) {
        return ResponseEntity.ok(bookingService.createBooking(dto));
    }

    @Operation(summary = "List bookings by client")
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<BookingResponseDto>> getByClient(@PathVariable Long clientId) {
        return ResponseEntity.ok(bookingService.getBookingsByClient(clientId));
    }

    @Operation(summary = "List bookings by provider")
    @GetMapping("/provider/{providerId}")
    public ResponseEntity<List<BookingResponseDto>> fetchBookingByProvider(@PathVariable Long providerId) {
        return ResponseEntity.ok(bookingService.getBookingsByProvider(providerId));
    }

    @Operation(summary = "Cancel booking")
    @PutMapping("/cancel/{bookingId}")
    public ResponseEntity<Void> cancel(@PathVariable Long bookingId) {
        bookingService.cancelBooking(bookingId);
        return ResponseEntity.noContent().build();
    }
}



