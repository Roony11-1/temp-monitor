package io.github.roony11_1.temp_monitor.modules.empresa.api.rest;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import io.github.roony11_1.temp_monitor.modules.empresa.api.dto.EmpresaRequest;
import io.github.roony11_1.temp_monitor.modules.empresa.api.dto.EmpresaResponse;
import io.github.roony11_1.temp_monitor.modules.empresa.core.application.EmpresaService;
import io.github.roony11_1.temp_monitor.modules.empresa.core.domain.model.Empresa;

@Path("/api/empresas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class EmpresaResource 
{
    private final EmpresaService empresaService;

    @GET
    public List<EmpresaResponse> listarTodas() 
    {
        return empresaService.listarTodas().stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    public EmpresaResponse buscarPorId(@PathParam("id") Long id) 
    {
        Empresa empresa = empresaService.buscarPorId(id);
        return toResponse(empresa);
    }

    @POST
    public Response crear(EmpresaRequest request) 
    {
        Empresa empresa = empresaService.crear(
            request.getNombre(),
            request.getDireccion(),
            request.getTelefono(),
            request.getEmail()
        );

        EmpresaResponse response = toResponse(empresa);
        return Response.created(URI.create("/api/empresas/" + empresa.id))
            .entity(response)
            .build();
    }

    @PUT
    @Path("/{id}")
    public EmpresaResponse actualizar(@PathParam("id") Long id, EmpresaRequest request) 
    {
        Empresa empresa = empresaService.actualizar(
            id,
            request.getNombre(),
            request.getDireccion(),
            request.getTelefono(),
            request.getEmail()
        );
        return toResponse(empresa);
    }

    @POST
    @Path("/{id}/activar")
    public Response activar(@PathParam("id") Long id) 
    {
        empresaService.activar(id);
        return Response.ok().build();
    }

    @POST
    @Path("/{id}/desactivar")
    public Response desactivar(@PathParam("id") Long id) 
    {
        empresaService.desactivar(id);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") Long id) 
    {
        empresaService.eliminar(id);
        return Response.noContent().build();
    }

    private EmpresaResponse toResponse(Empresa empresa) 
    {
        EmpresaResponse response = new EmpresaResponse();
        response.setId(empresa.id);
        response.setNombre(empresa.getNombre());
        response.setDireccion(empresa.getDireccion());
        response.setTelefono(empresa.getTelefono());
        response.setEmail(empresa.getEmail());
        response.setActivo(empresa.isActivo());
        response.setCreatedAt(empresa.getCreatedAt());
        response.setUpdatedAt(empresa.getUpdatedAt());

        return response;
    }
}
