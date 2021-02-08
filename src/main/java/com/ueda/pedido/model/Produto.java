package com.ueda.pedido.model;

import com.ueda.pedido.model.enumeration.TipoProduto;
import com.ueda.pedido.model.enumeration.converter.TipoProdutoConverter;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Produto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name = "descricao", length = 500)
    @NotNull(message = "A descrição não pode ser nula")
    private String descricao;

    @Convert(converter = TipoProdutoConverter.class)
    @Column(name = "tipo")
    @NotNull(message = "O tipo não pode ser nulo")
    private TipoProduto tipo;

    @Column(name = "ativo", length = 1)
    @NotNull(message = "O campo ativo não pode ser nulo")
    private String ativo;


}
