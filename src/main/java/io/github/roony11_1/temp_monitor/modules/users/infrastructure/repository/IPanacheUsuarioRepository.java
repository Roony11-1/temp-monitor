package io.github.roony11_1.temp_monitor.modules.users.infrastructure.repository;

import java.util.Optional;

import io.github.roony11_1.temp_monitor.modules.users.core.domain.model.Usuario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

public interface IPanacheUsuarioRepository extends PanacheRepository<Usuario>
{
    long countByEmail(String email);

    Optional<Usuario> findByEmailOptional(String email);
}