package io.github.roony11_1.temp_monitor.modules.empresa.core.domain.repository;

import io.github.roony11_1.temp_monitor.modules.empresa.core.domain.model.Empresa;
import java.util.List;
import java.util.Optional;

public interface IEmpresaRepository 
{
    Optional<Empresa> findByIdOptional(Long id);
    Optional<Empresa> findByNombre(String nombre);
    boolean existsByNombre(String nombre);
    List<Empresa> listAll();
    void save(Empresa empresa);
    void delete(Empresa empresa);
}
