package com.smartbooking.app.repository;

import com.smartbooking.app.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByProviderIdAndStartTimeBetween(Long providerId, LocalDateTime start, LocalDateTime end);


    List<Booking> findByClientId(Long clientId);
    
    List<Booking> findByProviderId(Long providerId);
}
