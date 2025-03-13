package com.hms.controller;

import com.hms.Service.StateService;
import com.hms.payload.StateDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/state")
public class StateController {

    private final StateService stateService;

    public StateController(StateService stateService) {
        this.stateService = stateService;
    }

    @PostMapping("/add")
    public ResponseEntity<StateDTO> createState(
            @RequestBody StateDTO stateDTO
    ){
        StateDTO addState = stateService.addState(stateDTO);
        return new ResponseEntity<>(addState, HttpStatus.CREATED);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deleteState(
            @PathVariable Long id
    ){
        stateService.deleteState(id);
        return new ResponseEntity<>("State deleted successfully !",HttpStatus.OK);
    }

    @GetMapping("/states")
    public ResponseEntity<List<StateDTO>> getAllState(){
        List<StateDTO> allState = stateService.getAllState();
        return ResponseEntity.ok(allState);
    }

}
