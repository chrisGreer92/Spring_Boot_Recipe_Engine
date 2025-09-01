package chrisgreer.recipeengine.controllers;


import chrisgreer.recipeengine.dtos.RecipeDto;
import chrisgreer.recipeengine.mappers.RecipeMapper;
import chrisgreer.recipeengine.repositories.RecipeRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/recipes")
@AllArgsConstructor
public class RecipeController {

    private final RecipeRepository recipeRepository;
    private final RecipeMapper recipeMapper;


    @GetMapping
    public List<RecipeDto> getAllRecipes() {
        return recipeRepository.findAll()
                .stream()
                .map(recipeMapper::toDto)
                .toList();
    }

}