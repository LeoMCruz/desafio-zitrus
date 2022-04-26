package com.example.desafio.dto;

import com.example.desafio.domain.model.TipoProdutoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO {
    private Long id;
    private String codigo;
    private String descricao;
    private TipoProdutoEnum tipoProduto;
    private BigDecimal valorFornecedor;
    private Long quantidadeEstoque;
}
