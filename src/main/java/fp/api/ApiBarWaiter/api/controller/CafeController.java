package fp.api.ApiBarWaiter.api.controller;

import fp.api.ApiBarWaiter.api.model.Bebida;
import fp.api.ApiBarWaiter.api.model.Cafe;
import fp.api.ApiBarWaiter.api.repository.CafeRepository;
import fp.api.ApiBarWaiter.api.upload.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CafeController {

    private final CafeRepository cafeRepository;
    private final StorageService storageService;

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
    @PostMapping(value = "/cafe", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Cafe> insertarCafe(@RequestPart("nuevo")Cafe cafe,
                                             @RequestPart("file") MultipartFile file) {
        String urlImagen = null;
        if (!file.isEmpty()){
            String imagen = storageService.store(file);
            urlImagen =  MvcUriComponentsBuilder
                    // El segundo argumento es necesario solo cuando queremos obtener la imagen
                    // En este caso tan solo necesitamos obtener la URL
                    .fromMethodName(FicherosController.class, "serveFile", imagen, null)
                    .build().toUriString();
        }
        Cafe cafeGuadada = cafe;
        cafeGuadada.setImagen(urlImagen);
        cafeRepository.save(cafeGuadada);
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
