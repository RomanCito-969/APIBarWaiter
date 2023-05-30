package fp.api.ApiBarWaiter.api.controller;

import fp.api.ApiBarWaiter.api.model.Postre;
import fp.api.ApiBarWaiter.api.repository.PostreRepository;
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
public class PostreController {

    private final PostreRepository postreRepository;
    private final StorageService storageService;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/postre")
    public ResponseEntity<?> obtenerPostres(){
        List<Postre> postreList = postreRepository.findAll();
        if (postreList.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else {
            return ResponseEntity.ok(postreList);
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/postre/{id}")
    public ResponseEntity<?> obtenerUnaPostre(@PathVariable Long id){
        if (postreRepository.existsById(id)){
            Postre postre = postreRepository.findById(id).orElse(null);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(postre);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "/postre",consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Postre> insertarPostre(@RequestPart("nuevo") Postre postre,
                                                 @RequestPart("file") MultipartFile file) {
        String urlImagen=null;
        if(!file.isEmpty()){
            String imagen=storageService.store(file);
            urlImagen = MvcUriComponentsBuilder
                    .fromMethodName(FicherosController.class,"serveFile",imagen,null)
                    .build().toUriString();
        }
        Postre postreGuadada = postre;
        postreGuadada.setImagen(urlImagen);
        postreRepository.save(postreGuadada);
        return ResponseEntity.status(HttpStatus.CREATED).body(postreGuadada);
    }

    @PutMapping("/postre/{id}")
    public ResponseEntity<?> editarPostre(@PathVariable Long id, @RequestBody Postre postre){
        return postreRepository.findById(id).map(t ->{
            t.setNombre(postre.getNombre());
            t.setDescripcion(postre.getDescripcion());
            t.setPrecio(postre.getPrecio());
            return  ResponseEntity.status(HttpStatus.CREATED).body(t);
        }).orElseGet(() -> {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        });
    }

    @DeleteMapping("/postre/{id}")
    public ResponseEntity<?> eliminarPostre(@PathVariable Long id){
        if (postreRepository.existsById(id)){
            Postre postre = postreRepository.findById(id).get();
            postreRepository.delete(postre);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
