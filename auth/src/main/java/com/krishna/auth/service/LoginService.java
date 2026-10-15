package com.krishna.auth.service;

import com.krishna.auth.dto.LoginRequestDTO;
import com.krishna.auth.dto.LoginResponseDTO;
import com.krishna.auth.dto.TokenVerifyDTO;
import com.krishna.auth.util.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LoginService {
    @Autowired
    AuthenticationManager authenticationManager;
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDTO.getUserName(),
                            loginRequestDTO.getPassword()
                    )
            );
            loginResponseDTO.setToken(JWTUtil.createToken(loginRequestDTO.getUserName()));
        } catch (AuthenticationException e) {
            log.error("Authentication failed!", e);
        }
        return loginResponseDTO;
    }

    public String verifyToken(TokenVerifyDTO tokenVerifyDTO) {
        return JWTUtil.verifyToken(tokenVerifyDTO.getToken()) ? "Token is valid!" : "Token is in-valid!";
    }
}
