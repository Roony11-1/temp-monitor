package io.github.roony11_1.temp_monitor.modules.users.core.application;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.Set;

import io.github.roony11_1.temp_monitor.kernel.security.crypto.PasswordHasher;
import io.github.roony11_1.temp_monitor.kernel.security.model.Rol;
import io.github.roony11_1.temp_monitor.modules.users.core.domain.exceptions.EmailAlreadyExistsException;
import io.github.roony11_1.temp_monitor.modules.users.core.domain.exceptions.UserNotFoundException;
import io.github.roony11_1.temp_monitor.modules.users.core.domain.model.Usuario;
import io.github.roony11_1.temp_monitor.modules.users.core.domain.repository.IUsuarioRepository;

@ApplicationScoped
@RequiredArgsConstructor
public class UsuarioService 
{
    private final IUsuarioRepository usuarioRepository;
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
    public Usuario crear(String email, String password, String nombre, Long empresaId, Long sucursalId, Set<Rol> roles) 
    {
        if (usuarioRepository.existsByEmail(email))
            throw new EmailAlreadyExistsException(email);

        Usuario usuario = Usuario.builder()
                .email(email)
                .passwordHash(passwordHasher.hash(password))
                .nombre(nombre)
                .roles(roles)
                .empresaId(empresaId)
                .sucursalId(sucursalId)
                .activo(true)
                .build();

        usuarioRepository.save(usuario);
        return usuario;
    }

    @Transactional
    public Usuario actualizar(Long id, String nombre, String telefono, Long empresaId, Long sucursalId, Set<Rol> roles) 
    {
        Usuario usuario = buscarPorId(id);

        usuario.setNombre(nombre);
        usuario.setTelefono(telefono);
        usuario.setEmpresaId(empresaId);
        usuario.setSucursalId(sucursalId);
        if (roles != null && !roles.isEmpty()) 
        {
            usuario.setRoles(roles);
        }
        usuario.setUpdatedAt(Instant.now());
        usuarioRepository.save(usuario);
        return usuario;
    }

    @Transactional
    public void cambiarPassword(Long id, String nuevaPassword) 
    {
        Usuario usuario = buscarPorId(id);
        usuario.setPasswordHash(passwordHasher.hash(nuevaPassword));
        usuario.setUpdatedAt(Instant.now());
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void activar(Long id) 
    {
        Usuario usuario = buscarPorId(id);
        usuario.setActivo(true);
        usuario.setUpdatedAt(Instant.now());
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void desactivar(Long id) 
    {
        Usuario usuario = buscarPorId(id);
        usuario.setActivo(false);
        usuario.setUpdatedAt(Instant.now());
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void eliminar(Long id) 
    {
        var usuario = buscarPorId(id);

        usuarioRepository.delete(usuario);
    }
}
