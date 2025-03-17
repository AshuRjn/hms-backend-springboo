package com.hms.Service;

import com.hms.entity.Property;
import com.hms.entity.Rooms;
import com.hms.repository.PropertyRepository;
import com.hms.repository.RoomsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {
    private RoomsRepository roomsRepository;
    private PropertyRepository propertyRepository;

    public RoomService(RoomsRepository roomsRepository, PropertyRepository propertyRepository) {
        this.roomsRepository = roomsRepository;
        this.propertyRepository = propertyRepository;
    }


    public Rooms addRooms(Rooms rooms, Long propertyId) {

        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new EntityNotFoundException("Property with this id not exists" + propertyId));

        rooms.setProperty(property);
        return roomsRepository.save(rooms);

    }

    public List<Rooms> getAllRooms() {
        List<Rooms> roomsList = roomsRepository.findAll();
        return roomsList;
    }
}
