package io.github.roony.kernel.shared.exception;

import jakarta.ws.rs.core.Response;

public enum ErrorCode 
{
    CREDENCIALES_INVALIDAS("Credenciales inválidas", Response.Status.UNAUTHORIZED),
    USUARIO_DESACTIVADO("Usuario desactivado", Response.Status.FORBIDDEN),
    RECURSO_NO_ENCONTRADO("Recurso no encontrado", Response.Status.NOT_FOUND),
    ACCESO_DENEGADO("Acceso denegado", Response.Status.FORBIDDEN),
    CONFLICTO("Conflicto", Response.Status.CONFLICT),
    ERROR_INTERNO("Error interno del servidor", Response.Status.INTERNAL_SERVER_ERROR),
    USUARIO_NO_ENCONTRADO("Usuario no encontrado", Response.Status.NOT_FOUND),
    EMAIL_DUPLICADO("El email ya está registrado", Response.Status.CONFLICT);

    private final String mensajePorDefecto;
    private final Response.Status status;

    ErrorCode(String mensajePorDefecto, Response.Status status) 
    {
        this.mensajePorDefecto = mensajePorDefecto;
        this.status = status;
    }

    public String getMensajePorDefecto() { return mensajePorDefecto; }
    public Response.Status getStatus() { return status; }
    public int getStatusCode() { return status.getStatusCode(); }
}
