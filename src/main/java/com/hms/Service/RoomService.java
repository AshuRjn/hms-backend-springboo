package com.hms.Service;

import com.hms.entity.Property;
import com.hms.entity.Rooms;
import com.hms.repository.PropertyRepository;
import com.hms.repository.RoomsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class RoomService {
    private RoomsRepository roomsRepository;
    private PropertyRepository propertyRepository;

    public RoomService(RoomsRepository roomsRepository, PropertyRepository propertyRepository) {
        this.roomsRepository = roomsRepository;
        this.propertyRepository = propertyRepository;
    }

    public Rooms addRooms(Rooms roomsType, Long propertyId) {

        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new EntityNotFoundException("Property Not Found with this Id" + propertyId));


        roomsType.setProperty(property);
        Rooms saveRoom = roomsRepository.save(roomsType);
        return saveRoom;
    }
}
