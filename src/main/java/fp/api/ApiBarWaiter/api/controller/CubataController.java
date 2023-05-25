package fp.api.ApiBarWaiter.api.controller;

import fp.api.ApiBarWaiter.api.model.Cubata;
import fp.api.ApiBarWaiter.api.repository.CubataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CubataController {

    private final CubataRepository cubataRepository;
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/cubata")
    public ResponseEntity<?> obtenerCubatas(){
        List<Cubata> cubataList = cubataRepository.findAll();
        if (cubataList.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else {
            return ResponseEntity.ok(cubataList);
        }
    }

    @GetMapping("/cubata/{id}")
    public ResponseEntity<?> obtenerUnaCubata(@PathVariable Long id){
        if (cubataRepository.existsById(id)){
            Cubata cubata = cubataRepository.findById(id).orElse(null);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(cubata);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/cubata")
    public ResponseEntity<Cubata> insertarCubata(@RequestBody Cubata cubata) {
        Cubata cubataGuadada = cubataRepository.save(cubata);
        return ResponseEntity.status(HttpStatus.CREATED).body(cubataGuadada);
    }

    @PutMapping("/cubata/{id}")
    public ResponseEntity<?> editarCubata(@PathVariable Long id, @RequestBody Cubata cubata){
        return cubataRepository.findById(id).map(t ->{
            t.setNombre(cubata.getNombre());
            t.setDescripcion(cubata.getDescripcion());
            t.setPrecio(cubata.getPrecio());
            return  ResponseEntity.status(HttpStatus.CREATED).body(t);
        }).orElseGet(() -> {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        });
    }

    @DeleteMapping("/cubata/{id}")
    public ResponseEntity<?> eliminarCubata(@PathVariable Long id){
        if (cubataRepository.existsById(id)){
            Cubata cubata = cubataRepository.findById(id).get();
            cubataRepository.delete(cubata);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
