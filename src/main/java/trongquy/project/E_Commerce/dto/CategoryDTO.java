package trongquy.project.E_Commerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
public class CategoryDTO {

    @NotBlank(message = "category_name can not be empty")
    private String name;

}
