package br.edu.ifpb.api_achados_perdidos.mapper;

import br.edu.ifpb.api_achados_perdidos.dto.GeocodeResponse;
import com.fasterxml.jackson.databind.JsonNode;

public class GeocodeMapper {

    private GeocodeMapper() {
    }

    public static GeocodeResponse toResponse(JsonNode n) {
        return new GeocodeResponse(
                n.get("display_name").asText(),
                n.get("lat").asDouble(),
                n.get("lon").asDouble()
        );
    }
}