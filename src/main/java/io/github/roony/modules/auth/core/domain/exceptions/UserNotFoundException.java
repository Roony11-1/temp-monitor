package io.github.roony.modules.auth.core.domain.exceptions;

import io.github.roony.error.core.StandardErrorCategories;
import io.github.roony.error.core.exceptions.AppException;

public class UserNotFoundException extends AppException 
{
    public UserNotFoundException(String email) 
    {
        super("AUTH-003", "Usuario no encontrado: " + email, StandardErrorCategories.NOT_FOUND, "Usuario no encontrado: " + email);
    }
}
