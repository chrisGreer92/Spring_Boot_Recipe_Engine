package chrisgreer.recipeengine.controllers;


import chrisgreer.recipeengine.dtos.CreateRecipeDto;
import chrisgreer.recipeengine.dtos.RecipeDto;
import chrisgreer.recipeengine.dtos.RecipeIngredientDto;
import chrisgreer.recipeengine.entitites.*;
import chrisgreer.recipeengine.repositories.IngredientRepository;
import chrisgreer.recipeengine.mappers.RecipeMapper;
import chrisgreer.recipeengine.repositories.RecipeRepository;
import chrisgreer.recipeengine.repositories.UnitRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/recipes")
@AllArgsConstructor
public class RecipeController {

    private final RecipeRepository recipeRepository;
    private final RecipeMapper recipeMapper;
    private final IngredientRepository ingredientRepository;
    private final UnitRepository unitRepository;


    @PostMapping
    public ResponseEntity<RecipeDto> createRecipe(
            @RequestBody CreateRecipeDto dto,
            UriComponentsBuilder uriBuilder
            ){

        Recipe recipe = recipeMapper.toEntity(dto);

        //Build list of ingredients included in the recipe
        List<RecipeIngredient> recipeIngredients = new ArrayList<>();
        for(RecipeIngredientDto ingredientDto : dto.getIngredients()){

            //Check if ingredient already exists, if not save it to repository
            Ingredient ingredient = ingredientRepository
                    .findByNameIgnoreCase(ingredientDto.getName()).orElse(null);

            if(ingredient == null) ingredient = ingredientRepository.save(new Ingredient(ingredientDto.getName()));

            //Similarly check if unit already exists and save if not
            Unit unit = unitRepository.findByNameIgnoreCase(ingredientDto.getUnit()).orElse(null);

            if(unit == null) unit = unitRepository.save(new Unit(ingredientDto.getUnit()));

            //Then, build a recipe-ingredient entity for linking all these together
            RecipeIngredient recipeIngredient = recipeMapper.toEntity(ingredientDto);
            recipeIngredient.setRecipe(recipe);
            recipeIngredient.setIngredient(ingredient);
            recipeIngredient.setUnit(unit);

            //Finally, add the recipeIngredient to our list as an entity
            recipeIngredients.add(recipeIngredient);

        }

        //Add our list of recipeIngredient links to the recipe
        recipe.setIngredients(recipeIngredients);

        recipeRepository.save(recipe);

        RecipeDto recipeDto = recipeMapper.toDto(recipe);
        var uri = uriBuilder.path("/{id}")
                .buildAndExpand(recipeDto.getId())
                .toUri();

        return ResponseEntity.created(uri).body(recipeDto);

    }


    @GetMapping
    public List<RecipeDto> getAllRecipes() {
        return recipeRepository.findAll()
                .stream()
                .map(recipeMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeDto> getRecipes(
            @PathVariable Long id
    ) {

        var recipe = recipeRepository.findById(id).orElse(null);

        return (recipe == null)
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(recipeMapper.toDto(recipe));
    }



}