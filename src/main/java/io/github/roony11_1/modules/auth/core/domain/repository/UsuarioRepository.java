package io.github.roony11_1.modules.auth.core.domain.repository;

import java.util.Optional;

import io.github.roony11_1.modules.auth.core.domain.model.Usuario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UsuarioRepository implements PanacheRepository<Usuario> 
{
    
    public Optional<Usuario> findByEmail(String email) 
    {
        return find("email", email).firstResultOptional();
    }
    
    public boolean existsByEmail(String email) 
    {
        return count("email", email) > 0;
    }

    public boolean existsById(Long id) 
    {
        return count("id", id) > 0;
    }
}
