package com.smartbooking.app.impl;

import com.smartbooking.app.dto.ReviewResponseDto;
import com.smartbooking.app.entity.Booking;
import com.smartbooking.app.entity.Review;
import com.smartbooking.app.entity.User;
import com.smartbooking.app.exception.BadRequestException;
import com.smartbooking.app.repository.BookingRepository;
import com.smartbooking.app.repository.ReviewRepository;
import com.smartbooking.app.repository.UserRepository;
import com.smartbooking.app.service.ReviewService;
import com.smartbooking.app.utils.AdapterUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final BookingRepository bookingRepo;
    private final ReviewRepository reviewRepo;
    private final UserRepository userRepo;

    public ReviewServiceImpl(BookingRepository bookingRepo, ReviewRepository reviewRepo, UserRepository userRepo) {
        this.bookingRepo = bookingRepo;
        this.reviewRepo = reviewRepo;
        this.userRepo = userRepo;
    }

    @Transactional
    @Override
    public ReviewResponseDto submitReview(Long bookingId, Long clientId, Integer rating, String comment) {

        if (rating == null || rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5.");
        }

        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found: " + bookingId));

        User client = userRepo.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client not found: " + clientId));

        if (!booking.getClient().getId().equals(clientId)) {
            throw new BadRequestException("This booking does not belong to the current client.");
        }
        Optional<Review> existingOpt = reviewRepo.findByBookingIdAndClientId(bookingId, clientId);
        Review review;
        if (existingOpt.isPresent()) {
            review = existingOpt.get();
        } else {
            review = new Review();
            review.setBooking(booking);
            review.setClient(client);
        }
        review.setRating(rating);
        review.setComment(comment);
        Review savedReview = reviewRepo.save(review);
        return AdapterUtils.toReviewResponseDto(savedReview);
    }

    @Override
    public ReviewResponseDto getReviewForBooking(Long bookingId) {
        Review review = reviewRepo.findByBooking_Id(bookingId);
        return AdapterUtils.toReviewResponseDto(review);
    }
}
