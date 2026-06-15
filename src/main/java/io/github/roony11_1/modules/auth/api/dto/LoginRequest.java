package io.github.roony11_1.modules.auth.api.dto;

import lombok.Data;

@Data
public class LoginRequest 
{
    private String email;
    private String password;
}
