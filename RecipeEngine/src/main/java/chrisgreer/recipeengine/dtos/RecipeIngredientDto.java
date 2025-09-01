package chrisgreer.recipeengine.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeIngredientDto {

    private String name;
    private Double amount;
    private String unit;
    private String originalText;

}
