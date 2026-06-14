package io.github.roony.kernel.shared.exception;

import org.slf4j.MDC;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;

@Provider
@Slf4j
public class GenericExceptionMapper extends ExceptionMapperSupport implements ExceptionMapper<Exception> 
{

    @Override
    public Response toResponse(Exception exception) 
    {
        log.error("Error inesperado", exception);

        ErrorResponse body = new ErrorResponse(ErrorCode.ERROR_INTERNO.getMensajePorDefecto(), ErrorCode.ERROR_INTERNO.name());
        body.setPath(uriInfo != null ? uriInfo.getPath() : "desconocido");
        body.setTraceId(MDC.get("traceId"));
        
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(body)
                .build();
    }
}
