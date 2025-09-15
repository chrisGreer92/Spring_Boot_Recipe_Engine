package chrisgreer.recipeengine.services;

import chrisgreer.recipeengine.model.ServiceResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import chrisgreer.recipeengine.repositories.*;
import chrisgreer.recipeengine.mappers.*;
import chrisgreer.recipeengine.dtos.*;
import chrisgreer.recipeengine.entitites.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeMapper recipeMapper;


    @Transactional
    public RecipeDto createRecipe(CreateRecipeDto dto) {
        Recipe recipe = recipeMapper.toEntity(dto);

        List<RecipeIngredient> recipeIngredients = new ArrayList<>();
        for (RecipeIngredientDto ingredientDto : dto.getIngredients()) {
            Ingredient ingredient = ingredientRepository
                    .findByNameIgnoreCase(ingredientDto.getName())
                    .orElseGet(() -> ingredientRepository.save(new Ingredient(ingredientDto.getName())));

            RecipeIngredient recipeIngredient = recipeMapper.toEntity(ingredientDto);
            recipeIngredient.setRecipe(recipe);
            recipeIngredient.setIngredient(ingredient);
//            recipeIngredient.setUnit(unit);

            recipeIngredients.add(recipeIngredient);
        }

        recipe.setIngredients(recipeIngredients);
        recipeRepository.save(recipe);

        return recipeMapper.toDto(recipe);
    }

    public List<RecipeDto> getAllRecipes() {
        return recipeRepository.findAll()
                .stream()
                .map(recipeMapper::toDto)
                .toList();
    }

    public Optional<RecipeDto> getRecipe(Long id) {
        return recipeRepository.findById(id)
                .map(recipeMapper::toDto);
    }

    @Transactional
    public ServiceResult updateRecipe(Long id, UpdateRecipeDto dto) {
        Recipe recipe = recipeRepository.findById(id).orElse(null);
        if(recipe == null) return ServiceResult.NOT_FOUND;

        Recipe updatedRecipe = recipeMapper.updateRecipe(dto, recipe);
        recipeRepository.save(updatedRecipe);
        return ServiceResult.SUCCESS;
    }

    @Transactional
    public ServiceResult deleteRecipe(Long id){
        Recipe recipe = recipeRepository.findById(id).orElse(null);
        if (recipe == null) return ServiceResult.NOT_FOUND;

        recipeRepository.delete(recipe);
        return ServiceResult.SUCCESS;
    }

    @Transactional
    public ServiceResult deleteIngredient(Long id, Long ingredientId){

        Recipe recipe = recipeRepository.findById(id).orElse(null);
        if (recipe == null) return ServiceResult.NOT_FOUND;

        RecipeIngredient toRemove = recipe.getIngredients().stream()
                .filter(i -> i.getId().equals(ingredientId))
                .findFirst()
                .orElse(null);

        if (toRemove == null) return ServiceResult.NOT_FOUND;

        recipe.getIngredients().remove(toRemove);
        recipeRepository.save(recipe);
        return ServiceResult.SUCCESS;
    }




}
