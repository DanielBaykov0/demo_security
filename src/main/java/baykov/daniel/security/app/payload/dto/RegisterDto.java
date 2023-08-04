package baykov.daniel.security.app.payload.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record RegisterDto(

        @NotEmpty(message = "First Name should not be null or empty")
        @Size(min = 2, message = "Name should have at least 2 characters")
        String firstName,

        @NotEmpty(message = "Last Name should not be null or empty")
        @Size(min = 2, message = "Name should have at least 2 characters")
        String lastName,

        @NotEmpty(message = "Password should not be null or empty")
        @Size(min = 8, max = 20, message = "Password should be between 8 and 20 characters")
        String password,

        @NotEmpty(message = "Email should not be null or empty")
        @Email
        String email
) {
}
