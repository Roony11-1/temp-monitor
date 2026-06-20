package io.github.roony11_1.temp_monitor.kernel.security;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import io.github.roony11_1.error.core.exceptions.InternalErrorException;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@RequiredArgsConstructor
@Slf4j
public class JwtGenerator 
{
    private final JwtKeyProvider keyProvider;

    @Inject
    @org.eclipse.microprofile.config.inject.ConfigProperty(name = "jwt.expiration-hours", defaultValue = "24")
    Long expirationHours;

    public String generate(TokenUser user) 
    {
        Objects.requireNonNull(user.getId(), "El usuario no tiene ID");
        Objects.requireNonNull(user.getEmail(), "El usuario no tiene email");

        Set<String> roles = user.getRoles()
                .stream()
                .map(Rol::name)
                .collect(Collectors.toSet());

        if (roles.isEmpty()) 
        {
            throw new InvalidTokenUserException("El usuario no tiene roles asignados");
        }

        try 
        {
            SecretKey key = keyProvider.getHmacKey();

            var builder = Jwt.issuer("temp-monitor")
                    .subject(user.getId().toString())
                    .claim("roles", roles)
                    .expiresAt(Instant.now().plus(Duration.ofHours(expirationHours)));

            if (user.getEmpresaId() != null) 
            {
                builder.claim("empresaId", user.getEmpresaId());
            }

            return builder.sign(key);

        } 
        catch (Exception e) 
        {
            log.error("Error al generar JWT para userId={}", user.getId(), e);
            throw new InternalErrorException("generar el token JWT", e);
        }
    }
}