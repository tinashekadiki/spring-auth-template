package com.dot.ws.services;

import com.dot.ws.exceptions.UnauthorisedException;
import com.dot.ws.exceptions.UserAlreadyExistsException;
import com.dot.ws.dtos.models.User;
import com.dot.ws.repos.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public interface UserService {


    public User create(User user) throws UserAlreadyExistsException;

    public User authenticate(String email, String password) throws UnauthorisedException;

    public User findByEmail(String email);

    public boolean isPasswordCorrect(String rawPassword, User user);
}
