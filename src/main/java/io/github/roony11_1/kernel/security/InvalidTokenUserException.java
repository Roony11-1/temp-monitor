package io.github.roony11_1.kernel.security;

import io.github.roony.error.core.exceptions.AppException;

public class InvalidTokenUserException extends AppException 
{
    public InvalidTokenUserException(String detail) 
    {
        super("JWT-001", "TokenUser inválido: " + detail, SecurityErrorCategories.INVALID_TOKEN_USER, "TokenUser inválido: " + detail);
    }
}
