package io.github.roony11_1.kernel.security;

import java.util.Set;

public interface TokenUser 
{
    Long getId();
    String getEmail();
    Set<Rol> getRoles(); 
    Long getEmpresaId();
}