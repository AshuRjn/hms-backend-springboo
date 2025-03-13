package com.hms.controller;

import com.hms.Service.UserService;
import com.hms.payload.LoginDTO;
import com.hms.payload.TokenDTO;
import com.hms.payload.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/hms/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> createUser(
            @RequestBody UserDTO userDTO
    ){
        UserDTO user = userService.registerUser(userDTO);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/property-owner-signup")
    public ResponseEntity<UserDTO> addUser(
            @RequestBody UserDTO userDTO
    ){
        UserDTO user = userService.addUser(userDTO);
        return new ResponseEntity<>(user,HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> verifyLogin(
            @RequestBody LoginDTO loginDTO
    ) {
        String token = userService.verifyLogin(loginDTO);
        if (token != null) {
            TokenDTO tokenDTO = new TokenDTO();
            tokenDTO.setToken(token);
            tokenDTO.setType("JWT TOKEN");
            return new ResponseEntity<>(tokenDTO,HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid username/password", HttpStatus.UNAUTHORIZED);
        }
    }
}
