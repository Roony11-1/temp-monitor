package io.github.roony11_1.temp_monitor.modules.empresa.infrastructure.repository;

import io.github.roony11_1.temp_monitor.modules.empresa.core.domain.model.Empresa;
import io.github.roony11_1.temp_monitor.modules.empresa.core.domain.repository.IEmpresaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
@RequiredArgsConstructor
public class EmpresaRepository implements IEmpresaRepository
{
    private final IPanacheEmpresaRepository panacheRepository;

    @Override
    public Optional<Empresa> findByIdOptional(Long id)
    {
        return panacheRepository.findByIdOptional(id);
    }

    @Override
    public Optional<Empresa> findByNombre(String nombre)
    {
        return panacheRepository.findByNombreOptional(nombre);
    }

    @Override
    public boolean existsByNombre(String nombre)
    {
        return panacheRepository.countByNombre(nombre) > 0;
    }

    @Override
    public List<Empresa> listAll()
    {
        return panacheRepository.listAll();
    }

    @Override
    public void save(Empresa empresa)
    {
        panacheRepository.getEntityManager().merge(empresa);
    }

    @Override
    public void delete(Empresa empresa)
    {
        panacheRepository.delete(empresa);
    }
}
