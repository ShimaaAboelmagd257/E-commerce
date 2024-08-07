package com.techie.ecommerce.service;

import com.techie.ecommerce.domain.model.UserEntity;
import com.techie.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserEntity save(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    @Override
    public Optional<UserEntity> getUserById(Long userId) {
        return  userRepository.findById(userId);
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public boolean isExists(Long userId) {
        return userRepository.existsById(userId);
    }

    @Override
    public void deleteUserById(Long userId) {
        UserEntity userEntity = userRepository.findById(userId).orElse(null);
        if (userEntity != null){
            userRepository.deleteById(userId);
        }


    }
    public UserEntity loadUserByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<UserDetails> getUserByUserName(String username) throws UsernameNotFoundException {
        UserEntity user = loadUserByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return Optional.of(new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>()));
    }


}
