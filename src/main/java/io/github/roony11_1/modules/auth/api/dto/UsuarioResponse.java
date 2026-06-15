package io.github.roony11_1.modules.auth.api.dto;

import java.time.Instant;
import java.util.Set;

import io.github.roony11_1.kernel.security.Rol;
import lombok.Data;

@Data
public class UsuarioResponse 
{
    private Long id;
    private String email;
    private String nombre;
    private String telefono;
    private Set<Rol> roles;
    private Long empresaId;
    private boolean activo;
    private Instant createdAt;
    private Instant lastLogin;
}
