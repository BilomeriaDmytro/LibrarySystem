package test.Library.service.exceptionHandler.exception;

import lombok.Getter;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BadRequestParameters extends Exception{

    private List<MyFieldError> errors;

    public BadRequestParameters(String message){
        super(message);
    }

    public BadRequestParameters(String message , List<FieldError> errors){
        super(message);
        List<MyFieldError> myFieldErrors = new ArrayList<>();
        errors.forEach(error -> myFieldErrors.add(new MyFieldError(error.getField(),error.getDefaultMessage())));
        this.errors = myFieldErrors;
    }

}
