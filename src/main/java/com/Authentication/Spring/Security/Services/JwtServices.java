package com.Authentication.Spring.Security.Services;

import com.Authentication.Spring.Security.Entities.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashMap;
import java.util.Map;

public interface JwtServices {

    String extractUserName(String token);

    String generateToken(UserDetails userDetails);
    boolean isTokenValid(String token, UserDetails userDetails);

    String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails);


}
