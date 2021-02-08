package com.ueda.pedido.repository;


import com.ueda.pedido.model.PedidoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PedidoItemRepository extends JpaRepository<PedidoItem, UUID> {
}
