package chrisgreer.recipeengine;

import chrisgreer.recipeengine.entitites.Recipe;
import chrisgreer.recipeengine.repositories.RecipeRepository;

public class TestUtil {
    public static Recipe persistRecipe(RecipeRepository recipeRepository) {
        Recipe recipe = createValidRecipe();
        return recipeRepository.save(recipe);
    }

    public static Recipe createValidRecipe() {
        Recipe recipe = new Recipe();
        recipe.setTitle("Example Recipe");
        recipe.setDescription("For Example");
        recipe.setInstructions("1 prep | 2 cook");
        recipe.setUrl("example.com");
        return recipe;
    }
}
