package com.example.desafio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class VendasEstoqueDTO {
    private final String codigo;
    private final Long quantidadeSaida;
    private final Long quantidadeDisponivel;
}
