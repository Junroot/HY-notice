package hynotice.api.presentation.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    private final String ERROR_LOG = "[ERROR] %s : %s";

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> unexpectedException(final Exception exception) {
        exception.printStackTrace();
        return ResponseEntity.badRequest().body(new ExceptionDto("unexpected exception"));
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    static class ExceptionDto {

        private String message;
    }
}
