package az.ingress.turbo.az_clone.common.exception;

import java.time.Instant;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(BaseException ex, WebRequest request) {
        ErrorResponse body = ErrorResponse.builder()
                .status(ex.getHttpStatus().value())
                .code(ex.getErrorCode().getCode())
                .message(ex.getFormattedDetails())
                .timestamp(Instant.now())
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .build();
        return ResponseEntity.status(ex.getHttpStatus()).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpected(Exception ex, WebRequest request) {
        log.error("Unhandled exception", ex);
        ErrorCode code = ErrorCode.INTERNAL_SERVER_ERROR;
        ErrorResponse body = ErrorResponse.builder()
                .status(code.getHttpStatus().value())
                .code(code.getCode())
                .message(code.getMessage())
                .timestamp(Instant.now())
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .build();
        return ResponseEntity.status(code.getHttpStatus()).body(body);
    }

}
