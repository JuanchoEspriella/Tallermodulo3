package co.edu.unbosque.tallersicseven.service;

import java.util.List;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import co.edu.unbosque.tallersicseven.dto.CountriesDTO;

@Service
public class CountriesService {

    @Value("${paises.url}")
    private String baseUrl;

    public CountriesService() {
        super();
    }

    public List<CountriesDTO> buscarPorNombre(String nombre) {

        String pais = URLEncoder.encode(nombre, StandardCharsets.UTF_8);

        String urlCompleta = baseUrl + "name/" + pais;

        return ExternalHTTPRequestHandler.doGetAndConvertToDtoListCountries(urlCompleta);
    }

    public List<CountriesDTO> buscarPorCodigo(String codigo) {

        String urlCompleta = baseUrl + "alpha/" + codigo;

        return ExternalHTTPRequestHandler.doGetAndConvertToDtoListCountries(urlCompleta);
    }

    public List<CountriesDTO> buscarPorRegion(String region) {

        String urlCompleta = baseUrl + "region/" + region;

        return ExternalHTTPRequestHandler.doGetAndConvertToDtoListCountries(urlCompleta);
    }
}