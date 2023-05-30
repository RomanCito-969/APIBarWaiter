package fp.api.ApiBarWaiter.api.controller;

import fp.api.ApiBarWaiter.api.model.Plato;
import fp.api.ApiBarWaiter.api.repository.PlatoRepository;
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
@CrossOrigin(origins = "http://localhost:3000")
public class PlatoController {

    private final PlatoRepository platoRepository;
    private final StorageService storageService;
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

    @CrossOrigin(origins = "http://localhost:3000")
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
    @PostMapping(value = "/plato", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Plato> insertarPlato(@RequestPart("nuevo") Plato plato,
                                               @RequestPart("file") MultipartFile file) {
        String urlImagen=null;
        if(!file.isEmpty()){
            String imagen=storageService.store(file);
            urlImagen = MvcUriComponentsBuilder
                    .fromMethodName(FicherosController.class,"serveFile",imagen,null)
                    .build().toUriString();
        }
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
