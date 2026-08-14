package com.krishna.auth.dto;

import lombok.Data;

@Data
public class LoginResponseDTO {
    private String token;
    private String tokenType="Bearer";
}
