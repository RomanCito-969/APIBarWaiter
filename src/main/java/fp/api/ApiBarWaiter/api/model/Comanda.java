package fp.api.ApiBarWaiter.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Table;

import java.time.LocalDateTime;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(appliesTo =  "comanda")
public class Comanda {
    @Id @GeneratedValue(strategy = GenerationType.TABLE, generator = "comanda_id")
    @TableGenerator(name = "comanda_id", table = "comanda_seq")
    private Long id;
    private String nombre;
    private LocalDateTime fechaComanda;

    @ManyToOne
    @JoinColumn(name="mesa_id")
    private Mesa mesa;
    @ManyToMany
    @JoinColumn(name="producto_id")
    private List<Producto> productoList ;

}
