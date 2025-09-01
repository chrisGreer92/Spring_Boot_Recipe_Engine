package chrisgreer.recipeengine.entitites;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "unit_conversion")
@Getter
@Setter
public class UnitConversion {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "factor")
    private Double factor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_unit_id")
    private Unit fromUnit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_unit_id")
    private Unit toUnit;
    

}
