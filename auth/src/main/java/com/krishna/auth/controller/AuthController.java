package com.krishna.auth.controller;

import com.krishna.auth.dto.LoginRequestDTO;
import com.krishna.auth.dto.LoginResponseDTO;
import com.krishna.auth.dto.TokenVerifyDTO;
import com.krishna.auth.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class AuthController {
    @Autowired
    LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        LoginResponseDTO loginResponseDTO =  loginService.login(loginRequestDTO);
        return ResponseEntity.ok(loginResponseDTO);
    }

    @PostMapping("/verify")
    public ResponseEntity<String> tokenVerify(@RequestBody TokenVerifyDTO tokenVerifyDTO) {
        String tokenVerification = loginService.verifyToken(tokenVerifyDTO);
        return ResponseEntity.ok(tokenVerification);
    }
}
