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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id")
    private Long id;

    @Column(name = "original_amount")
    private Double original_amount;

    @Column(name = "original_amount")
    private String original_unit;

    @Column(name = "amount_in_grams")
    private Double amount_g;

    @Column(name = "amount_in_milliliters")
    private Double amount_ml;

    @Column(name = "original_text")
    private String originalText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;

}
