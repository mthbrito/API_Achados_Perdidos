package br.edu.ifpb.api_achados_perdidos.service;

import br.edu.ifpb.api_achados_perdidos.dto.GeocodeResponse;
import br.edu.ifpb.api_achados_perdidos.exception.RecursoNaoEncontradoException;
import br.edu.ifpb.api_achados_perdidos.exception.ServicoExternoException;
import br.edu.ifpb.api_achados_perdidos.mapper.GeocodeMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import tools.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;

@Service
public class NominatimService {

    private final RestClient client;

    public NominatimService() {
        this.client = RestClient.builder()
                .baseUrl("https://nominatim.openstreetmap.org")
                .defaultHeader(HttpHeaders.USER_AGENT,
                        "AchadosPerdidosApp/1.0 (trabalho academico IFPB)")
                .build();
    }

    public List<GeocodeResponse> buscarPorEndereco(String q) {
        JsonNode json = consultar(u -> u.path("/search")
                .queryParam("q", q)
                .queryParam("format", "jsonv2")
                .queryParam("limit", 5)
                .build());

        List<GeocodeResponse> lista = new ArrayList<>();
        if (json != null) {
            json.forEach(n -> lista.add(GeocodeMapper.toResponse(n)));
        }
        return lista;
    }

    public GeocodeResponse buscarPorCoordenadas(double lat, double lon) {
        JsonNode json = consultar(u -> u.path("/reverse")
                .queryParam("lat", lat)
                .queryParam("lon", lon)
                .queryParam("format", "jsonv2")
                .build());

        if (json == null || !json.has("display_name")) {
            throw new RecursoNaoEncontradoException(
                    "Endereço não encontrado para: " + lat + ", " + lon);
        }
        return GeocodeMapper.toResponse(json);
    }

    private JsonNode consultar(java.util.function.Function<
            org.springframework.web.util.UriBuilder, java.net.URI> uri) {
        try {
            return client.get().uri(uri).retrieve().body(JsonNode.class);
        } catch (RestClientException e) {
            throw new ServicoExternoException("Falha ao consultar o Nominatim", e);
        }
    }
}