package test.Library.service.exceptionHandler;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import test.Library.service.exceptionHandler.exception.MyFieldError;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ApiError {
    private HttpStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;

    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<MyFieldError> fieldErrors;

    private ApiError() {
        timestamp = LocalDateTime.now();
    }


    ApiError(HttpStatus status, String message, Throwable ex) {
        this();
        this.status = status;
        this.message = message;
    }

    ApiError(HttpStatus status, String message, Throwable ex, List<MyFieldError> fieldErrors) {
        this();
        this.status = status;
        this.message = message;
        this.fieldErrors = fieldErrors;
    }
}
