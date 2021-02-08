package com.ueda.pedido.controller;

import com.ueda.pedido.model.Pedido;
import com.ueda.pedido.service.PedidoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

    @Autowired
    PedidoService service;

    @PostMapping("")
    public ResponseEntity<?> post(@Valid @RequestBody Pedido pedido){
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(pedido));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> visualizar(@Valid @PathVariable UUID id) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(this.service.getPedidoById(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/search")
    public Page<Pedido> search(
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
            return ResponseEntity.status(HttpStatus.OK).body("Pedido removido com sucesso!");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody @Valid Pedido pedido) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.update(id, pedido ));
        } catch ( Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/desconto")
    public ResponseEntity<?> gerarDesconto(@PathVariable UUID id, @RequestBody @Valid Pedido pedido) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.aplicarDesconto(id, pedido.getPercentualDesconto() ));
        } catch ( Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping(value = "{pedidoId}/item/{item}/add")
    public ResponseEntity<?> addItem(@PathVariable final UUID pedidoId, @PathVariable final UUID item){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.addItem(pedidoId, item));
        } catch ( Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping(value = "{pedidoId}/item/{item}/delete")
    public ResponseEntity<?> deleteItem(@PathVariable final UUID pedidoId, @PathVariable final UUID item){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.deleteItem(pedidoId, item));
        } catch ( Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
