package br.edu.ifpb.api_achados_perdidos.controller;

import br.edu.ifpb.api_achados_perdidos.dto.UploadResponse;
import br.edu.ifpb.api_achados_perdidos.service.ArmazenamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

@RestController
@RequestMapping("/upload")
@RequiredArgsConstructor
public class UploadController {

    private final ArmazenamentoService service;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UploadResponse> enviar(@RequestParam("imagem") MultipartFile imagem) {
        UploadResponse criado = service.salvar(imagem);
        return ResponseEntity.created(URI.create(criado.url())).body(criado);
    }
}
