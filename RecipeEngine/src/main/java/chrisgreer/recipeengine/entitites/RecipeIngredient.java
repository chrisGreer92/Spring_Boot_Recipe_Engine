package chrisgreer.recipeengine.entitites;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "recipe_ingredient")
@RequiredArgsConstructor
@Getter
@Setter
public class RecipeIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id")
    private Long id;

    @Column(name = "original_amount")
    private Double originalAmount;

    @Column(name = "original_unit")
    private String originalUnit;

    @Column(name = "amount_in_grams")
    private Double amountG;

    @Column(name = "amount_in_milliliters")
    private Double amountMl;

    @Column(name = "original_text")
    private String originalText;

    @Column(name = "is_non_uk_unit")
    private Boolean isNonUkUnit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;

    public RecipeIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

}
