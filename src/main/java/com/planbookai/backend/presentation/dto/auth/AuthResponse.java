package com.planbookai.backend.presentation.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthResponse {
    private String accessToken;
    private final String tokenType = "Bearer";
    private String email;
    private String hoTen;
    private String vaiTro;
}
