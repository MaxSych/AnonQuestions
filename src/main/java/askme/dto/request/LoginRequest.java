package askme.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "The email field cannot be left blank")
    @Email(message = "Invalid email address")
    private String email;

    @NotBlank(message = "The password cannot be blank")
    private String password;

}
