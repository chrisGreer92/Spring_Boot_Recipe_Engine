package chrisgreer.recipeengine;

import chrisgreer.recipeengine.entitites.Recipe;
import chrisgreer.recipeengine.repositories.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SecurityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.password}")
    private String adminPassword;

    @Autowired
    private RecipeRepository recipeRepository;

    private static final String VALID_RECIPE_JSON = """
        {
          "title": "Example Recipe",
          "description": "For Example",
          "instructions": "1 prep | 2 cook",
          "url": "example.com"
        }
        """;

    @Test
    void getAllRecipes_requiresNoAuth() throws Exception {
        mockMvc.perform(get("/recipes"))
                .andExpect(status().isOk());
    }


    @Test
    void getRecipe_isPublic() throws Exception {
        Recipe recipe = TestUtil.persistRecipe(recipeRepository);

        mockMvc.perform(get("/recipes/{id}",recipe.getId()))
                .andExpect(status().isOk());
    }


    @Test
    void createRecipe_requiresAuth() throws Exception {
        // Without auth
        mockMvc.perform(post("/recipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(VALID_RECIPE_JSON))
                .andExpect(status().isUnauthorized());

        // With auth
        mockMvc.perform(post("/recipes")
                        .with(httpBasic(adminUsername, adminPassword))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(VALID_RECIPE_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void updateRecipe_requiresAuth() throws Exception {
        Recipe recipe = TestUtil.persistRecipe(recipeRepository);

        String updateJson = """
            {"title": "Updated Title"}
            """;

        // Without auth
        mockMvc.perform(patch("/recipes/{id}", recipe.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson))
                .andExpect(status().isUnauthorized());

        // With auth
        mockMvc.perform(patch("/recipes/{id}", recipe.getId())
                        .with(httpBasic(adminUsername, adminPassword))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteRecipe_requiresAuth() throws Exception {
        Recipe recipe = TestUtil.persistRecipe(recipeRepository);

        // Without auth
        mockMvc.perform(delete("/recipes/{id}", recipe.getId()))
                .andExpect(status().isUnauthorized());

        // With auth
        mockMvc.perform(delete("/recipes/{id}", recipe.getId())
                        .with(httpBasic(adminUsername, adminPassword)))
                .andExpect(status().isNoContent());

        assertNull(recipeRepository.findById(recipe.getId()).orElse(null));
    }

}
