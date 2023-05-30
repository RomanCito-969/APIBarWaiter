package fp.api.ApiBarWaiter.api.controller;

import fp.api.ApiBarWaiter.api.model.Tapa;
import fp.api.ApiBarWaiter.api.repository.TapaRepository;
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
public class TapaController {

    private final TapaRepository tapaRepository;
    private final StorageService storageService;

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
            Tapa tapa = tapaRepository.findById(id).orElse(null);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(tapa);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "/tapa" ,consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Tapa> insertarTapa(@RequestPart("nuevo")Tapa tapa,
                                             @RequestPart("file") MultipartFile file) {
        String urlImagen=null;
        if(!file.isEmpty()){
            String imagen=storageService.store(file);
            urlImagen = MvcUriComponentsBuilder
                    .fromMethodName(FicherosController.class,"serveFile",imagen,null)
                    .build().toUriString();
        }
        Tapa tapaGuadada = tapa;
        tapaGuadada.setImagen(urlImagen);
        tapaRepository.save(tapaGuadada);
        return ResponseEntity.status(HttpStatus.CREATED).body(tapaGuadada);
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
