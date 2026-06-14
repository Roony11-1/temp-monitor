package io.github.roony.modules.auth.api.dto;

import lombok.Data;

@Data
public class LoginResponse 
{
    private String token;
    private String email;
}
