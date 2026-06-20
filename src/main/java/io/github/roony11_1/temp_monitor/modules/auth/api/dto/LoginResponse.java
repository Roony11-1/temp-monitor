package io.github.roony11_1.temp_monitor.modules.auth.api.dto;

import lombok.Data;

@Data
public class LoginResponse 
{
    private String token;
    private String email;
}
