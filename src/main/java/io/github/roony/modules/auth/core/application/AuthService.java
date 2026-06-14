package io.github.roony.modules.auth.core.application;

import java.time.Instant;

import io.github.roony.kernel.security.JwtGenerator;
import io.github.roony.kernel.security.PasswordHasher;
import io.github.roony.kernel.shared.exception.AppException;
import io.github.roony.kernel.shared.exception.ErrorCode;
import io.github.roony.modules.auth.core.domain.repository.UsuarioRepository;
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
                return new AppException(ErrorCode.CREDENCIALES_INVALIDAS);
            });

        if (!usuario.isActivo()) 
        {
            log.warn("Usuario desactivado: {}", email);
            throw new AppException(ErrorCode.USUARIO_DESACTIVADO);
        }

        if (!passwordHasher.verify(password, usuario.getPasswordHash())) 
        {
            log.warn("Password incorrecto para email: {}", email);
            throw new AppException(ErrorCode.CREDENCIALES_INVALIDAS);
        }

        usuario.setLastLogin(Instant.now());
        usuarioRepository.persist(usuario);

        String token = jwtGenerator.generate(usuario);
        log.info("Login exitoso para email: {}", email);

        return token;
    }
}