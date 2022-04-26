package com.example.desafio.domain.repository;

import com.example.desafio.domain.model.Produto;
import com.example.desafio.domain.model.TipoProdutoEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findAllByTipoProduto(TipoProdutoEnum tipoProduto);
    Optional<Produto> deleteByCodigo(String codigo);
    Optional<Produto> findByCodigo(String codigo);
}
