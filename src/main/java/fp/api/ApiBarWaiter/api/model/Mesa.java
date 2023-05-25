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
@Table(appliesTo =  "mesa")
public class Mesa {
    @Id @GeneratedValue(strategy = GenerationType.TABLE, generator = "mesa_id")
    @TableGenerator(name = "mesa_id", table = "mesa_seq")
    private Long id;
    private String nombre;
    private Boolean disponible;
}
