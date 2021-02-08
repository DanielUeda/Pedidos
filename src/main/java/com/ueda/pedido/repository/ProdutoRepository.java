package com.ueda.pedido.repository;


import com.ueda.pedido.model.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, UUID> {

    @Query("FROM Produto p " +
            "WHERE LOWER(p.descricao) like %:searchTerm% ")
    Page<Produto> search(
            @Param("searchTerm") String searchTerm,
            Pageable pageable);

}
