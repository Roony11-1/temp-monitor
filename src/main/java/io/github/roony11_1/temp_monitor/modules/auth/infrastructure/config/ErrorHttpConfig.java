package io.github.roony11_1.temp_monitor.modules.auth.infrastructure.config;

import io.github.roony11_1.error.rest.HttpStatusRegistry;
import io.github.roony11_1.temp_monitor.kernel.security.error.SecurityErrorCategories;
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