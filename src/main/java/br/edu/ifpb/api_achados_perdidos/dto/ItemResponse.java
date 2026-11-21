package br.edu.ifpb.api_achados_perdidos.dto;

import br.edu.ifpb.api_achados_perdidos.model.StatusItem;
import br.edu.ifpb.api_achados_perdidos.model.TipoItem;

import java.time.LocalDate;

public record ItemResponse(
        Long id,
        String titulo,
        String descricao,
        String categoria,
        TipoItem tipo,
        StatusItem status,
        String local,
        Double latitude,
        Double longitude,
        LocalDate data,
        String imagemUrl,
        String contato
) {}