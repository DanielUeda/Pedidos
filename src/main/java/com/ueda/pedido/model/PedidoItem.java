package com.ueda.pedido.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class PedidoItem implements Serializable {


    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @ManyToOne()
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @Column(name = "valor")
    private Double valor;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pedido_id")
    @JsonIgnore
    private Pedido pedido;
}
