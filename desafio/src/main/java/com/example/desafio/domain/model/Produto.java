package com.example.desafio.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private String codigo;
    @Column
    private String descricao;
    @Column
    @Enumerated(EnumType.STRING)
    private TipoProdutoEnum tipoProduto;
    @Column
    private BigDecimal valorFornecedor;
    @Column
    private Long quantidadeEstoque;
}
