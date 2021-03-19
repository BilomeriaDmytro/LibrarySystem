package test.Library.service.exceptionHandler.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MyFieldError {

    private String field;

    private String message;

}
