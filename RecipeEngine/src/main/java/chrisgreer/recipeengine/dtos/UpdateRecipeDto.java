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

}
