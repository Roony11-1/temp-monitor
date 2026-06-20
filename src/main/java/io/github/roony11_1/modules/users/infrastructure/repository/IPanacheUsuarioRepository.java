package io.github.roony11_1.modules.users.infrastructure.repository;

import io.github.roony11_1.modules.users.core.domain.model.Usuario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public interface IPanacheUsuarioRepository extends PanacheRepository<Usuario> 
{
    Optional<Usuario> findByEmailOptional(String email);
    long countByEmail(String email);
}