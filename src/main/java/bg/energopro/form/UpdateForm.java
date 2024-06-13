package bg.energopro.form;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
public class UpdateForm {
    @NotNull(message = "User id cannot be empty.")
    private Long userId;
    @NotEmpty(message = "First name cannot be empty.")
    private String firstName;
    @NotEmpty(message = "Last name cannot be empty.")
    private String lastName;
    @NotEmpty(message = "Email cannot be empty.")
    @Email(message = "Invalid email. Please enter a valid email address")
    private String email;
    private String address;
    private String phone;
    private String bio;
}
