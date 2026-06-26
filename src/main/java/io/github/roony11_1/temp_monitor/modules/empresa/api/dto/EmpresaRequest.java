package io.github.roony11_1.temp_monitor.modules.empresa.api.dto;

import lombok.Data;

@Data
public class EmpresaRequest 
{
    private String nombre;
    private String direccion;
    private String telefono;
    private String email;
}
