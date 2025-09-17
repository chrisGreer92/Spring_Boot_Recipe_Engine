package chrisgreer.recipeengine.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UrlDto {
    @NotBlank
    private String url;
}
