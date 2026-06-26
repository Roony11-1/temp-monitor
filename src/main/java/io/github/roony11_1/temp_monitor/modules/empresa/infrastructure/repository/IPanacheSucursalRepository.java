package io.github.roony11_1.temp_monitor.modules.empresa.infrastructure.repository;

import java.util.List;

import io.github.roony11_1.temp_monitor.modules.empresa.core.domain.model.Sucursal;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

public interface IPanacheSucursalRepository extends PanacheRepository<Sucursal>
{
    List<Sucursal> findByEmpresaId(Long empresaId);
}
