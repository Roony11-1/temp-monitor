package io.github.roony11_1.temp_monitor.modules.empresa.core.application;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.List;

import io.github.roony11_1.temp_monitor.modules.empresa.core.domain.exceptions.EmpresaNotFoundException;
import io.github.roony11_1.temp_monitor.modules.empresa.core.domain.exceptions.NombreEmpresaAlreadyExistsException;
import io.github.roony11_1.temp_monitor.modules.empresa.core.domain.model.Empresa;
import io.github.roony11_1.temp_monitor.modules.empresa.core.domain.repository.IEmpresaRepository;

@ApplicationScoped
@RequiredArgsConstructor
public class EmpresaService 
{
    private final IEmpresaRepository empresaRepository;

    public List<Empresa> listarTodas() 
    {
        return empresaRepository.listAll();
    }

    public Empresa buscarPorId(Long id) 
    {
        return empresaRepository.findByIdOptional(id)
                .orElseThrow(() -> new EmpresaNotFoundException("ID " + id));
    }

    @Transactional
    public Empresa crear(String nombre, String direccion, String telefono, String email) 
    {
        if (empresaRepository.existsByNombre(nombre))
            throw new NombreEmpresaAlreadyExistsException(nombre);

        Empresa empresa = Empresa.builder()
                .nombre(nombre)
                .direccion(direccion)
                .telefono(telefono)
                .email(email)
                .activo(true)
                .build();

        empresaRepository.save(empresa);
        return empresa;
    }

    @Transactional
    public Empresa actualizar(Long id, String nombre, String direccion, String telefono, String email) 
    {
        Empresa empresa = buscarPorId(id);

        empresa.setNombre(nombre);
        empresa.setDireccion(direccion);
        empresa.setTelefono(telefono);
        empresa.setEmail(email);
        empresa.setUpdatedAt(Instant.now());
        empresaRepository.save(empresa);
        return empresa;
    }

    @Transactional
    public void activar(Long id) 
    {
        Empresa empresa = buscarPorId(id);
        empresa.setActivo(true);
        empresa.setUpdatedAt(Instant.now());
        empresaRepository.save(empresa);
    }

    @Transactional
    public void desactivar(Long id) 
    {
        Empresa empresa = buscarPorId(id);
        empresa.setActivo(false);
        empresa.setUpdatedAt(Instant.now());
        empresaRepository.save(empresa);
    }

    @Transactional
    public void eliminar(Long id) 
    {
        var empresa = buscarPorId(id);
        empresaRepository.delete(empresa);
    }
}
