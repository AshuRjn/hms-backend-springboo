package com.hms.repository;

import com.hms.entity.Images;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImagesRepository extends JpaRepository<Images, Long> {

    List<Images> findByPropertyId(Long propertyId);
}