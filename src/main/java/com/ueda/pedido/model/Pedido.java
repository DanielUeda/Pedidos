package com.ueda.pedido.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ueda.pedido.model.enumeration.SituacaoPedido;
import com.ueda.pedido.model.enumeration.converter.SituacaoPedidoConverter;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DefaultValue;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Pedido implements Serializable {


    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Convert(converter = SituacaoPedidoConverter.class)
    @Column(name = "situacao_pedido")
    @NotNull(message = "A situação do pedido não pode ser nula")
    private SituacaoPedido situacaoPedido;

    @Column(name = "percentual_desconto")
    @Max(message = "O valor máximo de desconto é de 100%", value = 100)
    @Min(message = "O valor de desconto não pode ser menor que 0", value = 0)
    private Double percentualDesconto;

    @Column(name = "valor_total",  columnDefinition = "float8 default '0.0'")
    private Double valorTotal;

    @Column(name = "valor_produtos",  columnDefinition = "float8 default '0.0'")
    private Double valorProdutos;

    @Column(name = "valor_servicos",  columnDefinition = "float8 default '0.0'")
    private Double valorServicos;

    @Column(name = "descricao", length = 500)
    @NotNull(message = "A descrição não pode ser nula")
    private String descricao;

//    @OneToMany (fetch = FetchType.LAZY,
//            orphanRemoval = true,
//            cascade = CascadeType.ALL)
//    @JoinColumn(name = "pedido_id", nullable = true)
    @OneToMany(
            mappedBy = "pedido",
            cascade = CascadeType.ALL
    )
    private List<PedidoItem> items = new ArrayList<>();

    public void addItem(PedidoItem item){
        items.add(item);
    }

    public void removeItem(PedidoItem item){
        items.remove(item);
    }
}
