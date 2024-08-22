package com.techie.ecommerce.service;

import com.techie.ecommerce.domain.model.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserEntity save(UserEntity user);

    Optional<UserEntity> getUserById(Long userId);

    List<UserEntity> getAllUsers();

    boolean isExists(Long userId);

    void deleteUserById(Long userId);


    List<UserEntity> getUserByUsernameOrEmail(String username, String email);

    void changePassword(Long id, String newPassword);

    void setPasswordResetToken(String email);

    Optional<UserEntity> getUserByUsername(String username);

    void resetPassword(String token, String newPassword);
}
