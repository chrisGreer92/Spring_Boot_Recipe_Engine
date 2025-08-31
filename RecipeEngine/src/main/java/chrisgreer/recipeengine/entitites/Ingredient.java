package chrisgreer.recipeengine.entitites;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ingredient")
@Getter
@Setter
public class Ingredient {

    @Id
    @Column(nullable = false, name = "id")
    private Long id;

    @Column(name = "name")
    String name;

    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecipeIngredient> recipes = new ArrayList<>();

}
