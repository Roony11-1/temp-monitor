package io.github.roony11_1.modules.users.infrastructure.repository;

import io.github.roony11_1.modules.users.core.domain.model.Usuario;
import io.github.roony11_1.modules.users.core.domain.repository.IUsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
@RequiredArgsConstructor
public class UsuarioRepositoryAdapter implements IUsuarioRepository
{
    private final IPanacheUsuarioRepository panacheRepository;

    @Override
    public Optional<Usuario> findByIdOptional(Long id)
    {
        return panacheRepository.findByIdOptional(id);
    }

    @Override
    public Optional<Usuario> findByEmail(String email)
    {
        return panacheRepository.findByEmailOptional(email);
    }

    @Override
    public boolean existsByEmail(String email)
    {
        return panacheRepository.countByEmail(email) > 0;
    }

    @Override
    public boolean existsById(Long id)
    {
        return panacheRepository.count("id", id) > 0;
    }

    @Override
    public List<Usuario> listAll()
    {
        return panacheRepository.listAll();
    }

    @Override
    public void save(Usuario usuario)
    {
        panacheRepository.getEntityManager().merge(usuario);
    }

    @Override
    public void delete(Usuario usuario)
    {
        panacheRepository.delete(usuario);
    }
}