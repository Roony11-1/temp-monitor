package io.github.roony11_1.temp_monitor.modules.empresa.core.domain.repository;

import io.github.roony11_1.temp_monitor.modules.empresa.core.domain.model.Sucursal;
import java.util.List;
import java.util.Optional;

public interface ISucursalRepository 
{
    Optional<Sucursal> findByIdOptional(Long id);
    List<Sucursal> findByEmpresaId(Long empresaId);
    List<Sucursal> listAll();
    void save(Sucursal sucursal);
    void delete(Sucursal sucursal);
}
