package io.github.roony11_1.modules.auth.api.rest;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import io.github.roony11_1.modules.auth.api.dto.UsuarioRequest;
import io.github.roony11_1.modules.auth.api.dto.UsuarioResponse;
import io.github.roony11_1.modules.auth.core.application.UsuarioService;
import io.github.roony11_1.modules.auth.core.domain.model.Usuario;

@Path("/api/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class UsuarioResource 
{
    private final UsuarioService usuarioService;

    @GET
    public List<UsuarioResponse> listarTodos() 
    {
        return usuarioService.listarTodos().stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    public UsuarioResponse buscarPorId(@PathParam("id") Long id) 
    {
        Usuario usuario = usuarioService.buscarPorId(id);
        return toResponse(usuario);
    }

    @POST
    public Response crear(UsuarioRequest request) 
    {
        Usuario usuario = usuarioService.crear(
            request.getEmail(),
            request.getPassword(),
            request.getNombre(),
            request.getEmpresaId()
        );
        
        UsuarioResponse response = toResponse(usuario);
        return Response.created(URI.create("/api/usuarios/" + usuario.id))
            .entity(response)
            .build();
    }

    @PUT
    @Path("/{id}")
    public UsuarioResponse actualizar(@PathParam("id") Long id, UsuarioRequest request) 
    {
        Usuario usuario = usuarioService.actualizar(
            id,
            request.getNombre(),
            request.getTelefono(),
            request.getEmpresaId()
        );
        return toResponse(usuario);
    }

    @POST
    @Path("/{id}/password")
    public Response cambiarPassword(@PathParam("id") Long id, String nuevaPassword) 
    {
        usuarioService.cambiarPassword(id, nuevaPassword);
        return Response.ok().build();
    }

    @POST
    @Path("/{id}/activar")
    public Response activar(@PathParam("id") Long id) 
    {
        usuarioService.activar(id);
        return Response.ok().build();
    }

    @POST
    @Path("/{id}/desactivar")
    public Response desactivar(@PathParam("id") Long id) 
    {
        usuarioService.desactivar(id);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") Long id) 
    {
        usuarioService.eliminar(id);
        return Response.noContent().build();
    }

    private UsuarioResponse toResponse(Usuario usuario) 
    {
        UsuarioResponse response = new UsuarioResponse();
        response.setId(usuario.id);
        response.setEmail(usuario.getEmail());
        response.setNombre(usuario.getNombre());
        response.setTelefono(usuario.getTelefono());
        response.setRoles(usuario.getRoles());
        response.setEmpresaId(usuario.getEmpresaId());
        response.setActivo(usuario.isActivo());
        response.setCreatedAt(usuario.getCreatedAt());
        response.setLastLogin(usuario.getLastLogin());
        
        return response;
    }
}