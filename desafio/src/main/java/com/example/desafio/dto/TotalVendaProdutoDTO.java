package com.example.desafio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
public class TotalVendaProdutoDTO {
    private final String codigo;
    private final Long quantidadeSaida;
    private final BigDecimal lucro;
}
