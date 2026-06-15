package io.github.roony11_1.kernel.security;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.github.roony.error.core.exceptions.InternalErrorException;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
public class JwtGenerator 
{
    @ConfigProperty(name = "jwt.expiration-hours", defaultValue = "24")
    Long expirationHours;

    public String generate(TokenUser user) 
    {
        Objects.requireNonNull(user.getId(), "El usuario no tiene ID");
        Objects.requireNonNull(user.getEmail(), "El usuario no tiene email");

        Set<String> rolNames = user.getRoles().stream()
                .map(Rol::name)
                .collect(Collectors.toSet());

        if (rolNames.isEmpty())
            throw new InvalidTokenUserException("El usuario no tiene roles asignados");

        try 
        {
            var jwt = Jwt.issuer("temp-monitor")
                    .subject(user.getId().toString())
                    .claim("roles", rolNames)
                    .expiresAt(Instant.now().plus(Duration.ofHours(expirationHours)));

            if (user.getEmpresaId() != null) 
                jwt.claim("empresaId", user.getEmpresaId());

            return jwt.signWithSecret("ezkyO/2l7ExQ5LfJYgGT1dVtF1WGAFMAY6vtvomGmxY=");
        } 
        catch (Exception e) 
        {
            log.error("Error al generar el token JWT", e);
            throw new InternalErrorException("generar el token JWT", e);
        }
    }
}