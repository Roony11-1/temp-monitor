package io.github.roony.modules.auth.infrastructure.config;

import io.github.roony.error.quarkus.HttpStatusRegistry;
import io.github.roony.kernel.security.SecurityErrorCategories;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ErrorHttpConfig 
{
    @PostConstruct
    void registerCustomMappings() 
    {
        HttpStatusRegistry.register(SecurityErrorCategories.JWT_GENERATION_FAILED, 503);
        HttpStatusRegistry.register(SecurityErrorCategories.INVALID_TOKEN_USER, 400);
    }
}