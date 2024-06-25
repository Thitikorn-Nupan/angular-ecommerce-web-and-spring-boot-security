package com.ttknpdev.backend.services.secure;

import com.ttknpdev.backend.entities.secure.User;
import com.ttknpdev.backend.repositories.secure.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;
    private PasswordEncoder bcryptEncoder;
    @Autowired
    public UserService(UserRepository repository,
                       @Qualifier("bcryptEncoder") PasswordEncoder bcryptEncoder) {
        this.userRepository = repository;
        this.bcryptEncoder = bcryptEncoder;
    }

    public User create(User user) {
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        newUser.setRoles(user.getRoles());
        return userRepository.save(newUser);
    }

    public User read(String username) {
        return userRepository.findByUsername(username);
    }
}
