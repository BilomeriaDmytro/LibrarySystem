package test.Library.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class BookDTO {

    @NotNull
    @Size(min = 5, max = 25)
    @Pattern(regexp = "^[A-Z][a-zA-Z0-9\\s]*$", message = "Wrong name format")
    private String name;
}
