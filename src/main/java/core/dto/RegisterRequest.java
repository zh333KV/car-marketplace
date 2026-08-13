package core.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    @Email(message = "Некорректный email")
    @NotBlank(message = "Email обязателен")
    private String email;
    @NotBlank(message = "Пароль обязателен")
    @Size(min = 6, message = "Пароль должен быть не короче 6 символов")
    private String password;
    @NotBlank(message = "Имя обязательно")
    private String firstName;
    private String phone;
    private String city;
}
