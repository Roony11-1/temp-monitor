package io.github.roony11_1.modules.users.infrastructure.repository;

import java.util.Optional;

import io.github.roony11_1.modules.users.core.domain.model.Usuario;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PanacheUsuarioRepository implements IPanacheUsuarioRepository
{
    @Override
    public Optional<Usuario> findByEmailOptional(String email)
    {
        return find("email", email).firstResultOptional();
    }

    @Override
    public long countByEmail(String email)
    {
        return count("email", email);
    }
}