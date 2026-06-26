package io.github.roony11_1.temp_monitor.kernel.security.service;

import io.github.roony11_1.temp_monitor.kernel.security.model.TokenUser;

public interface IUserCredentialsService 
{
    /**
     * Valida credenciales y devuelve el usuario autenticado.
     * Lanza excepciones de dominio si algo falla.
     */
    TokenUser authenticate(String email, String rawPassword);
}
