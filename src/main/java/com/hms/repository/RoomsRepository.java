package com.hms.repository;

import com.hms.entity.Property;
import com.hms.entity.Rooms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface RoomsRepository extends JpaRepository<Rooms, Long> {

    @Query("SELECT r FROM Rooms r WHERE r.date BETWEEN :startDate AND :endDate " +
            "AND r.roomsType = :roomsType AND r.property.id = :propertyId")
    List<Rooms> findRoomsBetweenDates(
            @Param("startDate") LocalDate checkInDate,
            @Param("endDate") LocalDate checkOutDate,
            @Param("roomsType") String roomsType,
            @Param("propertyId") Long propertyId
    );

}