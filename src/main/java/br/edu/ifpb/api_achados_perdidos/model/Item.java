package br.edu.ifpb.api_achados_perdidos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "itens")
@Getter
@Setter
@NoArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String titulo;

    @Column(length = 1000)
    private String descricao;

    @Column(length = 50)
    private String categoria;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoItem tipo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusItem status = StatusItem.ATIVO;

    private String local;

    private Double latitude;

    private Double longitude;

    private LocalDate data;

    @Column(name = "imagem_url")
    private String imagemUrl;

    @Column(length = 30)
    private String contato;

    @PrePersist
    void aoCriar() {
        if (data == null) data = LocalDate.now();
        if (status == null) status = StatusItem.ATIVO;
    }
}