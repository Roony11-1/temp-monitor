package io.github.roony.kernel.shared.exception;

import org.slf4j.MDC;

import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;

public abstract class ExceptionMapperSupport
{
    @Context
    UriInfo uriInfo;

    protected ErrorResponse buildErrorResponse(AppException ex) 
    {
        ErrorResponse response = new ErrorResponse(ex.getMessageForResponse(), ex.getErrorCode().name());
        response.setPath(uriInfo != null ? uriInfo.getPath() : "desconocido");
        response.setTraceId(MDC.get("traceId"));
        return response;
    }
}
