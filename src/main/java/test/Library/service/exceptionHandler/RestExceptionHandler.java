package test.Library.service.exceptionHandler;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import test.Library.service.exceptionHandler.exception.BadRequestParameters;


@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(BadRequestParameters.class)
    protected ResponseEntity<Object> handleUserNotFound(BadRequestParameters ex) {

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(),ex, ex.getErrors());
        return buildResponseEntity(apiError);
    }

}