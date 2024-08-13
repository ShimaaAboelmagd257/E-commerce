package com.techie.ecommerce.controller;

import com.techie.ecommerce.domain.dto.UserDto;
import com.techie.ecommerce.domain.model.UserEntity;
import com.techie.ecommerce.mappers.Mapper;
import com.techie.ecommerce.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

/*    @Autowired
    private AuthenticationService service;
    @Autowired
     private Mapper<UserEntity, UserDto> userMapper;

    @PostMapping(path = "/login")
    public ResponseEntity<String> login(@RequestBody UserDto userDto){
        String token = service.login(userDto.getEmail(),userDto.getPassword());
        return ResponseEntity.ok(token);
    }

    @PutMapping(path = "/register")
    public ResponseEntity<UserDto> signUp(@RequestBody UserDto userDto){
        UserEntity userEntity = userMapper.mapFrom(userDto);
        UserEntity registeredUser = service.signUp(userEntity);
        UserDto responseDto = userMapper.mapTo(registeredUser);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping(path = "/logout")
    public ResponseEntity<Void> logout(){
        service.logout();
        return ResponseEntity.ok().build();
    }*/


}
