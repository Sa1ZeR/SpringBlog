package me.sa1zer_.springblog.service;

import me.sa1zer_.springblog.exceptions.UserExistsException;
import me.sa1zer_.springblog.models.ERole;
import me.sa1zer_.springblog.models.User;
import me.sa1zer_.springblog.payload.request.SignupRequest;
import me.sa1zer_.springblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(SignupRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setName(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.getRoles().add(ERole.ROLE_USER);

        try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw new UserExistsException("User " + user.getUsername() + " already exists!");
        }
    }
}