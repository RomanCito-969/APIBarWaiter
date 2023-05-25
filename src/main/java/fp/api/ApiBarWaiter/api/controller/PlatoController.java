package fp.api.ApiBarWaiter.api.controller;

import fp.api.ApiBarWaiter.api.model.Plato;
import fp.api.ApiBarWaiter.api.repository.PlatoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PlatoController {

    private final PlatoRepository platoRepository;
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/plato")
    public ResponseEntity<?> obtenerPlatos(){
        List<Plato> platoList = platoRepository.findAll();
        if (platoList.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else {
            return ResponseEntity.ok(platoList);
        }
    }

    @GetMapping("/plato/{id}")
    public ResponseEntity<?> obtenerUnaPlato(@PathVariable Long id){
        if (platoRepository.existsById(id)){
            Plato plato = platoRepository.findById(id).orElse(null);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(plato);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/plato")
    public ResponseEntity<Plato> insertarPlato(@RequestBody Plato plato) {
        Plato platoGuadada = platoRepository.save(plato);
        return ResponseEntity.status(HttpStatus.CREATED).body(platoGuadada);
    }

    @PutMapping("/plato/{id}")
    public ResponseEntity<?> editarPlato(@PathVariable Long id, @RequestBody Plato plato){
        return platoRepository.findById(id).map(t ->{
            t.setNombre(plato.getNombre());
            t.setDescripcion(plato.getDescripcion());
            t.setPrecio(plato.getPrecio());
            return  ResponseEntity.status(HttpStatus.CREATED).body(t);
        }).orElseGet(() -> {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        });
    }

    @DeleteMapping("/plato/{id}")
    public ResponseEntity<?> eliminarPlato(@PathVariable Long id){
        if (platoRepository.existsById(id)){
            Plato plato = platoRepository.findById(id).get();
            platoRepository.delete(plato);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
