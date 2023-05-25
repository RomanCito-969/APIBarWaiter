package fp.api.ApiBarWaiter.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(appliesTo =  "producto")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "tapa_id")
    @TableGenerator(name = "tapa_id", table = "tapa_seq")
    private Long id;
    private String nombre;
    private String imagen;

    @Min(value = 0, message = "El precio debe ser mayor o igual a 0")
    private Float cantidad;
}
