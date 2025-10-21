package com.smartbooking.app.controller;

import com.smartbooking.app.dto.ReviewRequestDto;
import com.smartbooking.app.dto.ReviewResponseDto;
import com.smartbooking.app.service.ReviewService;
import com.smartbooking.app.utils.AuthUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
@Tag(name = "Reviews", description = "Create & view client reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }
    @Operation(summary = "Submit a review for a booking")
    @PostMapping
    public ResponseEntity<ReviewResponseDto> submitReview(@RequestBody ReviewRequestDto reviewRequestDto) {
        Long clientId = AuthUtil.getCurrentUserId();
        return ResponseEntity.ok(
                reviewService.submitReview(
                        reviewRequestDto.getBookingId(),
                        clientId,
                        reviewRequestDto.getRating(),
                        reviewRequestDto.getComment()
                )
        );
    }
    @Operation(summary = "Get reviews for a specific service")
    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<ReviewResponseDto> getReviewsForBooking(@PathVariable Long bookingId) {
        return ResponseEntity.ok(reviewService.getReviewForBooking(bookingId));
    }
}
