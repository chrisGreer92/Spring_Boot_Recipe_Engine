package chrisgreer.recipeengine.mappers;

import chrisgreer.recipeengine.dtos.CreateRecipeDto;
import chrisgreer.recipeengine.dtos.RecipeDto;
import chrisgreer.recipeengine.dtos.RecipeIngredientDto;
import chrisgreer.recipeengine.dtos.UpdateRecipeDto;
import chrisgreer.recipeengine.entitites.Recipe;
import chrisgreer.recipeengine.entitites.RecipeIngredient;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RecipeMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "ingredients", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Recipe updateRecipe(UpdateRecipeDto dto, @MappingTarget Recipe recipe);

    UpdateRecipeDto toUpdateDto(Recipe recipe);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "instructions", source = "instructions", qualifiedByName = "pipeToNewline")
    Recipe toEntity(CreateRecipeDto dto);

    @Named("pipeToNewline")
    static String pipeToNewline(String instructions) {
        return instructions == null ? null : instructions.replace("|", "\n");
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "recipe", ignore = true)
    @Mapping(target = "ingredient", ignore = true)
    @Mapping(target = "unit", ignore = true)
    RecipeIngredient toEntity(RecipeIngredientDto dto);

    RecipeDto toDto(Recipe recipe);

    @Mapping(target = "unit", source = "unit.name")
    @Mapping(target = "name", source = "ingredient.name")
    RecipeIngredientDto toDto(RecipeIngredient recipeIngredient);


    List<RecipeDto> toDtoList(List<Recipe> recipes);


}
