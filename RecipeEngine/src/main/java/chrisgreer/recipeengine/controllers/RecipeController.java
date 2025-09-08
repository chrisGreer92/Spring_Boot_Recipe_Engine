package chrisgreer.recipeengine.controllers;


import chrisgreer.recipeengine.dtos.CreateRecipeDto;
import chrisgreer.recipeengine.dtos.RecipeDto;
import chrisgreer.recipeengine.dtos.UpdateRecipeDto;
import chrisgreer.recipeengine.entitites.*;
import chrisgreer.recipeengine.repositories.IngredientRepository;
import chrisgreer.recipeengine.mappers.RecipeMapper;
import chrisgreer.recipeengine.repositories.RecipeRepository;
import chrisgreer.recipeengine.repositories.UnitRepository;
import chrisgreer.recipeengine.services.RecipeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/recipes")
@AllArgsConstructor
public class RecipeController {

    private final RecipeRepository recipeRepository;
    private final RecipeMapper recipeMapper;
    private final IngredientRepository ingredientRepository;
    private final UnitRepository unitRepository;
    private final RecipeService recipeService;


    @PostMapping
    public ResponseEntity<RecipeDto> createRecipe(
            @RequestBody CreateRecipeDto dto,
            UriComponentsBuilder uriBuilder
            ){

        RecipeDto recipeDto = recipeService.createRecipe(dto);

        var uri = uriBuilder.path("/{id}")
                .buildAndExpand(recipeDto.getId())
                .toUri();

        return ResponseEntity.created(uri).body(recipeDto);

    }

    @GetMapping
    public List<RecipeDto> getAllRecipes() {return recipeService.getAllRecipes();}

    @GetMapping("/{id}")
    public ResponseEntity<RecipeDto> getRecipe(
            @PathVariable Long id
    ) {
        return recipeService.getRecipe(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateRecipe(
            @PathVariable Long id,
            @RequestBody UpdateRecipeDto dto
            ){
        return recipeService.updateRecipe(id, dto)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(
            @PathVariable Long id
    ){
        return recipeService.deleteRecipe(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}/ingredients/{ingredientId}")
    public ResponseEntity<Void> deleteIngredient(
            @PathVariable Long id,
            @PathVariable Long ingredientId
    ){
        return recipeService.deleteIngredient(id, ingredientId)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

}