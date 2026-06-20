package io.github.roony11_1.temp_monitor.kernel.security;

import java.util.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class JwtKeyProvider 
{
    @ConfigProperty(name = "smallrye.jwt.sign.key")
    String rawKey;

    public SecretKey getHmacKey() 
    {
        byte[] decoded = Base64.getDecoder().decode(rawKey);
        return new SecretKeySpec(decoded, "HmacSHA256");
    }
}