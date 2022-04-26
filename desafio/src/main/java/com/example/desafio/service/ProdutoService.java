package com.example.desafio.service;

import com.example.desafio.domain.model.Produto;
import com.example.desafio.domain.repository.ProdutoRepository;
import com.example.desafio.dto.ProdutoDTO;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProdutoService {
    private final ProdutoRepository produtoRepository;

    @Transactional
    public ProdutoDTO salvarProduto(ProdutoDTO produtoDTO) throws Exception {
        var produto = Produto.builder()
                .id(produtoDTO.getId())
                .codigo(produtoDTO.getCodigo())
                .descricao(produtoDTO.getDescricao())
                .tipoProduto(produtoDTO.getTipoProduto())
                .valorFornecedor(produtoDTO.getValorFornecedor())
                .quantidadeEstoque(produtoDTO.getQuantidadeEstoque())
                .build();
        produtoRepository.save(produto);
        return produtoDTO;
    }

    @Transactional
    public List<ProdutoDTO> buscarTodosProdutos(Pageable pageable) {
        return produtoRepository.findAll(pageable).stream()
                .map(produto -> ProdutoDTO.builder()
                        .id(produto.getId())
                        .codigo(produto.getCodigo())
                        .descricao(produto.getDescricao())
                        .tipoProduto(produto.getTipoProduto())
                        .valorFornecedor(produto.getValorFornecedor())
                        .quantidadeEstoque(produto.getQuantidadeEstoque())
                        .build()).collect(Collectors.toList());
    }

    @SneakyThrows
    public Produto get(String codigo) {
        return produtoRepository.findByCodigo(codigo).orElseThrow(()
                -> new Exception("Codigo não encontrado"));
    }

    @SneakyThrows
    public void deleteCodigo(String codigo) {
        produtoRepository.deleteByCodigo(codigo);
    }
}