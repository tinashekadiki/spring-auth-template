package com.dot.ws.controllers;

import com.dot.ws.config.JwtTokenUtil;
import com.dot.ws.exceptions.UnauthorisedException;
import com.dot.ws.exceptions.UserAlreadyExistsException;
import com.dot.ws.dtos.models.User;
import com.dot.ws.dtos.responses.AuthResponse;
import com.dot.ws.dtos.requests.LoginRequest;
import com.dot.ws.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController @RequestMapping(path = "api/public")
public class AuthenticationController {

    @Autowired
    UserService userService;

    private final JwtTokenUtil jwtTokenUtil;

    public AuthenticationController(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/auth/register")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid User user) throws UserAlreadyExistsException {
        try{
            User savedUser = userService.create(user);
            AuthResponse authResponse = new AuthResponse(savedUser.getId(), savedUser.getName(), savedUser.getEmail(), jwtTokenUtil.generateAccessToken(savedUser), savedUser.getPhoneNumber());
            return ResponseEntity.ok()
                    .body(authResponse);
        }
        catch (UserAlreadyExistsException ex){
            throw new UserAlreadyExistsException("Email already taken");
        }
    }

    @PostMapping("/auth/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest loginRequest) throws UnauthorisedException{
        try{
            User user = userService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
            AuthResponse authResponse = new AuthResponse(user.getId(), user.getName(), user.getEmail(), jwtTokenUtil.generateAccessToken(user), user.getPhoneNumber());
            return ResponseEntity.ok()
                    .body(authResponse);
        } catch (NullPointerException | UnauthorisedException exception){
            throw new UnauthorisedException("Wrong email/password combination");
        }
    }
}
