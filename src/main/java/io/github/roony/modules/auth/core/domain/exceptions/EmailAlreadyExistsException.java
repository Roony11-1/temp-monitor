package io.github.roony.modules.auth.core.domain.exceptions;

import io.github.roony.error.core.StandardErrorCategories;
import io.github.roony.error.core.exceptions.AppException;

public class EmailAlreadyExistsException extends AppException
{
    public EmailAlreadyExistsException(String email) 
    {
        super("USER-001", "El email ya está registrado: " + email, StandardErrorCategories.ALREADY_EXISTS, "El email ya está registrado: " + email);
    }
}
