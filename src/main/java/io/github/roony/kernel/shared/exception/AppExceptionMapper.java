package io.github.roony.kernel.shared.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;

@Provider
@Slf4j
public class AppExceptionMapper extends ExceptionMapperSupport implements ExceptionMapper<AppException> 
{

    @Override
    public Response toResponse(AppException exception) 
    {
        log.warn("Error de aplicación: {} - {}", exception.getErrorCode(), exception.getMessage());
        
        ErrorResponse body = buildErrorResponse(exception);

        return Response.status(exception.getErrorCode().getStatus())
                .entity(body)
                .build();
    }
}
