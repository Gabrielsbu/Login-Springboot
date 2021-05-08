package com.jwt.udemy.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "item_pedido")
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pedidos_id")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "produtos_id")
    private Produto produto;

    @Column(name = "quantidade")
    private Integer quantidade;
}
