package chrisgreer.recipeengine.repositories;

import chrisgreer.recipeengine.entitites.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}
