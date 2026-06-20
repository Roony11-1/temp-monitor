package io.github.roony11_1.temp_monitor.modules.users.core.domain.repository;

import io.github.roony11_1.temp_monitor.modules.users.core.domain.model.Usuario;
import java.util.List;
import java.util.Optional;

public interface IUsuarioRepository 
{
    Optional<Usuario> findByIdOptional(Long id);
    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsById(Long id);
    List<Usuario> listAll();
    void save(Usuario usuario);        // persiste o actualiza
    void delete(Usuario usuario);
}