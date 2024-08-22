package com.techie.ecommerce.controller.apiImpl;

import com.techie.ecommerce.controller.api.UserApi;
import com.techie.ecommerce.domain.dto.UserDto;
import com.techie.ecommerce.domain.model.UserEntity;
import com.techie.ecommerce.mappers.Mapper;
import com.techie.ecommerce.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController implements UserApi {
    private static final Log log = LogFactory.getLog(AuthController.class);

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
        log.info("Creating user with username: {}"+ userEntity.getUsername());

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
        if(!userService.isExists(userId)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

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
    public ResponseEntity<UserDto> getUserByUserNameOrEmail(String username, String email) {
        return null;
    }

    @Override
    public ResponseEntity<Void> changePassword(Long id, String newPassword) {
        return null;
    }

    @Override
    public ResponseEntity<Void> forgetPassword(String email) {
        return null;
    }

    @Override
    public ResponseEntity<Void> resetPassword(String token, String newPassword) {
        return null;
    }

    @Override
    public ResponseEntity<UserDto> getUserProfile() {
        return null;
    }

}



