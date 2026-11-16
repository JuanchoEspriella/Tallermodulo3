package co.edu.unbosque.tallersicseven.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.tallersicseven.dto.CountriesDTO;
import co.edu.unbosque.tallersicseven.service.CountriesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/countries")
@Tag(name = "Países", description = "Consultas de países mediante countries.dev")
@SecurityRequirement(name = "bearerAuth")
public class CountriesController {

    private final CountriesService countriesService;

    public CountriesController(CountriesService countriesService) {
        this.countriesService = countriesService;
    }

    @Operation(summary = "Buscar país por nombre", description = "Busca países por nombre completo o parcial.")
    @GetMapping("/buscar")
    public ResponseEntity<?> buscarPorNombre(@RequestParam String nombre) {
        if (nombre == null || nombre.isBlank()) {
            return ResponseEntity.badRequest().body("Debes ingresar un país.");
        }

        return responder(countriesService.buscarPorNombre(nombre));
    }

    @Operation(summary = "Buscar país por código", description = "Busca por código ISO alpha-2 o alpha-3.")
    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<?> buscarPorCodigo(@PathVariable String codigo) {
        if (codigo == null || codigo.isBlank()) {
            return ResponseEntity.badRequest().body("Debes ingresar un código de país.");
        }

        return responder(countriesService.buscarPorCodigo(codigo));
    }

    @Operation(summary = "Buscar países por región", description = "Consulta países de una región, por ejemplo Americas, Europe o Asia.")
    @GetMapping("/region/{region}")
    public ResponseEntity<?> buscarPorRegion(@PathVariable String region) {
        if (region == null || region.isBlank()) {
            return ResponseEntity.badRequest().body("Debes ingresar una región.");
        }

        return responder(countriesService.buscarPorRegion(region));
    }

    private ResponseEntity<?> responder(List<CountriesDTO> paises) {
        if (paises == null) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body("No fue posible consultar el servicio externo de países.");
        }

        if (paises.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontraron países.");
        }

        return ResponseEntity.ok(paises);
    }
}
