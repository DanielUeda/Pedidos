package com.ueda.pedido.repository;


import com.ueda.pedido.model.Pedido;
import com.ueda.pedido.model.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, UUID> {

    @Query("FROM Pedido p " +
            "WHERE LOWER(p.descricao) like %:searchTerm% ")
    Page<Pedido> search(
            @Param("searchTerm") String searchTerm,
            Pageable pageable);
}
