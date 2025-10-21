package com.smartbooking.app.repository;

import com.smartbooking.app.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {


    @Query(value = "select * from reviews r JOIN Booking b ON r.booking_id = b.id WHERE b.provider_id = :providerId", nativeQuery = true)
    List<Review> findByBooking_Provider_Id(@Param("providerId") Long providerId);

    @Query(value = "select * from reviews  WHERE booking_id = :bookingId",nativeQuery = true)
    Review findByBooking_Id(@Param("bookingId") Long bookingId);

    @Query(value = "select * from reviews  WHERE booking_id = :bookingId AND client_id =:clientId", nativeQuery = true)
    Optional<Review> findByBookingIdAndClientId(@Param("bookingId") Long bookingId, @Param("clientId") Long clientId);
}
