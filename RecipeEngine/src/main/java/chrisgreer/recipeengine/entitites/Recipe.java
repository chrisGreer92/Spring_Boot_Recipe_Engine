package chrisgreer.recipeengine.entitites;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "recipe")
@Getter
@Setter
public class Recipe {
    @Id
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

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant created_at = Instant.now();

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecipeIngredient> ingredients = new ArrayList<>();



}
