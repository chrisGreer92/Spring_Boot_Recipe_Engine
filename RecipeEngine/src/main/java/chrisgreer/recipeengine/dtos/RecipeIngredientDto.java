package chrisgreer.recipeengine.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeIngredientDto {

    private Long id;
    private String name;
    private Double originalAmount;
    private String originalUnit;
    private Double amountG;
    private Double amountMl;
    private String originalText;
    private Boolean isNonUkUnit;

}
