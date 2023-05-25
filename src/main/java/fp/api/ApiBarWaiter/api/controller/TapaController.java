package fp.api.ApiBarWaiter.api.controller;

import fp.api.ApiBarWaiter.api.model.Tapa;
import fp.api.ApiBarWaiter.api.repository.TapaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TapaController {

    private final TapaRepository tapaRepository;
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/tapa")
    public ResponseEntity<?> obtenerTapa(){
        List<Tapa> tapaList = tapaRepository.findAll();
        if (tapaList.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else {
            return ResponseEntity.ok(tapaList);
        }
    }

    @GetMapping("/tapa/{id}")
    public ResponseEntity<?> obtenerUnaTapa(@PathVariable Long id){
        if (tapaRepository.existsById(id)){
            Tapa mesa = tapaRepository.findById(id).orElse(null);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(mesa);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/tapa")
    public ResponseEntity<Tapa> insertarTapa(@RequestBody Tapa mesa) {
        Tapa mesaGuadada = tapaRepository.save(mesa);
        return ResponseEntity.status(HttpStatus.CREATED).body(mesaGuadada);
    }

    @PutMapping("/tapa/{id}")
    public ResponseEntity<?> editarTapa(@PathVariable Long id, @RequestBody Tapa tapa){
        return tapaRepository.findById(id).map(t ->{
            t.setNombre(tapa.getNombre());
            t.setDescripcion(tapa.getDescripcion());
            t.setPrecio(tapa.getPrecio());
            return  ResponseEntity.status(HttpStatus.CREATED).body(t);
        }).orElseGet(() -> {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        });
    }

    @DeleteMapping("/tapa/{id}")
    public ResponseEntity<?> eliminarMesa(@PathVariable Long id){
        if (tapaRepository.existsById(id)){
            Tapa tapa = tapaRepository.findById(id).get();
            tapaRepository.delete(tapa);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
