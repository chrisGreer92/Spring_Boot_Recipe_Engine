package chrisgreer.recipeengine.dtos;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CreateRecipeDto {

    private String title;
    private String description;
    private String instructions;
    private String url;
    private String dietType;
    private boolean glutenFree;
    private int prepTime;
    private int cookTime;
    private int totalTime;
    private int serves;

    private List<RecipeIngredientDto> ingredients = new ArrayList<>(); //By default, uses empty list

}
