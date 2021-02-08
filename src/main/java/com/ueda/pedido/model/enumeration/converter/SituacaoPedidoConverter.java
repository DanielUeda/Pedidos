package com.ueda.pedido.model.enumeration.converter;


import com.ueda.pedido.model.enumeration.SituacaoPedido;
import com.ueda.pedido.model.enumeration.TipoProduto;

import javax.persistence.AttributeConverter;

public class SituacaoPedidoConverter implements AttributeConverter<SituacaoPedido, String> {

    @Override
    public String convertToDatabaseColumn(SituacaoPedido situacaoPedido) {
        switch (situacaoPedido) {
            case ABERTO:
                return "A";
            case FECHADO:
                return "F";
            default:
                throw new IllegalArgumentException("Unknown" + situacaoPedido);
        }
    }

    @Override
    public SituacaoPedido convertToEntityAttribute(String situacaoPedido) {
        switch (situacaoPedido) {
            case "A":
                return SituacaoPedido.ABERTO;
            case "F":
                return SituacaoPedido.FECHADO;
            default:
                throw new IllegalArgumentException("Unknown" + situacaoPedido);
        }
    }

}