package br.edu.ifpb.api_achados_perdidos.controller;

import br.edu.ifpb.api_achados_perdidos.dto.GeocodeResponse;
import br.edu.ifpb.api_achados_perdidos.service.NominatimService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/geocode")
@RequiredArgsConstructor
public class GeocodeController {

    private final NominatimService service;

    @GetMapping("/endereco")
    public ResponseEntity<List<GeocodeResponse>> buscarPorEndereco(@RequestParam String q) {
        return ResponseEntity.ok(service.buscarPorEndereco(q));
    }

    @GetMapping("/coordenadas")
    public ResponseEntity<GeocodeResponse> buscarPorCoordenadas(@RequestParam double lat,
                                                                @RequestParam double lon) {
        return ResponseEntity.ok(service.buscarPorCoordenadas(lat, lon));
    }
}