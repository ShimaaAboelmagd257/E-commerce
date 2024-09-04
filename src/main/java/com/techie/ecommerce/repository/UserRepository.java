package com.techie.ecommerce.repository;

import com.techie.ecommerce.domain.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByUsername(String username);
    boolean existsByUsername(String username);
    @Query("SELECT u FROM UserEntity u WHERE u.username = :username OR u.email = :email")
    Optional<UserEntity>findByUsernameOrEmail(String username, String email);
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByRequestToken(String token);
}
