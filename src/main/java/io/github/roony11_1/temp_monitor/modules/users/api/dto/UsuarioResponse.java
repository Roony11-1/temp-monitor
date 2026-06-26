package io.github.roony11_1.temp_monitor.modules.users.api.dto;

import java.time.Instant;
import java.util.Set;

import io.github.roony11_1.temp_monitor.kernel.security.model.Rol;
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
    private Long sucursalId;
    private boolean activo;
    private Instant createdAt;
    private Instant lastLogin;
}
