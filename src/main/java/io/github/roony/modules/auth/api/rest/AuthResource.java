package io.github.roony.modules.auth.api.rest;

import io.github.roony.modules.auth.api.dto.LoginRequest;
import io.github.roony.modules.auth.api.dto.LoginResponse;
import io.github.roony.modules.auth.core.application.AuthService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
@Slf4j
public class AuthResource 
{
    private final AuthService authService;

    @POST
    @Path("/login")
    public Response login(LoginRequest request) 
    {
        log.info("Intento de login para email: {}", request.getEmail());

        String token = authService.login(request.getEmail(), request.getPassword());
        
        LoginResponse response = new LoginResponse();
        response.setToken(token);
        response.setEmail(request.getEmail());
        
        return Response.ok(response).build();
    }
}