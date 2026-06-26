package io.github.roony11_1.temp_monitor.modules.empresa.api.rest;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import io.github.roony11_1.temp_monitor.modules.empresa.api.dto.SucursalRequest;
import io.github.roony11_1.temp_monitor.modules.empresa.api.dto.SucursalResponse;
import io.github.roony11_1.temp_monitor.modules.empresa.core.application.SucursalService;
import io.github.roony11_1.temp_monitor.modules.empresa.core.domain.model.Sucursal;

@Path("/api/sucursales")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class SucursalResource 
{
    private final SucursalService sucursalService;

    @GET
    public List<SucursalResponse> listarTodas() 
    {
        return sucursalService.listarTodas().stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    @GET
    @Path("/empresa/{empresaId}")
    public List<SucursalResponse> listarPorEmpresa(@PathParam("empresaId") Long empresaId) 
    {
        return sucursalService.listarPorEmpresa(empresaId).stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    public SucursalResponse buscarPorId(@PathParam("id") Long id) 
    {
        Sucursal sucursal = sucursalService.buscarPorId(id);
        return toResponse(sucursal);
    }

    @POST
    public Response crear(SucursalRequest request) 
    {
        Sucursal sucursal = sucursalService.crear(
            request.getNombre(),
            request.getDireccion(),
            request.getTelefono(),
            request.getEmpresaId()
        );

        SucursalResponse response = toResponse(sucursal);
        return Response.created(URI.create("/api/sucursales/" + sucursal.id))
            .entity(response)
            .build();
    }

    @PUT
    @Path("/{id}")
    public SucursalResponse actualizar(@PathParam("id") Long id, SucursalRequest request) 
    {
        Sucursal sucursal = sucursalService.actualizar(
            id,
            request.getNombre(),
            request.getDireccion(),
            request.getTelefono(),
            request.getEmpresaId()
        );
        return toResponse(sucursal);
    }

    @POST
    @Path("/{id}/activar")
    public Response activar(@PathParam("id") Long id) 
    {
        sucursalService.activar(id);
        return Response.ok().build();
    }

    @POST
    @Path("/{id}/desactivar")
    public Response desactivar(@PathParam("id") Long id) 
    {
        sucursalService.desactivar(id);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") Long id) 
    {
        sucursalService.eliminar(id);
        return Response.noContent().build();
    }

    private SucursalResponse toResponse(Sucursal sucursal) 
    {
        SucursalResponse response = new SucursalResponse();
        response.setId(sucursal.id);
        response.setNombre(sucursal.getNombre());
        response.setDireccion(sucursal.getDireccion());
        response.setTelefono(sucursal.getTelefono());
        response.setEmpresaId(sucursal.getEmpresa().getId());
        response.setActivo(sucursal.isActivo());
        response.setCreatedAt(sucursal.getCreatedAt());
        response.setUpdatedAt(sucursal.getUpdatedAt());

        return response;
    }
}
