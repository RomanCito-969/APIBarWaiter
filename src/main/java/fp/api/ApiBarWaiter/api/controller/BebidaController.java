package fp.api.ApiBarWaiter.api.controller;

import fp.api.ApiBarWaiter.api.error.ProductoNotFoundException;
import fp.api.ApiBarWaiter.api.model.Bebida;
import fp.api.ApiBarWaiter.api.repository.BebidaRepository;
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

public class BebidaController {

    private final BebidaRepository bebidaRepository;
    private final StorageService storageService;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/bebida")
    public ResponseEntity<?> obtenerBebidas(){
        List<Bebida> bebidaList = bebidaRepository.findAll();
        if (bebidaList.isEmpty()){
            throw new ProductoNotFoundException();
        }else {
            return ResponseEntity.ok(bebidaList);
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/bebida/{id}")
    public ResponseEntity<?> obtenerUnaBebida(@PathVariable Long id){
        if (bebidaRepository.existsById(id)){
            Bebida bebida = bebidaRepository.findById(id).orElse(null);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(bebida);
        }else {
            throw new ProductoNotFoundException(id);
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "/bebida", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Bebida> nuevaBebida(@RequestPart("nuevo") Bebida bebida,
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
        Bebida bebidaGuadada = bebida;
        bebidaGuadada.setImagen(urlImagen);
        bebidaRepository.save(bebidaGuadada);

        return ResponseEntity.status(HttpStatus.CREATED).body(bebidaGuadada);
    }

    @PutMapping("/bebida/{id}")
    public ResponseEntity<?> editarBebida(@PathVariable Long id, @RequestBody Bebida bebida){
        return bebidaRepository.findById(id).map(t ->{
            t.setNombre(bebida.getNombre());
            t.setDescripcion(bebida.getDescripcion());
            t.setPrecio(bebida.getPrecio());
            return  ResponseEntity.status(HttpStatus.CREATED).body(t);
        }).orElseThrow(() -> new ProductoNotFoundException(id));
    }

    @DeleteMapping("/bebida/{id}")
    public ResponseEntity<?> eliminarBebida(@PathVariable Long id){
        if (bebidaRepository.existsById(id)){
            Bebida bebida = bebidaRepository.findById(id).get();
            bebidaRepository.delete(bebida);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }else {
            throw new ProductoNotFoundException(id);
        }
    }
}
