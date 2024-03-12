package com.Authentication.Spring.Security.Services.impl;

import com.Authentication.Spring.Security.Entities.Role;
import com.Authentication.Spring.Security.Entities.User;
import com.Authentication.Spring.Security.Repository.UserRepository;
import com.Authentication.Spring.Security.Services.AuthenticationService;
import com.Authentication.Spring.Security.Services.JwtServices;
import com.Authentication.Spring.Security.dto.JwtAuthenticationResponse;
import com.Authentication.Spring.Security.dto.RefreshTokenRequest;
import com.Authentication.Spring.Security.dto.SignInRequest;
import com.Authentication.Spring.Security.dto.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor

public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;


    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtServices jwtServices;

    public User signup(SignUpRequest signUpRequest){
        User user = new User();


        user.setEmail(signUpRequest.getEmail());
        user.setFirstname(signUpRequest.getFirstName());
        user.setLastname(signUpRequest.getLastName());
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        return userRepository.save(user);

    }

    public JwtAuthenticationResponse signIn(SignInRequest signInRequest){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(),
                signInRequest.getPassword()));

        var user = userRepository.findByEmail(signInRequest.getEmail())
                .orElseThrow(() ->new IllegalArgumentException("Invalid email and password: "));
        var jwt = jwtServices.generateToken(user);
        var refreshToken = jwtServices.generateRefreshToken(new HashMap<>(), user);

        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();


        jwtAuthenticationResponse.setToken(jwt);

        jwtAuthenticationResponse.setRefreshToken(refreshToken);

        return jwtAuthenticationResponse;
    }

    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest){

        String userEmail = jwtServices.extractUserName(refreshTokenRequest.getToken());
        User user = userRepository.findByEmail(userEmail).orElseThrow();
        if(jwtServices.isTokenValid(refreshTokenRequest.getToken(),user)){

            var jwt = jwtServices.generateToken(user);

            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();


            jwtAuthenticationResponse.setToken(jwt);

            jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());

            return jwtAuthenticationResponse;
        }
        return null;
    }
}
