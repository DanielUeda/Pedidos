package com.ueda.pedido.controller;

import com.ueda.pedido.model.PedidoItem;
import com.ueda.pedido.service.PedidoItemService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/pedido-item")
@Api(value = "Pedido Item")
public class PedidoItemController {

    @Autowired
    PedidoItemService service;

    @PostMapping("")
    public ResponseEntity<?> post(@Valid @RequestBody PedidoItem pedidoItem){
        try{
            this.service.save(pedidoItem);
            return ResponseEntity.status(HttpStatus.CREATED).body("Item adicionado com sucesso!");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@Valid @PathVariable UUID id) {
        try{
            service.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("Item removido com sucesso!");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody @Valid PedidoItem pedidoItem) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.update(id, pedidoItem ));
        } catch ( Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
