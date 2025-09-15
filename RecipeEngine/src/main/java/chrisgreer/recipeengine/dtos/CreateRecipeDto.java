package chrisgreer.recipeengine.dtos;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateRecipeDto {

    private String title;
    private String description;
    private String instructions;
    private String url;
    private List<RecipeIngredientDto> ingredients;

}
