package com.ueda.pedido;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ueda.pedido.model.Pedido;
import com.ueda.pedido.model.Produto;
import com.ueda.pedido.model.enumeration.SituacaoPedido;
import com.ueda.pedido.model.enumeration.TipoProduto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PedidoTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void InserindoPedido() throws Exception {
        Pedido pedido = new Pedido();
        pedido.setDescricao("Pedido Teste");
        pedido.setSituacaoPedido(SituacaoPedido.ABERTO);

        mockMvc.perform(post("/pedido")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(pedido)))
                .andExpect(status().isCreated());
    }

    @Test
    void InserindoPedidoSemDescricao() throws Exception {
        Pedido pedido = new Pedido();
        pedido.setSituacaoPedido(SituacaoPedido.ABERTO);

        mockMvc.perform(post("/pedido")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(pedido)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void InserindoProdutoSemSituacao() throws Exception {
        Pedido pedido = new Pedido();
        pedido.setDescricao("Pedido Teste");

        mockMvc.perform(post("/pedido")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(pedido)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void ConsultandoPedidoNaoExiste() throws Exception {
        mockMvc.perform(get("/produto/dbaa7213-489d-47f9-96cd-81ee7474ad76"))
                .andExpect(status().isNoContent());
    }

}
