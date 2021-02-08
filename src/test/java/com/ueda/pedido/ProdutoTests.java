package com.ueda.pedido;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ueda.pedido.model.Produto;
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
public class ProdutoTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void InserindoProduto() throws Exception {
        Produto produto = new Produto();
        produto.setDescricao("Produto Teste");
        produto.setTipo(TipoProduto.PRODUTO);
        produto.setAtivo("S");

        mockMvc.perform(post("/produto")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(produto)))
                .andExpect(status().isCreated());
    }

    @Test
    void InserindoProdutoSemDescricao() throws Exception {
        Produto produto = new Produto();
        produto.setTipo(TipoProduto.PRODUTO);
        produto.setAtivo("S");

        mockMvc.perform(post("/produto")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(produto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void InserindoProdutoSemTipo() throws Exception {
        Produto produto = new Produto();
        produto.setDescricao("Produto Teste");
        produto.setAtivo("S");

        mockMvc.perform(post("/produto")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(produto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void ConsultandoProdutoNaoExiste() throws Exception {

        mockMvc.perform(get("/produto/dbaa7213-489d-47f9-96cd-81ee7474ad76"))
                .andExpect(status().isNoContent());
    }

}
