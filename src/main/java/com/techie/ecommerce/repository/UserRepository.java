package com.techie.ecommerce.repository;

import com.techie.ecommerce.domain.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByUsername(String username);
    boolean existsByUsername(String username);
    Optional<UserEntity> findByUsernameOrEmail(String username, String email);
    Optional<UserEntity> finByEmail(String email);
    Optional<UserEntity> findByRequestTopken(String token);
}
