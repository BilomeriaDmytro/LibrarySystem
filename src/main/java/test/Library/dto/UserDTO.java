package test.Library.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UserDTO {

    @NotNull
    @Size(min = 2, max = 15)
    @Pattern(regexp = "^[A-Z][a-z]+", message = "Wrong name format")
    private String firstName;

    @NotNull
    @Size(min = 2, max = 15)
    @Pattern(regexp = "^[A-Z][a-z]+", message = "Wrong name format")
    private String lastName;
}
