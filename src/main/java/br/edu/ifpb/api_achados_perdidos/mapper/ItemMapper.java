package br.edu.ifpb.api_achados_perdidos.mapper;

import br.edu.ifpb.api_achados_perdidos.dto.ItemRequest;
import br.edu.ifpb.api_achados_perdidos.dto.ItemResponse;
import br.edu.ifpb.api_achados_perdidos.model.Item;

public class ItemMapper {

    public static ItemResponse toResponse(Item i) {
        return new ItemResponse(
                i.getId(), i.getTitulo(), i.getDescricao(), i.getCategoria(),
                i.getTipo(), i.getStatus(), i.getLocal(), i.getLatitude(),
                i.getLongitude(), i.getData(), i.getImagemUrl(), i.getContato()
        );
    }

    public static Item toEntity(ItemRequest r) {
        Item item = new Item();
        item.setTitulo(r.titulo());
        item.setDescricao(r.descricao());
        item.setCategoria(r.categoria());
        item.setTipo(r.tipo());
        item.setLocal(r.local());
        item.setLatitude(r.latitude());
        item.setLongitude(r.longitude());
        item.setData(r.data());
        item.setImagemUrl(r.imagemUrl());
        item.setContato(r.contato());
        return item;
    }
}
