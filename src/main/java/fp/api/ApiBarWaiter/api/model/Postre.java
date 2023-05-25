package fp.api.ApiBarWaiter.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Table;

@Data @AllArgsConstructor @NoArgsConstructor
@Entity
@Table(appliesTo =  "postre")
public class Postre {
    @Id @GeneratedValue(strategy = GenerationType.TABLE, generator = "postre_id")
    @TableGenerator(name = "postre_id", table = "postre_seq")
    private Long id;
    private String nombre;
    private String descripcion;
    private String imagen;

    @Min(value = 0, message = "El precio debe ser mayor o igual a 0")
    @NotNull(message = "El precio no puede ser nulo")
    private Float precio;

}
