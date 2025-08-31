package chrisgreer.recipeengine.entitites;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "recipe_ingredient")
@Getter
@Setter
public class RecipeIngredient {

    @Id
    @Column(nullable = false, name = "id")
    private Long id;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "original_text")
    private String originalText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id", nullable = false)
    private Unit unit;



}
