package com.techie.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CustomUserDetailsService
       // implements UserDetailsService
{

    /*private static final Log log = LogFactory.getLog(CustomUserDetailsService.class);
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("CustomUserDetailsService:  loadUserByUsername"  );
        Optional<UserEntity> userEntity = userRepository.findByUsername(username);
        if (userEntity.isEmpty()) {
            throw new UsernameNotFoundException("UsernameNotFoundException: USER NOT FOUND IN THE DATABASE");
        }
        User user = new User(userEntity.get().getUsername(),
                userEntity.get().getPassword(), new ArrayList<>());
        log.info("User Found is " + userEntity.get().getUsername() );
        return user;

    }*/
}