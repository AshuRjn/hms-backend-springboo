package com.hms.controller;

import com.hms.Service.RoomService;
import com.hms.entity.Rooms;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/room")
public class RoomController {

    private RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }
    @PostMapping(value = "/add/propertyId/{propertyId}")
    public ResponseEntity<Rooms> addRooms(
            @PathVariable Long propertyId,
            @RequestBody Rooms roomsType

    ){
        Rooms addRooms = roomService.addRooms(roomsType, propertyId);
        return new ResponseEntity<>(addRooms, HttpStatus.CREATED);
    }
}
