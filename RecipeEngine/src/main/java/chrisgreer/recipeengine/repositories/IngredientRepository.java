package chrisgreer.recipeengine.repositories;

import chrisgreer.recipeengine.entitites.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
  Optional<Ingredient> findByNameIgnoreCase(String name);
}