package com.hms.repository;

import com.hms.entity.City;
import com.hms.entity.Country;
import com.hms.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {

    boolean existsByNameAndCityIdAndCountryId(String name, Long cityId, Long countryId);

    // setting state = null after delete state
    @Modifying
    @Query("UPDATE Property p SET p.state = NULL WHERE p.state.id = :stateId")
    void updateStateToNull(@Param("stateId") Long stateId);

    //if we use more than one field to search hotels make all filed as name
    @Query("select p FROM Property p JOIN p.city c JOIN p.country co" +
            " where c.cityName=:name or co.countryName=:name")
    List<Property> searchHotels(
            @Param("name") String name
    );

}