package io.github.roony11_1.temp_monitor.modules.empresa.infrastructure.repository;

import java.util.Optional;

import io.github.roony11_1.temp_monitor.modules.empresa.core.domain.model.Empresa;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

public interface IPanacheEmpresaRepository extends PanacheRepository<Empresa>
{
    Optional<Empresa> findByNombreOptional(String nombre);

    long countByNombre(String nombre);
}
