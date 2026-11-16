package co.edu.unbosque.tallersicseven.service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import co.edu.unbosque.tallersicseven.dto.CountriesDTO;

@Service
public class CountriesService {

    @Value("${paises.url:https://countries.dev/}")
    private String baseUrl;

    public List<CountriesDTO> buscarPorNombre(String nombre) {
        return consultar("name", nombre);
    }

    public List<CountriesDTO> buscarPorCodigo(String codigo) {
        return consultar("alpha", codigo);
    }

    public List<CountriesDTO> buscarPorRegion(String region) {
        return consultar("region", region);
    }

    private List<CountriesDTO> consultar(String recurso, String valor) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("El parámetro de búsqueda no puede estar vacío.");
        }

        String base = baseUrl.endsWith("/") ? baseUrl : baseUrl + "/";
        String valorCodificado = URLEncoder.encode(valor.trim(), StandardCharsets.UTF_8)
                .replace("+", "%20");
        String urlCompleta = base + recurso + "/" + valorCodificado;

        return ExternalHTTPRequestHandler.doGetAndConvertToDtoListCountries(urlCompleta);
    }
}
