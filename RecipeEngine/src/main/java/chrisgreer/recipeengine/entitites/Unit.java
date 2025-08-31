package chrisgreer.recipeengine.entitites;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "unit")
@Getter
@Setter
public class Unit {

    @Id
    @Column(nullable = false, name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "abbreviation")
    private String abbreviation;

    @OneToMany(mappedBy = "unit")
    private List<RecipeIngredient> recipeIngredients = new ArrayList<>();

}
