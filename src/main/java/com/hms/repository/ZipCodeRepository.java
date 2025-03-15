package com.hms.repository;

import com.hms.entity.ZipCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ZipCodeRepository extends JpaRepository<ZipCode, Long> {

    Optional<ZipCode> findByZipCode(String zipCode);
}