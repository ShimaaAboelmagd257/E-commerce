package com.techie.ecommerce.controller;

import com.techie.ecommerce.domain.dto.UserDto;
import com.techie.ecommerce.domain.model.UserEntity;
import com.techie.ecommerce.mappers.Mapper;
import com.techie.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private Mapper<UserEntity, UserDto> userMapper;

    @PostMapping
    public ResponseEntity<UserDto> createUser (@RequestBody UserDto userDto){
        UserEntity userEntity = userMapper.mapFrom(userDto);
        UserEntity savedUser = userService.save(userEntity);
        UserDto responseDto = userMapper.mapTo(savedUser);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long userId){
         Optional<UserEntity> userEntity = userService.getUserById(userId);
        return userEntity.map( user -> {
           UserDto responseDto = userMapper.mapTo(user);
            return new ResponseEntity<>(responseDto,HttpStatus.OK);

        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @GetMapping
    public  ResponseEntity<List<UserDto>> getAllUsers(){
         List<UserEntity> userEntities = userService.getAllUsers();
        List<UserDto>  userDtos = userEntities
                .stream()
                .map(userMapper::mapTo)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDtos);
    }
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

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId){
        userService.deleteUserById(userId);
        return ResponseEntity.noContent().build();
    }

}



