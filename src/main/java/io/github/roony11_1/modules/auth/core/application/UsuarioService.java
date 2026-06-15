package io.github.roony11_1.modules.auth.core.application;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.Set;

import io.github.roony11_1.kernel.security.PasswordHasher;
import io.github.roony11_1.kernel.security.Rol;
import io.github.roony11_1.modules.auth.core.domain.exceptions.EmailAlreadyExistsException;
import io.github.roony11_1.modules.auth.core.domain.exceptions.UserNotFoundException;
import io.github.roony11_1.modules.auth.core.domain.model.Usuario;
import io.github.roony11_1.modules.auth.core.domain.repository.UsuarioRepository;

@ApplicationScoped
@RequiredArgsConstructor
public class UsuarioService 
{
    private final UsuarioRepository usuarioRepository;
    private final PasswordHasher passwordHasher;

    public List<Usuario> listarTodos() 
    {
        return usuarioRepository.listAll();
    }

    public Usuario buscarPorId(Long id) 
    {
        return usuarioRepository.findByIdOptional(id)
                .orElseThrow(() -> new UserNotFoundException("ID " + id));
    }

    @Transactional
    public Usuario crear(String email, String password, String nombre, Long empresaId) 
    {
        if (usuarioRepository.existsByEmail(email))
            throw new EmailAlreadyExistsException(email);

        Usuario usuario = Usuario.builder()
                .email(email)
                .passwordHash(passwordHasher.hash(password))
                .nombre(nombre)
                .roles(Set.of(Rol.USUARIO))
                .empresaId(empresaId)
                .activo(true)
                .build();

        usuarioRepository.persist(usuario);
        return usuario;
    }

    @Transactional
    public Usuario actualizar(Long id, String nombre, String telefono, Long empresaId) 
    {
        Usuario usuario = buscarPorId(id);

        usuario.setNombre(nombre);
        usuario.setTelefono(telefono);
        usuario.setEmpresaId(empresaId);
        usuario.setUpdatedAt(Instant.now());
        usuarioRepository.persist(usuario);
        return usuario;
    }

    @Transactional
    public void cambiarPassword(Long id, String nuevaPassword) 
    {
        Usuario usuario = buscarPorId(id);
        usuario.setPasswordHash(passwordHasher.hash(nuevaPassword));
        usuario.setUpdatedAt(Instant.now());
        usuarioRepository.persist(usuario);
    }

    @Transactional
    public void activar(Long id) 
    {
        Usuario usuario = buscarPorId(id);
        usuario.setActivo(true);
        usuario.setUpdatedAt(Instant.now());
        usuarioRepository.persist(usuario);
    }

    @Transactional
    public void desactivar(Long id) 
    {
        Usuario usuario = buscarPorId(id);
        usuario.setActivo(false);
        usuario.setUpdatedAt(Instant.now());
        usuarioRepository.persist(usuario);
    }

    @Transactional
    public void eliminar(Long id) 
    {
        if (!usuarioRepository.existsById(id)) 
            throw new UserNotFoundException("ID " + id);

        usuarioRepository.deleteById(id);
    }
}