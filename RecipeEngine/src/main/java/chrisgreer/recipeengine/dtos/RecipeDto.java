package chrisgreer.recipeengine.dtos;


import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
public class RecipeDto {

    private Long id;
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

    private Instant createdAt;
    private List<RecipeIngredientDto> ingredients;

}
