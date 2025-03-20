package trongquy.project.E_Commerce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProductDTO {

    @NotBlank(message = "not blank")
    @Size(min = 3, max = 200, message = "Title must be between 3 and 200 characters")
    private String name;

    @Min(value = 0, message = "Price must be grater than or equal to 0")
    @Max(value = 100000000, message = "Price must be less than or equal to 100,000,000")
    private Float price;

    private String thumbnail;

    private String description;

    @JsonProperty("category_id")
    private String categoryId;
}
