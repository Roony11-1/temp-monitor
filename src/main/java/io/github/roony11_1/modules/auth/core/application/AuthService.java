package io.github.roony11_1.modules.auth.core.application;

import java.time.Instant;

import io.github.roony.error.core.exceptions.InternalErrorException;
import io.github.roony11_1.kernel.security.JwtGenerator;
import io.github.roony11_1.kernel.security.PasswordHasher;
import io.github.roony11_1.modules.auth.core.domain.exceptions.InvalidCredentialsException;
import io.github.roony11_1.modules.auth.core.domain.exceptions.UserDisabledException;
import io.github.roony11_1.modules.auth.core.domain.exceptions.UserNotFoundException;
import io.github.roony11_1.modules.auth.core.domain.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@RequiredArgsConstructor
@Slf4j
public class AuthService 
{
    private final UsuarioRepository usuarioRepository;
    private final PasswordHasher passwordHasher;
    private final JwtGenerator jwtGenerator;

    @Transactional
    public String login(String email, String password)
    {
        log.info("Intento de login para email: {}", email);

        var usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> {
                log.warn("Usuario no encontrado: {}", email);
                return new UserNotFoundException(email);
            });

        if (!usuario.isActivo()) 
        {
            log.warn("Usuario desactivado: {}", email);
            throw new UserDisabledException();
        }

        if (!passwordHasher.verify(password, usuario.getPasswordHash())) 
        {
            log.warn("Password incorrecto para email: {}", email);
            throw new InvalidCredentialsException();
        }

        usuario.setLastLogin(Instant.now());
        usuarioRepository.persist(usuario);

        try 
        {
            String token = jwtGenerator.generate(usuario);
            log.info("Login exitoso para email: {}", email);
            return token;
        } 
        catch (Exception e) 
        {
            log.error("Error al generar el token JWT", e);
            throw new InternalErrorException("generar el token JWT", e);
        }
    }
}