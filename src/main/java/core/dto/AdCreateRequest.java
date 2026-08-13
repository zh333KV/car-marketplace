package core.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Getter
@Setter
public class AdCreateRequest {
    @NotBlank(message = "Название обязательно")
    @Size(max = 150, message = "Название не должно быть длиннее 150 символов")
    private String title;
    @NotBlank(message = "Описание обязательно")
    @Size(max = 2000, message = "Описание не должно быть длиннее 2000 символов")
    private String description;
    @NotNull(message = "Цена обязательна")
    @DecimalMin(value = "0.0", inclusive = false, message = "Цена должна быть больше 0")
    private BigDecimal price;
    @NotBlank(message = "Город обязателен")
    private String city;
    private MultipartFile[] images;
}
