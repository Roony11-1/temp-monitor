package io.github.roony11_1.temp_monitor.modules.users.api.dto;

import java.util.Set;

import io.github.roony11_1.temp_monitor.kernel.security.model.Rol;
import lombok.Data;

@Data
public class UsuarioRequest 
{
    private String email;
    private String password;
    private String nombre;
    private String telefono;
    private Long empresaId;
    private Long sucursalId;
    private Set<Rol> roles;
}
