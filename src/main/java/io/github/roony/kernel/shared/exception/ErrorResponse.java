package io.github.roony.kernel.shared.exception;

import java.time.Instant;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse 
{
    private String error;
    private String code;
    private String timestamp;
    private String traceId;
    private String path;
    private List<String> details;

    public ErrorResponse(String error, String code) 
    {
        this.error = error;
        this.code = code;
        this.timestamp = Instant.now().toString();
    }
}