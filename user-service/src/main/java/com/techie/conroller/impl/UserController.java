package com.techie.conroller.impl;

import com.techie.conroller.api.UserApi;
import com.techie.domain.UserDto;
import com.techie.domain.UserEntity;
import com.techie.mappers.Mapper;
import com.techie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController implements UserApi {
    //private static final Log log = LogFactory.getLog(AuthController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private Mapper<UserEntity, UserDto> userMapper;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody UserDto userDto) {
        UserEntity userEntity = userMapper.mapFrom(userDto);
        //log.info("Creating user with username: {}"+ userEntity.getUsername());

        if (userEntity.getId() == null) {
//          userEntity.setPassword(passwordEncoder.encode(userDto.getPassword())); // Hashing the password
            UserEntity savedUser = userService.save(userEntity);
            UserDto responseDto = userMapper.mapTo(savedUser);
            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @Override

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId){
         Optional<UserEntity> userEntity = userService.getUserById(userId);
        return userEntity.map( user -> {
           UserDto responseDto = userMapper.mapTo(user);
            return new ResponseEntity<>(responseDto,HttpStatus.OK);

        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @Override
    @GetMapping
    public  ResponseEntity<List<UserDto>> getAllUsers(){
         List<UserEntity> userEntities = userService.getAllUsers();
        List<UserDto>  userDtos = userEntities
                .stream()
                .map(userMapper::mapTo)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDtos);
    }
    @Override

    @PutMapping(path = "/{userId}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable("userId") Long userId,
            @RequestBody UserDto userDto
    ){
//        if(!userService.isExists(userId)){
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }

        userDto.setId(userId);
        UserEntity updateUser = userMapper.mapFrom(userDto);
        UserEntity user = userService.save(updateUser);
        return new ResponseEntity<>(
                userMapper.mapTo(user),
                HttpStatus.OK);
    }
    @Override
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId){
        userService.deleteUserById(userId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping("/search")
    public ResponseEntity<UserDto> getUserByUsernameOrEmail(@RequestParam String username, @RequestParam String email) {
        Optional<UserEntity> userEntity = userService.getUserByUsernameOrEmail(username,email);
        return userEntity.map( user -> {
            UserDto responseDto = userMapper.mapTo(user);
            return new ResponseEntity<>(responseDto,HttpStatus.OK);

        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    @PutMapping("/{userId}/change-password")
    public ResponseEntity<Void> changePassword(@PathVariable Long userId,@RequestParam String newPassword) {
//        if(!userService.isExists(userId)){
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
        userService.changePassword(userId,newPassword);
        return ResponseEntity.ok().build();
    }

  /*  @Override
    @PostMapping("/forget-password")
    public ResponseEntity<Void> forgetPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        userService.setPasswordResetToken(email);
        return ResponseEntity.ok().build();
    }*/

    @Override
    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@RequestParam String token,@RequestBody Map<String, String> request) {
        String newPassword = request.get("newPassword");
        userService.resetPassword(token,newPassword);
        return ResponseEntity.ok().build();
    }

    @Override
    @GetMapping("/profile")
    public ResponseEntity<UserDto> getUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserEntity user = userService.getUserByUsername(username).orElseThrow(() -> new  UsernameNotFoundException("UserNot Found"));
        UserDto userDto = userMapper.mapTo(user);
        return new ResponseEntity<>(userDto,HttpStatus.OK);
    }

}



