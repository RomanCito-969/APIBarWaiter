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
@Table(appliesTo = "cafe")
public class Cafe {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "cafe_id")
    @TableGenerator(name = "cafe_id", table = "cafe_seq")
    private Long id;
    private String nombre;
    private String descripcion;
    private String imagen;

    @Min(value = 0, message = "El precio debe ser mayor o igual a 0")
    @NotNull(message = "El precio no puede ser nulo")
    private Float precio;

}
