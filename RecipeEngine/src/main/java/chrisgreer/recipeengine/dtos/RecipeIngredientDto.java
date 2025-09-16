package chrisgreer.recipeengine.dtos;

import chrisgreer.recipeengine.entitites.Ingredient;
import chrisgreer.recipeengine.entitites.Recipe;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeIngredientDto {

    private Long id;
    private Double originalAmount;
    private String originalUnit;
    private Double amountG;
    private Double amountMl;
    private String originalText;
    private Boolean nonUkUnit;
    private Recipe recipe;
    private Ingredient ingredient;

}
