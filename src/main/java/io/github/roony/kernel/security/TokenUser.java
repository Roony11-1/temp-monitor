package io.github.roony.kernel.security;

import java.util.Set;

public interface TokenUser 
{
    Long getId();
    String getEmail();
    Set<Rol> getRoles(); 
    Long getEmpresaId();
}