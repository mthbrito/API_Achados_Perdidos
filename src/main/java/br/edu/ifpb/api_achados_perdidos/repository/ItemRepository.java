package br.edu.ifpb.api_achados_perdidos.repository;

import br.edu.ifpb.api_achados_perdidos.model.Item;
import br.edu.ifpb.api_achados_perdidos.model.TipoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("""
            SELECT i FROM Item i
            WHERE (:tipo IS NULL OR i.tipo = :tipo)
              AND (:categoria IS NULL OR LOWER(i.categoria) = LOWER(:categoria))
              AND (:q IS NULL OR LOWER(i.titulo) LIKE LOWER(CONCAT('%', :q, '%'))
                              OR LOWER(i.descricao) LIKE LOWER(CONCAT('%', :q, '%')))
            ORDER BY i.data DESC, i.id DESC
            """)
    List<Item> filtrar(@Param("tipo") TipoItem tipo,
                       @Param("categoria") String categoria,
                       @Param("q") String q);
}