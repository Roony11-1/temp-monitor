package io.github.roony11_1.temp_monitor.modules.users.core.application;

import java.time.Instant;

import io.github.roony11_1.temp_monitor.kernel.security.IUserCredentialsService;
import io.github.roony11_1.temp_monitor.kernel.security.PasswordHasher;
import io.github.roony11_1.temp_monitor.kernel.security.TokenUser;
import io.github.roony11_1.temp_monitor.modules.users.core.domain.exceptions.InvalidCredentialsException;
import io.github.roony11_1.temp_monitor.modules.users.core.domain.exceptions.UserDisabledException;
import io.github.roony11_1.temp_monitor.modules.users.core.domain.exceptions.UserNotFoundException;
import io.github.roony11_1.temp_monitor.modules.users.core.domain.model.Usuario;
import io.github.roony11_1.temp_monitor.modules.users.core.domain.repository.IUsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class DefaultUserCredentialsService implements IUserCredentialsService 
{
    private final IUsuarioRepository usuarioRepository;
    private final PasswordHasher passwordHasher;

    @Override
    @Transactional
    public TokenUser authenticate(String email, String rawPassword) 
    {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        if (!usuario.isActivo()) 
        {
            throw new UserDisabledException();
        }

        if (!passwordHasher.verify(rawPassword, usuario.getPasswordHash())) 
        {
            throw new InvalidCredentialsException();
        }

        usuario.setLastLogin(Instant.now());
        usuarioRepository.save(usuario);

        return usuario;
    }
}
