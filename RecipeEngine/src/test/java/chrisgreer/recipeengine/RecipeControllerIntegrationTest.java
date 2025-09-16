package chrisgreer.recipeengine;

import chrisgreer.recipeengine.entitites.Ingredient;
import chrisgreer.recipeengine.entitites.Recipe;
import chrisgreer.recipeengine.repositories.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@Transactional
public class RecipeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RecipeRepository recipeRepository;

    @Test
    void getRecipe_ShouldReturnNotFound_whenRecipeDoesNotExist() throws Exception {
        mockMvc.perform(get("/recipes/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllRecipesReturnsAList() throws Exception {
        recipeRepository.deleteAll();
        int totalRecipes = 3;
        for(int i = 0 ; i < totalRecipes ; i++){
            Recipe recipe = createRecipe();
            recipeRepository.save(recipe);
        }

        mockMvc.perform(get("/recipes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(totalRecipes));

    }

    private Recipe createRecipe(){
        Recipe recipe = new Recipe();
        recipe.setTitle("Example Recipe");
        recipe.setDescription("For Example");
        recipe.setInstructions("1 prep | 2 cook");
        recipe.setUrl("example.com");
        recipe.setIngredients(new ArrayList<>());
        return recipe;
    }

    private Ingredient makeIngredient(String name, double qty, String unit, Recipe recipe) {
        Ingredient ing = new Ingredient();
        ing.setName(name);
        return ing;
    }
}
