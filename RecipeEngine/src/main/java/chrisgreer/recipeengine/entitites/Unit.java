package chrisgreer.recipeengine.entitites;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "unit")
@NoArgsConstructor
@Getter
@Setter
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "abbreviation")
    private String abbreviation;

    @OneToMany(mappedBy = "unit")
    private List<RecipeIngredient> recipeIngredients = new ArrayList<>();

    public Unit(String name) {
        this.name = name;
        this.abbreviation = name; //By default both the same, may end up dropping
    }
}
