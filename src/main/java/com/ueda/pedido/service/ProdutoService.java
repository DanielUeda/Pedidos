package com.ueda.pedido.service;

import com.ueda.pedido.model.Produto;
import com.ueda.pedido.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ProdutoService {

    @Autowired
    ProdutoRepository repository;

    public Page<Produto> search(String searchTerm, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "nome");

        return repository.search( searchTerm.toLowerCase(), pageRequest);
    }

    public Produto save(Produto produto){
        return repository.save(produto);
    }

    public void delete(UUID id) {

        repository.delete(getProdutoById(id).get());
    }

    public Optional<Produto> getProdutoById(UUID id) {
        Optional<Produto> produto = repository.findById(id);
        return produto;
    }

    public Produto update(UUID id, Produto produto){
        try{
            Optional<Produto> produtoOptional = repository.findById(id);
            if (produtoOptional.isPresent()) {
                produtoOptional.get().setAtivo(produto.getAtivo());
                produtoOptional.get().setDescricao(produto.getDescricao());
                produtoOptional.get().setTipo(produto.getTipo());
                return repository.save(produtoOptional.get());
            }
        }catch (Exception e){
            return null;
        }
        return produto;
    }
}
