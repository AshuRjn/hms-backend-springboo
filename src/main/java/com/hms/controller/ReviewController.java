package com.hms.controller;

import com.hms.Service.ReviewService;
import com.hms.entity.AppUser;
import com.hms.entity.Review;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/review")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/write")
    private ResponseEntity<Review> writeReview(
            @RequestBody Review review,
            @RequestParam Long propertyId,
            // use to get user details from jwt token
            @AuthenticationPrincipal AppUser user
            ){
        Review writtenReview = reviewService.writeReview(review, propertyId, user);
        return new ResponseEntity<>(writtenReview, HttpStatus.CREATED);
    }

    @GetMapping("/user/review")
    public ResponseEntity<List<Review>> getUseReview(
            @AuthenticationPrincipal AppUser user
    ){
        List<Review> userReview = reviewService.getUserReview(user);
        return new ResponseEntity<>(userReview,HttpStatus.OK);
    }

    @DeleteMapping("/reviews/propertyId/{propertyId}")
    public ResponseEntity<String> deleteUserReview(
            @AuthenticationPrincipal AppUser user,
            @PathVariable Long propertyId
    ){ try {
        String response = reviewService.deleteUserReview(user, propertyId);
        return ResponseEntity.ok(response);
    } catch (EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
    }

    @GetMapping("/reviewsList")
    public ResponseEntity<List<Review>> getAllReviews(){
        List<Review> allReview = reviewService.getAllReviews();
        return new ResponseEntity<>(allReview,HttpStatus.OK);
    }

    @PutMapping("/reviews")
    public ResponseEntity<Review> updateReview(
            @AuthenticationPrincipal AppUser user,
            @RequestParam Long propertyId,
            @RequestBody Review review
    ){
        Review updatedReview = reviewService.updateReview(user, review, propertyId);
        return ResponseEntity.accepted().body(updatedReview);
    }

}
