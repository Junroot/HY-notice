package hynotice.api.presentation.controller;

import hynotice.core.configuration.ExceptionTracer;
import hynotice.core.exception.ExpectedException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    private static final Logger LOGGER = LogManager.getLogger(ControllerAdvice.class);
    private static final String ERROR_LOG = "[ERROR] %s : %s";

    private final ExceptionTracer exceptionTracer;

    public ControllerAdvice(final ExceptionTracer exceptionTracer) {
        this.exceptionTracer = exceptionTracer;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> unexpectedException(final Exception exception) {
        LOGGER.error(String.format(ERROR_LOG, exception.getClass().getSimpleName(),
            exceptionTracer.getStackTrace(exception)));
        return ResponseEntity.internalServerError()
            .body(new ExceptionDto("unexpected exception"));
    }

    @ExceptionHandler(ExpectedException.class)
    public ResponseEntity<ExceptionDto> expectedException(final Exception exception) {
        return ResponseEntity.badRequest()
            .body(new ExceptionDto(exception.getMessage()));
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    private static class ExceptionDto {

        private String message;
    }
}
