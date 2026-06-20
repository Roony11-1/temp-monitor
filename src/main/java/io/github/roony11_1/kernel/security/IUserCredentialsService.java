package io.github.roony11_1.kernel.security;

public interface IUserCredentialsService 
{
    /**
     * Valida credenciales y devuelve el usuario autenticado.
     * Lanza excepciones de dominio si algo falla.
     */
    TokenUser authenticate(String email, String rawPassword);
}