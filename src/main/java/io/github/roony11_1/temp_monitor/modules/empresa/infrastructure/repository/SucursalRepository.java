package io.github.roony11_1.temp_monitor.modules.empresa.infrastructure.repository;

import io.github.roony11_1.temp_monitor.modules.empresa.core.domain.model.Sucursal;
import io.github.roony11_1.temp_monitor.modules.empresa.core.domain.repository.ISucursalRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
@RequiredArgsConstructor
public class SucursalRepository implements ISucursalRepository
{
    private final IPanacheSucursalRepository panacheRepository;

    @Override
    public Optional<Sucursal> findByIdOptional(Long id)
    {
        return panacheRepository.findByIdOptional(id);
    }

    @Override
    public List<Sucursal> findByEmpresaId(Long empresaId)
    {
        return panacheRepository.findByEmpresaId(empresaId);
    }

    @Override
    public List<Sucursal> listAll()
    {
        return panacheRepository.listAll();
    }

    @Override
    public void save(Sucursal sucursal)
    {
        panacheRepository.getEntityManager().merge(sucursal);
    }

    @Override
    public void delete(Sucursal sucursal)
    {
        panacheRepository.delete(sucursal);
    }
}
