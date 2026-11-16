
package co.edu.unbosque.tallersicseven.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.tallersicseven.dto.CountriesDTO;
import co.edu.unbosque.tallersicseven.service.CountriesService;

@RestController
@RequestMapping("/countries")
public class CountriesController {

    @Autowired
    private CountriesService countriesService;

    public CountriesController() {
        super();
    }

    @GetMapping("/buscar")
    public ResponseEntity<?> buscarPorNombre(
            @RequestParam String nombre) {

        if (nombre.isBlank()) {
            return new ResponseEntity<>(
                    "Debes ingresar un país",
                    HttpStatus.BAD_REQUEST);
        }

        List<CountriesDTO> paises =
                countriesService.buscarPorNombre(nombre);

        return responder(paises);
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<?> buscarPorCodigo(
            @PathVariable String codigo) {

        List<CountriesDTO> paises =
                countriesService.buscarPorCodigo(codigo);

        return responder(paises);
    }

    @GetMapping("/region/{region}")
    public ResponseEntity<?> buscarPorRegion(
            @PathVariable String region) {

        List<CountriesDTO> paises =
                countriesService.buscarPorRegion(region);

        return responder(paises);
    }

    private ResponseEntity<?> responder(
            List<CountriesDTO> paises) {

        if (paises == null) {
            return new ResponseEntity<>(
                    "Error al consultar la API",
                    HttpStatus.BAD_GATEWAY);
        }

        if (paises.isEmpty()) {
            return new ResponseEntity<>(
                    "No se encontraron países",
                    HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(
                paises, HttpStatus.OK);
    }
}
