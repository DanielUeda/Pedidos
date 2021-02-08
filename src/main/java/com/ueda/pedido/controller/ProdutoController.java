package com.ueda.pedido.controller;

import com.ueda.pedido.model.Produto;
import com.ueda.pedido.service.ProdutoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @PostMapping("")
    public ResponseEntity<?> post(@Valid @RequestBody Produto produto){
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(produto));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> visualizar(@Valid @PathVariable UUID id) {
        try{
            Optional<Produto> produtoOptional = this.service.getProdutoById(id);
            if (produtoOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(produtoOptional.get());
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Produto não encontrado");
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/search")
    public Page<Produto> search(
            @RequestParam("searchTerm") String searchTerm,
            @RequestParam(
                    value = "page",
                    required = false,
                    defaultValue = "0") int page,
            @RequestParam(
                    value = "size",
                    required = false,
                    defaultValue = "10") int size) {
        return service.search(searchTerm, page, size);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@Valid @PathVariable UUID id) {
        try{
            service.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("Produto removido com sucesso!");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody @Valid Produto produto) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.update(id, produto ));
        } catch ( Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
