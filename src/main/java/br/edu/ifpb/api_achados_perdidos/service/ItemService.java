package br.edu.ifpb.api_achados_perdidos.service;

import br.edu.ifpb.api_achados_perdidos.dto.ItemRequest;
import br.edu.ifpb.api_achados_perdidos.dto.ItemResponse;
import br.edu.ifpb.api_achados_perdidos.exception.RecursoNaoEncontradoException;
import br.edu.ifpb.api_achados_perdidos.mapper.ItemMapper;
import br.edu.ifpb.api_achados_perdidos.model.Item;
import br.edu.ifpb.api_achados_perdidos.model.StatusItem;
import br.edu.ifpb.api_achados_perdidos.model.TipoItem;
import br.edu.ifpb.api_achados_perdidos.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository repository;

    @Transactional(readOnly = true)
    public List<ItemResponse> listar(TipoItem tipo, String categoria, String q) {
        String categoriaFiltro = vazioParaNulo(categoria);
        String busca = vazioParaNulo(q);
        return repository.filtrar(tipo, categoriaFiltro, busca)
                .stream()
                .map(ItemMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ItemResponse buscar(Long id) {
        return ItemMapper.toResponse(buscarEntidade(id));
    }

    @Transactional
    public ItemResponse criar(ItemRequest request) {
        Item item = ItemMapper.toEntity(request);
        return ItemMapper.toResponse(repository.save(item));
    }

    @Transactional
    public ItemResponse atualizar(Long id, ItemRequest request) {
        Item existente = buscarEntidade(id);
        Item novo = ItemMapper.toEntity(request);

        novo.setId(existente.getId());
        novo.setStatus(existente.getStatus());
        if (novo.getData() == null) {
            novo.setData(existente.getData());
        }

        return ItemMapper.toResponse(repository.save(novo));
    }

    @Transactional
    public ItemResponse marcarDevolvido(Long id) {
        Item item = buscarEntidade(id);
        item.setStatus(StatusItem.DEVOLVIDO);
        return ItemMapper.toResponse(repository.save(item));
    }

    @Transactional
    public void excluir(Long id) {
        Item item = buscarEntidade(id);
        repository.delete(item);
    }

    private Item buscarEntidade(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Item não encontrado: " + id));
    }

    private String vazioParaNulo(String s) {
        return (s == null || s.isBlank()) ? null : s.trim();
    }
}
