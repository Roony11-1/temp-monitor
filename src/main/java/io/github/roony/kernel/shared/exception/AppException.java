package io.github.roony.kernel.shared.exception;


public class AppException extends RuntimeException 
{
    private final ErrorCode errorCode;
    private final String customMessage;

    public AppException(ErrorCode errorCode) 
    {
        this(errorCode, null);
    }

    public AppException(ErrorCode errorCode, String customMessage) 
    {
        super(customMessage != null ? customMessage : errorCode.getMensajePorDefecto());
        this.errorCode = errorCode;
        this.customMessage = customMessage;
    }

    public ErrorCode getErrorCode() { return errorCode; }

    public String getMessageForResponse() 
    {
        return customMessage != null ? customMessage : errorCode.getMensajePorDefecto();
    }
}