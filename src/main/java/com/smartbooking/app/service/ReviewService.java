package com.smartbooking.app.service;

import com.smartbooking.app.dto.ReviewResponseDto;

public interface ReviewService {
    ReviewResponseDto submitReview(Long bookingId, Long clientId, Integer rating, String comment);
    ReviewResponseDto getReviewForBooking(Long bookingId);
}
