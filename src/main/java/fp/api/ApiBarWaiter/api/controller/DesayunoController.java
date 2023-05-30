package fp.api.ApiBarWaiter.api.controller;


import fp.api.ApiBarWaiter.api.model.Desayuno;
import fp.api.ApiBarWaiter.api.repository.DesayunoRepository;
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
public class DesayunoController {

    private final DesayunoRepository desayunoRepository;
    private final StorageService storageService;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/desayuno")
    public ResponseEntity<?> obtenerDesayunos(){
        List<Desayuno> desayunoList = desayunoRepository.findAll();
        if (desayunoList.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else {
            return ResponseEntity.ok(desayunoList);
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/desayuno/{id}")
    public ResponseEntity<?> obtenerUnaDesayuno(@PathVariable Long id){
        if (desayunoRepository.existsById(id)){
            Desayuno desayuno = desayunoRepository.findById(id).orElse(null);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(desayuno);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "/desayuno",consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Desayuno> insertarDesayuno(@RequestPart("nuevo") Desayuno desayuno,
                                                     @RequestPart("file") MultipartFile file) {

        String urlImagen=null;
        if(!file.isEmpty()){
            String imagen=storageService.store(file);
            urlImagen = MvcUriComponentsBuilder
                    .fromMethodName(FicherosController.class,"serveFile",imagen,null)
                    .build().toUriString();
        }

        Desayuno desayunoGuadada = desayunoRepository.save(desayuno);
        return ResponseEntity.status(HttpStatus.CREATED).body(desayunoGuadada);
    }

    @PutMapping("/desayuno/{id}")
    public ResponseEntity<?> editarDesayuno(@PathVariable Long id, @RequestBody Desayuno desayuno){
        return desayunoRepository.findById(id).map(t ->{
            t.setNombre(desayuno.getNombre());
            t.setDescripcion(desayuno.getDescripcion());
            t.setPrecio(desayuno.getPrecio());
            return  ResponseEntity.status(HttpStatus.CREATED).body(t);
        }).orElseGet(() -> {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        });
    }

    @DeleteMapping("/desayuno/{id}")
    public ResponseEntity<?> eliminarDesayuno(@PathVariable Long id){
        if (desayunoRepository.existsById(id)){
            Desayuno desayuno = desayunoRepository.findById(id).get();
            desayunoRepository.delete(desayuno);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
