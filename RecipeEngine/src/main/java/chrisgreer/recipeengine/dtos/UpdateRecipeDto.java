package chrisgreer.recipeengine.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateRecipeDto {

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

}
