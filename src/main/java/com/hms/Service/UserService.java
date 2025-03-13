package com.hms.Service;

import com.hms.entity.AppUser;
import com.hms.payload.LoginDTO;
import com.hms.payload.UserDTO;
import com.hms.repository.AppUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final JWTService jwtService;
    private final ModelMapper modelMapper;
    private final AppUserRepository userRepository;

    public UserService(JWTService jwtService, ModelMapper modelMapper, AppUserRepository userRepository) {
        this.jwtService = jwtService;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }
    UserDTO mapToDTO(AppUser appUser){
        return modelMapper.map(appUser, UserDTO.class);
    }
    AppUser mapToEntity(UserDTO userDTO){
        return modelMapper.map(userDTO, AppUser.class);
    }

    public UserDTO registerUser(UserDTO userDTO) {
        AppUser appUser = mapToEntity(userDTO);
        Optional<AppUser> byUsername = userRepository.findByUsername(appUser.getUsername());
        if (byUsername.isPresent()){
            throw  new RuntimeException("Username already Exist");
        }
        Optional<AppUser> byEmail = userRepository.findByEmail(appUser.getEmail());
        if (byEmail.isPresent()){
            throw new RuntimeException("Invalid Email Details !");
        }else {
            String enPw = BCrypt.hashpw(appUser.getPassword(), BCrypt.gensalt(5));
            appUser.setPassword(enPw);
            appUser.setRole("ROLE_USER");
            AppUser save = userRepository.save(appUser);
            return mapToDTO(save);
        }
    }

    public String verifyLogin(LoginDTO loginDTO) {
        Optional<AppUser> byUsername = userRepository.findByUsername(loginDTO.getUsername());
        if (byUsername.isPresent()){
            AppUser appUser = byUsername.get();
           if (BCrypt.checkpw(loginDTO.getPassword(),appUser.getPassword()));
           return jwtService.generateToken(appUser.getUsername());
        }else {
            return "Invalid Details !";
        }
    }
   // OWNER SIGNUP
    public UserDTO addUser(UserDTO userDTO) {
        AppUser user = mapToEntity(userDTO);
        Optional<AppUser> byUsername = userRepository.findByUsername(user.getUsername());
        if (byUsername.isPresent()){
            throw new RuntimeException("User already exists !");
        }
        Optional<AppUser> byEmail = userRepository.findByEmail(user.getEmail());
        if (byEmail.isPresent()){
            throw new RuntimeException("Invalid email id !");
        }else {
            String enPw = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(5));
            user.setPassword(enPw);
            user.setRole("ROLE_OWNER");
            AppUser save = userRepository.save(user);
            return mapToDTO(save);
        }
    }
}
