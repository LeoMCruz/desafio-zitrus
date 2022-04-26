package com.example.desafio.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovimentoEstoque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @JoinColumn(name = "produto_codigo")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Produto produto;
    @Column
    @Enumerated(EnumType.STRING)
    private TipoMovimentacaoEnum tipoMovimentacao;
    @Column
    private BigDecimal valorUnitario;
    @Column
    private LocalDateTime dataMovimentacao;
    @Column
    private Long quantidadeMovimentada;
    @Column
    private BigDecimal total;
    @Column
    private BigDecimal lucro;
}
