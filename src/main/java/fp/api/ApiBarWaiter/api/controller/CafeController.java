package fp.api.ApiBarWaiter.api.controller;

import fp.api.ApiBarWaiter.api.model.Cafe;
import fp.api.ApiBarWaiter.api.repository.CafeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CafeController {

    private final CafeRepository cafeRepository;
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/cafe")
    public ResponseEntity<?> obtenerCafes(){
        List<Cafe> cafeList = cafeRepository.findAll();
        if (cafeList.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else {
            return ResponseEntity.ok(cafeList);
        }
    }

    @GetMapping("/cafe/{id}")
    public ResponseEntity<?> obtenerUnaCafe(@PathVariable Long id){
        if (cafeRepository.existsById(id)){
            Cafe cafe = cafeRepository.findById(id).orElse(null);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(cafe);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/cafe")
    public ResponseEntity<Cafe> insertarCafe(@RequestBody Cafe mesa) {
        Cafe cafeGuadada = cafeRepository.save(mesa);
        return ResponseEntity.status(HttpStatus.CREATED).body(cafeGuadada);
    }

    @PutMapping("/cafe/{id}")
    public ResponseEntity<?> editarCafe(@PathVariable Long id, @RequestBody Cafe cafe){
        return cafeRepository.findById(id).map(t ->{
            t.setNombre(cafe.getNombre());
            t.setDescripcion(cafe.getDescripcion());
            t.setPrecio(cafe.getPrecio());
            return  ResponseEntity.status(HttpStatus.CREATED).body(t);
        }).orElseGet(() -> {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        });
    }

    @DeleteMapping("/cafe/{id}")
    public ResponseEntity<?> eliminarCafe(@PathVariable Long id){
        if (cafeRepository.existsById(id)){
            Cafe cafe = cafeRepository.findById(id).get();
            cafeRepository.delete(cafe);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
