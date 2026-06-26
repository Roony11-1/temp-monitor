package io.github.roony11_1.temp_monitor.modules.empresa.infrastructure.repository;

import java.util.Optional;

import io.github.roony11_1.temp_monitor.modules.empresa.core.domain.model.Empresa;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PanacheEmpresaRepository implements IPanacheEmpresaRepository
{
    @Override
    public Optional<Empresa> findByNombreOptional(String nombre)
    {
        return find("nombre", nombre).firstResultOptional();
    }

    @Override
    public long countByNombre(String nombre)
    {
        return count("nombre", nombre);
    }
}
