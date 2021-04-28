package com.dot.ws.implementation;

import com.dot.ws.exceptions.UnauthorisedException;
import com.dot.ws.exceptions.UserAlreadyExistsException;
import com.dot.ws.dtos.models.User;
import com.dot.ws.repos.UserRepository;

import com.dot.ws.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User create(User user) throws UserAlreadyExistsException {
        if(userRepository.findFirstByEmail(user.getEmail()) != null){
            System.out.println(user.getEmail());
            throw new UserAlreadyExistsException("Email already taken");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User authenticate(String email, String password) throws UnauthorisedException {
        User user = findByEmail(email);
        if(!isPasswordCorrect(password, user)){
            throw new UnauthorisedException("Password/Email is invalid");
        }
        return user;
    }

    public User findByEmail(String email){
        return userRepository.findFirstByEmail(email);
    }

    public boolean isPasswordCorrect(String rawPassword, User user){
        return passwordEncoder.matches(rawPassword, user.getPassword());
    }
}
