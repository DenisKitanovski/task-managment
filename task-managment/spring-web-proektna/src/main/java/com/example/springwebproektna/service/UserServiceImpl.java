package com.example.springwebproektna.service;

import com.example.springwebproektna.domains.RegistrationForm;
import com.example.springwebproektna.domains.UserId;
import com.example.springwebproektna.exception.UserWithSuchUsernameAlreadyExists;
import com.example.springwebproektna.model.Role;
import com.example.springwebproektna.model.User;
import com.example.springwebproektna.repository.UserRepository;
import com.example.springwebproektna.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder encoder;
    private SecurityUtils securityUtils;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder encoder, SecurityUtils securityUtils) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.securityUtils = securityUtils;
    }

    @Override
    public UserId createNewUser(RegistrationForm form) {
        if(userRepository.findByUsername(form.getUsername())!=null){
            throw new UserWithSuchUsernameAlreadyExists();
        }
        User user = new User();
        user.setUsername(form.getUsername());
        user.setPassword(encoder.encode(form.getPassword()));
        user.setEmail(form.getEmail());
        user.setRoles(Role.USER);
        userRepository.save(user);
        return new UserId(user.getId());
    }

    @Override
    public User getCurrentUser() {
        User user = userRepository.findByUsername(securityUtils.getCurrentUserLogin());
        user.setPassword("");
        return user;
    }
}
