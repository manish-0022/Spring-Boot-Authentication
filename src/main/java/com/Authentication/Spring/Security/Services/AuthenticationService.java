package com.Authentication.Spring.Security.Services;

import com.Authentication.Spring.Security.Entities.User;
import com.Authentication.Spring.Security.dto.JwtAuthenticationResponse;
import com.Authentication.Spring.Security.dto.RefreshTokenRequest;
import com.Authentication.Spring.Security.dto.SignInRequest;
import com.Authentication.Spring.Security.dto.SignUpRequest;

public interface AuthenticationService {

    User signup(SignUpRequest signUpRequest);

    JwtAuthenticationResponse signIn(SignInRequest signInRequest);

    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
