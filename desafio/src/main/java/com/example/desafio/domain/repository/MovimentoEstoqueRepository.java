package com.example.desafio.domain.repository;

import com.example.desafio.domain.model.MovimentoEstoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Optional;

public interface MovimentoEstoqueRepository extends JpaRepository<MovimentoEstoque, Long> {
    @Query("SELECT sum(me.quantidadeMovimentada) FROM MovimentoEstoque me " +
            "WHERE me.produto.codigo = :codigo" +
            " AND me.tipoMovimentacao = com.example.desafio.domain.model.TipoMovimentacaoEnum.SAIDA")
    Optional<Long> quantidadeVendida(String codigo);

    @Query("SELECT sum(me.lucro) FROM MovimentoEstoque me " +
            "WHERE me.produto.codigo = :codigo" +
            " AND me.tipoMovimentacao = com.example.desafio.domain.model.TipoMovimentacaoEnum.SAIDA")
    Optional<BigDecimal> totalVendaProduto(String codigo);
}
