package com.mrv.archive.service;

import com.mrv.archive.dto.auth.JwtRequest;
import com.mrv.archive.dto.auth.JwtResponse;

public interface AuthService {


    JwtResponse login(
            JwtRequest loginRequest
    );

    JwtResponse refresh(
            String refreshToken
    );

}
