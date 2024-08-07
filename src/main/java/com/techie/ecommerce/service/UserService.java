package com.techie.ecommerce.service;

import com.techie.ecommerce.domain.model.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserEntity save(UserEntity userEntity);

    Optional<UserEntity> getUserById(Long userId);

    List<UserEntity> getAllUsers();

    boolean isExists(Long userId);

    void deleteUserById(Long userId);

    Optional<UserDetails> getUserByUserName(String userName);

}
