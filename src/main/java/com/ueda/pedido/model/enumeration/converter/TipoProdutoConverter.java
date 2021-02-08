package com.ueda.pedido.model.enumeration.converter;


import com.ueda.pedido.model.enumeration.TipoProduto;

import javax.persistence.AttributeConverter;

public class TipoProdutoConverter implements AttributeConverter<TipoProduto, String> {

    @Override
    public String convertToDatabaseColumn(TipoProduto tipoProduto) {
        switch (tipoProduto) {
            case SERVICO:
                return "S";
            case PRODUTO:
                return "P";
            default:
                throw new IllegalArgumentException("Unknown" + tipoProduto);
        }
    }

    @Override
    public TipoProduto convertToEntityAttribute(String tipoProduto) {
        switch (tipoProduto) {
            case "S":
                return TipoProduto.SERVICO;
            case "P":
                return TipoProduto.PRODUTO;
            default:
                throw new IllegalArgumentException("Unknown" + tipoProduto);
        }
    }

}