package io.github.roony.modules.auth.core.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.github.roony.error.core.exceptions.InternalErrorException;
import io.github.roony.kernel.security.JwtGenerator;
import io.github.roony.kernel.security.PasswordHasher;
import io.github.roony.kernel.security.Rol;
import io.github.roony.modules.auth.core.application.AuthService;
import io.github.roony.modules.auth.core.domain.exceptions.InvalidCredentialsException;
import io.github.roony.modules.auth.core.domain.exceptions.UserDisabledException;
import io.github.roony.modules.auth.core.domain.exceptions.UserNotFoundException;
import io.github.roony.modules.auth.core.domain.model.Usuario;
import io.github.roony.modules.auth.core.domain.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest 
{
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private PasswordHasher passwordHasher;
    @Mock
    private JwtGenerator jwtGenerator;

    @InjectMocks
    private AuthService authService;

    private Usuario usuario;

    @BeforeEach
    void setUp() 
    {
        usuario = Usuario.builder()
            .email("test@example.com")
            .passwordHash("hashedPassword")
            .roles(Set.of(Rol.USUARIO))
            .activo(true)
            .build();

        usuario.id = 1L;
    }

    @Test
    void testLoginExitoso() 
    {
        when(usuarioRepository.findByEmail("test@example.com"))
            .thenReturn(Optional.of(usuario));
        when(passwordHasher.verify("password123", "hashedPassword"))
            .thenReturn(true);
        when(jwtGenerator.generate(usuario))
            .thenReturn("jwt-token-123");

        String token = authService.login("test@example.com", "password123");

        assertNotNull(token);
        assertEquals("jwt-token-123", token);
        verify(usuarioRepository).persist(usuario);
        verify(jwtGenerator).generate(usuario);
    }

    @Test
    void testLoginUsuarioNoEncontrado() 
    {
        when(usuarioRepository.findByEmail("noexiste@example.com"))
            .thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () ->
            authService.login("noexiste@example.com", "cualquiera"));

        verify(passwordHasher, never()).verify(any(), any());
        verify(usuarioRepository, never()).persist(any(Usuario.class));
    }

    @Test
    void testLoginPasswordIncorrecto() 
    {
        when(usuarioRepository.findByEmail("test@example.com"))
            .thenReturn(Optional.of(usuario));
        when(passwordHasher.verify("wrongPassword", "hashedPassword"))
            .thenReturn(false);

        assertThrows(InvalidCredentialsException.class, () ->
            authService.login("test@example.com", "wrongPassword"));

        verify(usuarioRepository, never()).persist(any(Usuario.class));
    }

    @Test
    void testLoginUsuarioDesactivado() 
    {
        usuario.setActivo(false);
        when(usuarioRepository.findByEmail("test@example.com"))
            .thenReturn(Optional.of(usuario));

        assertThrows(UserDisabledException.class, () ->
            authService.login("test@example.com", "password123"));

        verify(passwordHasher, never()).verify(any(), any());
        verify(usuarioRepository, never()).persist(any(Usuario.class));
    }

    @Test
    void testLoginFalloGeneracionToken() 
    {
        when(usuarioRepository.findByEmail("test@example.com"))
            .thenReturn(Optional.of(usuario));
        when(passwordHasher.verify("password123", "hashedPassword"))
            .thenReturn(true);
        when(jwtGenerator.generate(usuario))
            .thenThrow(new RuntimeException("Error interno"));

        assertThrows(InternalErrorException.class, () ->
            authService.login("test@example.com", "password123"));
    }
}