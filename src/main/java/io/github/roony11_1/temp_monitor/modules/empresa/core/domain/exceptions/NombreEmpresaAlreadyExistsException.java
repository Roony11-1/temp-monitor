package io.github.roony11_1.temp_monitor.modules.empresa.core.domain.exceptions;

import io.github.roony11_1.error.core.StandardErrorCategories;
import io.github.roony11_1.error.core.exceptions.AppException;

public class NombreEmpresaAlreadyExistsException extends AppException
{
    public NombreEmpresaAlreadyExistsException(String nombre) 
    {
        super("EMP-002", "Ya existe una empresa con el nombre: " + nombre, StandardErrorCategories.ALREADY_EXISTS, "Ya existe una empresa con el nombre: " + nombre);
    }
}
