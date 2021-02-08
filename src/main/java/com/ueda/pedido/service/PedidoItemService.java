package com.ueda.pedido.service;

import com.ueda.pedido.model.Pedido;
import com.ueda.pedido.model.PedidoItem;
import com.ueda.pedido.model.Produto;
import com.ueda.pedido.repository.PedidoItemRepository;
import com.ueda.pedido.repository.PedidoRepository;
import com.ueda.pedido.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PedidoItemService {

    @Autowired
    PedidoItemRepository repository;

    @Autowired
    ProdutoRepository repositoryProduto;

    @Autowired
    PedidoRepository repositoryPedido;

    public void save(PedidoItem pedidoItem){
        Produto produto = repositoryProduto.getOne(pedidoItem.getProduto().getId());
        if (produto.getAtivo().equals("S")) {
            repository.save(pedidoItem);
        }
    }

    public void delete(UUID id) {
        repository.delete(getPedidoItemById(id));
    }

    public PedidoItem getPedidoItemById(UUID id) {
        Optional<PedidoItem> pedidoItem = repository.findById(id);
        return pedidoItem.get();
    }

    public PedidoItem update(UUID id, PedidoItem pedidoItem){
        try{
            Optional<PedidoItem> pedidoItemOptional = repository.findById(id);
            if (pedidoItemOptional.isPresent()) {
                pedidoItemOptional.get().setProduto(pedidoItem.getProduto());
                pedidoItemOptional.get().setValor(pedidoItem.getValor());
                return repository.save(pedidoItemOptional.get());
            }
        }catch (Exception e){
            return null;
        }
        return pedidoItem;
    }
}
