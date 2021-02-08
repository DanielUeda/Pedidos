package com.ueda.pedido.service;

import com.ueda.pedido.model.Pedido;
import com.ueda.pedido.model.PedidoItem;
import com.ueda.pedido.model.Produto;
import com.ueda.pedido.model.enumeration.SituacaoPedido;
import com.ueda.pedido.model.enumeration.TipoProduto;
import com.ueda.pedido.repository.PedidoItemRepository;
import com.ueda.pedido.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.event.PaintEvent;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

@Service
public class PedidoService {

    @Autowired
    PedidoRepository repository;

    @Autowired
    PedidoItemRepository repositoryPedidoItem;

    public Pedido save(Pedido pedido){
        if (pedido.getValorProdutos() == null) {
            pedido.setValorProdutos(0.0);
        }
        if (pedido.getValorServicos() == null) {
            pedido.setValorServicos(0.0);
        }
        if (pedido.getValorTotal() == null) {
            pedido.setValorTotal(0.0);
        }
        if (pedido.getPercentualDesconto() == null) {
            pedido.setPercentualDesconto(0.0);
        }
        return repository.save(pedido);
    }

    public void delete(UUID id) {
        repository.delete(getPedidoById(id));
    }

    public Pedido getPedidoById(UUID id) {
        Optional<Pedido> pedido = repository.findById(id);
        return pedido.get();
    }

    public Pedido update(UUID id, Pedido pedido){
        try{
            Optional<Pedido> pedidoOptional = repository.findById(id);
            if (pedidoOptional.isPresent()) {
                pedidoOptional.get().setSituacaoPedido(pedido.getSituacaoPedido());
                pedidoOptional.get().setDescricao(pedido.getDescricao());
                return repository.save(pedidoOptional.get());
            }
        }catch (Exception e){
            return null;
        }
        return pedido;
    }

    public Page<Pedido> search(String searchTerm, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "nome");

        return repository.search( searchTerm.toLowerCase(), pageRequest);
    }

    public String aplicarDesconto(UUID id, Double desconto) {
        Pedido pedido = repository.getOne(id);
        if (pedido.getSituacaoPedido() == SituacaoPedido.ABERTO) {
            double vlrDesconto = 0.0;
            for ( PedidoItem pedidoItem: pedido.getItems()) {
                if (pedidoItem.getProduto().getTipo() == TipoProduto.PRODUTO) {
                    vlrDesconto += (pedidoItem.getValor() * desconto) / 100;
                }
            }
            double vlrComDesconto = pedido.getValorProdutos() - vlrDesconto;
            pedido.setValorProdutos(vlrComDesconto);
            pedido.setValorTotal(vlrComDesconto + pedido.getValorServicos());
            pedido.setPercentualDesconto(desconto);
            repository.save(pedido);
            return "Desconto de R$" + vlrDesconto + " aplicado";
        }
        return "Desconto não aplicado";
    }

    public Serializable addItem(UUID pedidoId, UUID itemId) {
        try{
            Pedido pedido = repository.findById(pedidoId).get();
            PedidoItem item = repositoryPedidoItem.findById(itemId).get();
            pedido.addItem(item);
            if (item.getProduto().getTipo() == TipoProduto.PRODUTO){
                pedido.setValorProdutos(pedido.getValorProdutos() + item.getValor());
            } else {
                pedido.setValorServicos(pedido.getValorServicos() + item.getValor());
            }
            pedido.setValorTotal(pedido.getValorTotal() + item.getValor());

            item.setPedido(pedido);
            repositoryPedidoItem.save(item);
            return repository.save(pedido);
        }catch (Exception e){
            return e.getMessage();
        }
    }

    public Serializable deleteItem(UUID pedidoId, UUID itemId) {
        try{
            Pedido pedido = repository.findById(pedidoId).get();
            PedidoItem item = repositoryPedidoItem.findById(itemId).get();
            pedido.removeItem(item);
            if (item.getProduto().getTipo() == TipoProduto.PRODUTO){
                pedido.setValorProdutos(pedido.getValorProdutos() - item.getValor());
            } else {
                pedido.setValorServicos(pedido.getValorServicos() - item.getValor());
            }

            pedido.setValorTotal(pedido.getValorTotal() - item.getValor());
            repositoryPedidoItem.delete(item);
            return  repository.save(pedido);
        }catch (Exception e){
            return e.getMessage();
        }
    }
}
