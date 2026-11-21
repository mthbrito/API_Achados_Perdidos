package br.edu.ifpb.api_achados_perdidos.dto;

import br.edu.ifpb.api_achados_perdidos.model.TipoItem;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record ItemRequest(
        @NotBlank @Size(max = 100) String titulo,
        @Size(max = 1000) String descricao,
        @Size(max = 50) String categoria,
        @NotNull TipoItem tipo,
        @Size(max = 255) String local,
        Double latitude,
        Double longitude,
        LocalDate data,
        String imagemUrl,
        @Size(max = 30) String contato
) {}
