package com.example.desafio.dto;

import com.example.desafio.domain.model.TipoMovimentacaoEnum;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovimentoEstoqueDTO {
    private Long id;
    private String codigo;
    @NotNull
    private TipoMovimentacaoEnum tipoMovimentacao;
    private BigDecimal valorUnitario;
    private LocalDateTime dataMovimentacao;
    private Long quantidadeMovimentada;
    private BigDecimal total;
    private BigDecimal lucro;
}
