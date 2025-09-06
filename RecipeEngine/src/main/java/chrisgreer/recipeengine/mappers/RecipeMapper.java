package chrisgreer.recipeengine.mappers;

import chrisgreer.recipeengine.dtos.CreateRecipeDto;
import chrisgreer.recipeengine.dtos.RecipeDto;
import chrisgreer.recipeengine.dtos.RecipeIngredientDto;
import chrisgreer.recipeengine.entitites.Recipe;
import chrisgreer.recipeengine.entitites.RecipeIngredient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RecipeMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "instructions", source = "instructions", qualifiedByName = "pipeToNewline")
    Recipe toEntity(CreateRecipeDto dto);

    @Named("pipeToNewline")
    static String pipeToNewline(String instructions) {
        return instructions == null ? null : instructions.replace("|", "\n");
    }

    RecipeDto toDto(Recipe recipe);

    RecipeIngredientDto toDto(RecipeIngredient recipeIngredient);

    List<RecipeDto> toDtoList(List<Recipe> recipes);

}
