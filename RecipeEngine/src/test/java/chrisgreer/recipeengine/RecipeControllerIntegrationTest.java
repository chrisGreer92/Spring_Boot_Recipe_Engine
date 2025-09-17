package chrisgreer.recipeengine;

import chrisgreer.recipeengine.entitites.Ingredient;
import chrisgreer.recipeengine.entitites.Recipe;
import chrisgreer.recipeengine.entitites.RecipeIngredient;
import chrisgreer.recipeengine.repositories.IngredientRepository;
import chrisgreer.recipeengine.repositories.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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
    @Autowired
    private IngredientRepository ingredientRepository;

    @Test
    void getRecipe_ShouldReturnNotFound_whenRecipeDoesNotExist() throws Exception {
        mockMvc.perform(get("/recipes/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllRecipes_ShouldReturnList_WhenRecipesExist() throws Exception {
        recipeRepository.deleteAll(); //Make sure don't still have any from previous
        TestUtil.persistRecipe(recipeRepository);
        TestUtil.persistRecipe(recipeRepository);

        mockMvc.perform(get("/recipes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void getRecipe_ShouldReturnRecipe_WhenExists() throws Exception {
        Recipe saved = TestUtil.persistRecipe(recipeRepository);

        mockMvc.perform(get("/recipes/" + saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(saved.getId()))
                .andExpect(jsonPath("$.title").value("Example Recipe"));
    }

    @Test
    void updateRecipe_ShouldReturn204_WhenSuccess() throws Exception {
        Recipe saved = TestUtil.persistRecipe(recipeRepository);

        String json = """
            {
              "title": "Updated Title",
              "description": "Updated Desc"
            }
            """;

        mockMvc.perform(patch("/recipes/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/recipes/" + saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"))
                .andExpect(jsonPath("$.description").value("Updated Desc"));
    }

    @Test
    void deleteRecipe_ShouldRemoveRecipe() throws Exception {
        Recipe saved = TestUtil.persistRecipe(recipeRepository);

        mockMvc.perform(delete("/recipes/" + saved.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/recipes/" + saved.getId()))
                .andExpect(status().isNotFound());

        assertNull(recipeRepository.findById(saved.getId()).orElse(null));
    }

    @Test
    void removeIngredient_ShouldUnlinkIngredient_WhenExists() throws Exception {
        // Given recipe with two ingredients
        Ingredient flour = ingredientRepository.save(new Ingredient("Flour"));
        Ingredient sugar = ingredientRepository.save(new Ingredient("Sugar"));

        Recipe recipe = TestUtil.createValidRecipe();
        RecipeIngredient flourLink = new RecipeIngredient(flour);
        RecipeIngredient sugarLink = new RecipeIngredient(sugar);

        flourLink.setRecipe(recipe);
        sugarLink.setRecipe(recipe);

        recipe.setIngredients(new ArrayList<>(List.of(flourLink, sugarLink)));
        Recipe savedRecipe = recipeRepository.save(recipe);

        Long recipeId = savedRecipe.getId();
        Long flourId = flour.getId();
        Long sugarId = sugar.getId();

        // When removing flour
        mockMvc.perform(delete("/recipes/" + recipeId + "/ingredients/" + flourId))
                .andExpect(status().isNoContent());

        // Recipe still exists
        Recipe updated = recipeRepository.findById(recipeId).orElseThrow();

        // Ingredients list just contains sugar
        assertEquals(1, updated.getIngredients().size());
        assertEquals(sugarId, updated.getIngredients().get(0).getIngredient().getId());

        // Flour ingredient still exists as an Ingredient record
        assertTrue(ingredientRepository.findById(flourId).isPresent());
    }
}
