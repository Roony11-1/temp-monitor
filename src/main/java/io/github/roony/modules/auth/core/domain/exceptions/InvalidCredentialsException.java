package io.github.roony.modules.auth.core.domain.exceptions;

import io.github.roony.error.core.StandardErrorCategories;
import io.github.roony.error.core.exceptions.AppException;

public class InvalidCredentialsException extends AppException 
{
    public InvalidCredentialsException() 
    {
        super("AUTH-001", "Credenciales inválidas", StandardErrorCategories.ACCESS_DENIED, "Credenciales inválidas");
    }
}
