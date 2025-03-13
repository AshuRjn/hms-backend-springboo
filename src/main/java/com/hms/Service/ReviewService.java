package com.hms.Service;

import com.hms.entity.AppUser;
import com.hms.entity.Property;
import com.hms.entity.Review;
import com.hms.repository.PropertyRepository;
import com.hms.repository.ReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final PropertyRepository propertyRepository;

    public ReviewService(ReviewRepository reviewRepository, PropertyRepository propertyRepository) {
        this.reviewRepository = reviewRepository;
        this.propertyRepository = propertyRepository;
    }

    public Review writeReview(Review review, Long propertyId, AppUser user) {

        // find the property id first
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found with the id = " + propertyId));

        // this is business logic that one user can not write multiple review
        if (reviewRepository.existsByAppUserAndProperty(user,property)){
           throw new IllegalStateException("Review already exists for this property !");
        }
         
        // if property exists  then set property id to review
        review.setProperty(property);
        // then set user details which come from jwt token
        review.setAppUser(user);
        return reviewRepository.save(review);
    }

    public List<Review> getUserReview(AppUser user) {
        List<Review> byAppUser = reviewRepository.findByAppUser(user);
        if (byAppUser.isEmpty()) {
            throw new RuntimeException("No reviews found for user: " + user.getUsername());
        }
        return byAppUser;
    }

    @Transactional
    public String deleteUserReview(AppUser user, Long propertyId) {
        Review review = reviewRepository.findByAppUserAndPropertyId(user, propertyId).
                orElseThrow(() -> new EntityNotFoundException("Review not found for this user on this property!"));

        reviewRepository.delete(review);
        return "Review deleted successfully!";
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();

    }


    @Transactional
    public Review updateReview(AppUser user, Review review, Long propertyId) {
        Review existingReview = reviewRepository.findByAppUserAndPropertyId(user, propertyId)
                .orElseThrow(() -> new EntityNotFoundException("Review does not exist on this property!"));

        if (review.getRating() != null) {
            existingReview.setRating(review.getRating());
        }
        if (review.getDescription() != null) {
            existingReview.setDescription(review.getDescription());
        }

        return reviewRepository.save(existingReview);
    }

}

