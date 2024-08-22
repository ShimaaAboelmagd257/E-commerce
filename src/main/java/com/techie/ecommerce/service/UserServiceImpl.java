package com.techie.ecommerce.service;

import com.techie.ecommerce.domain.model.UserEntity;
import com.techie.ecommerce.repository.UserRepository;
import com.techie.ecommerce.security.JwtTokenProvider;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private static final Log log = LogFactory.getLog(UserServiceImpl.class);

    @Autowired
    private MailService mailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider provider;

    @Override
    public UserEntity save(UserEntity user) {
        if (user.getPassword() == null || userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Password cannot be null Or user is registered");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
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

    @Override
    public List<UserEntity> getUserByUsernameOrEmail(String username, String email) {
            return userRepository.findByUsernameOrEmail(username, email);
    }

    @Override
    public void changePassword(Long id, String newPassword) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(()-> new UsernameNotFoundException("User NotFound Exception"));
        userEntity.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(userEntity);
    }

    @Override
    public void setPasswordResetToken(String email) {
        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("User Not Found"));
        String token = provider.generateToken(userEntity.getUsername());
        mailService.sendPasswordRestToken(userEntity,token);
    }

    @Override
    public Optional<UserEntity> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void resetPassword(String token, String newPassword) {
        UserEntity userEntity = userRepository.findByRequestToken(token).orElseThrow(()->new UsernameNotFoundException("User Not Found"));
        userEntity.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(userEntity);
    }


}
