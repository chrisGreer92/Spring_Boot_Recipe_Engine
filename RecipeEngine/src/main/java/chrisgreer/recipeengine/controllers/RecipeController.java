package chrisgreer.recipeengine.controllers;


import chrisgreer.recipeengine.dtos.CreateRecipeDto;
import chrisgreer.recipeengine.dtos.RecipeDto;
import chrisgreer.recipeengine.dtos.UpdateRecipeDto;
import chrisgreer.recipeengine.dtos.UrlDto;
import chrisgreer.recipeengine.repositories.IngredientRepository;
import chrisgreer.recipeengine.mappers.RecipeMapper;
import chrisgreer.recipeengine.repositories.RecipeRepository;
import chrisgreer.recipeengine.services.GumloopService;
import chrisgreer.recipeengine.services.RecipeService;
import chrisgreer.recipeengine.web.ResponseMapper;
import jakarta.validation.Valid;
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
    private final RecipeService recipeService;
    private final GumloopService gumloopService;


    @PostMapping("/ingest")
    public ResponseEntity<Void> ingestUrl(
            @RequestBody @Valid
            UrlDto dto
    ) {
        gumloopService.sendUrl(dto.getUrl());
        return ResponseEntity.accepted().build();
    }

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
        return ResponseMapper.toResponse(recipeService.updateRecipe(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(
            @PathVariable Long id
    ){
        return ResponseMapper.toResponse(recipeService.deleteRecipe(id));
    }

    @DeleteMapping("/{id}/ingredients/{ingredientId}")
    public ResponseEntity<Void> removeIngredient(
            @PathVariable Long id,
            @PathVariable Long ingredientId
    ){
        return ResponseMapper.toResponse(recipeService.removeIngredient(id, ingredientId));
    }

}