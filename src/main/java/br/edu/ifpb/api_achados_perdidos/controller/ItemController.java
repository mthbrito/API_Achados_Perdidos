package br.edu.ifpb.api_achados_perdidos.controller;

import br.edu.ifpb.api_achados_perdidos.dto.ItemRequest;
import br.edu.ifpb.api_achados_perdidos.dto.ItemResponse;
import br.edu.ifpb.api_achados_perdidos.model.TipoItem;
import br.edu.ifpb.api_achados_perdidos.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/itens")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService service;

    @GetMapping
    public ResponseEntity<List<ItemResponse>> listar(
            @RequestParam(required = false) TipoItem tipo,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) String q) {
        return ResponseEntity.ok(service.listar(tipo, categoria, q));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemResponse> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscar(id));
    }

    @PostMapping
    public ResponseEntity<ItemResponse> criar(@Valid @RequestBody ItemRequest request) {
        ItemResponse criado = service.criar(request);
        return ResponseEntity.created(URI.create("/itens/" + criado.id())).body(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemResponse> atualizar(@PathVariable Long id,
                                                  @Valid @RequestBody ItemRequest request) {
        return ResponseEntity.ok(service.atualizar(id, request));
    }

    @PatchMapping("/{id}/devolvido")
    public ResponseEntity<ItemResponse> marcarDevolvido(@PathVariable Long id) {
        return ResponseEntity.ok(service.marcarDevolvido(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
