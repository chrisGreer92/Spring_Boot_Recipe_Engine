package chrisgreer.recipeengine.entitites;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "recipe")
@Getter
@Setter
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "instructions")
    private String instructions;

    @Column(name = "url")
    private String url;

    @Column(name = "diet_type")
    private String dietType;

    @Column(name = "gluten_free")
    private boolean glutenFree;

    @Column(name = "prep_time_minutes")
    private int prepTime;

    @Column(name = "cooking_time_minutes")
    private int cookTime;

    @Column(name = "total_time_minutes")
    private int totalTime;

    @Column(name = "number_served")
    private int serves;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecipeIngredient> ingredients = new ArrayList<>();



}
