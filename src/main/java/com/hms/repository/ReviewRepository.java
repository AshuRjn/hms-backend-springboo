package com.hms.repository;

import com.hms.entity.AppUser;
import com.hms.entity.Property;
import com.hms.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {


    List<Review> findByAppUser(AppUser user);

    Boolean existsByAppUserAndProperty(AppUser user, Property property);
    Optional<Review> findByAppUserAndPropertyId(AppUser user, Long propertyId);
}
