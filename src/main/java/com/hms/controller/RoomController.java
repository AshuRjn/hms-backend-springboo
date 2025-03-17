package com.hms.controller;


import com.hms.Service.RoomService;
import com.hms.entity.Rooms;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/room")
public class RoomController {

    private RoomService roomService;


    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping("value = /add/propertyId/{propertyId}, consumes = application/json")
    public ResponseEntity<Rooms> createRoom(
            @RequestBody Rooms rooms,
            @PathVariable Long propertyId
    ){
        Rooms addRooms = roomService.addRooms(rooms, propertyId);
        return new ResponseEntity<>(addRooms, HttpStatus.CREATED);
    }

    @GetMapping("/rooms")
    public ResponseEntity<List<Rooms>> getAllRooms(){
        List<Rooms> allRooms = roomService.getAllRooms();
        return new ResponseEntity<>(allRooms,HttpStatus.OK);
    }
}
