package com.example.desafio.service;

import com.example.desafio.config.exception.AppException;
import com.example.desafio.domain.model.MovimentoEstoque;
import com.example.desafio.domain.model.TipoMovimentacaoEnum;
import com.example.desafio.domain.model.TipoProdutoEnum;
import com.example.desafio.domain.repository.MovimentoEstoqueRepository;
import com.example.desafio.domain.repository.ProdutoRepository;
import com.example.desafio.dto.MovimentoEstoqueDTO;
import com.example.desafio.dto.TotalVendaProdutoDTO;
import com.example.desafio.dto.VendasEstoqueDTO;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovimentoEstoqueService {
    private final ProdutoService produtoService;
    private final ProdutoRepository produtoRepository;
    private final MovimentoEstoqueRepository movimentoEstoqueRepository;

    public List<VendasEstoqueDTO> quantidadeVendidaTipo(TipoProdutoEnum tipoProduto){
        return produtoRepository.findAllByTipoProduto(tipoProduto).stream()
                        .map(cadaProduto -> VendasEstoqueDTO.builder()
                                .codigo(cadaProduto.getCodigo())
                                .quantidadeSaida(movimentoEstoqueRepository
                                        .quantidadeVendida(cadaProduto.getCodigo()).orElse(0L))
                                .quantidadeDisponivel(cadaProduto.getQuantidadeEstoque())
                                .build()).collect(Collectors.toList());
    }

    public List<TotalVendaProdutoDTO> totalVendaProduto(String codigo){
        return produtoRepository.findByCodigo(codigo).stream()
                .map(cadaProduto -> TotalVendaProdutoDTO.builder()
                        .codigo(cadaProduto.getCodigo())
                        .quantidadeSaida(movimentoEstoqueRepository
                                .quantidadeVendida(cadaProduto.getCodigo()).orElse(0L))
                        .lucro(movimentoEstoqueRepository
                                .totalVendaProduto(cadaProduto.getCodigo()).orElse(BigDecimal.ZERO))
                        .build()).collect(Collectors.toList());
    }

    private void entradaProduto(MovimentoEstoque movimentoEstoque){
        var produto = movimentoEstoque.getProduto();

        var novaQuantidade = produto.getQuantidadeEstoque()
                + (movimentoEstoque.getQuantidadeMovimentada());

        produto.setQuantidadeEstoque(novaQuantidade);
        produtoRepository.save(produto);
    }

    private void saidaProduto(MovimentoEstoque movimentoEstoque){
        var produto = movimentoEstoque.getProduto();

        var novaQuantidade = produto.getQuantidadeEstoque()
                - (movimentoEstoque.getQuantidadeMovimentada());

        if(novaQuantidade < 0)
            throw new AppException("Quantidade Insuficiente",
                    "Retirada " + movimentoEstoque.getQuantidadeMovimentada()
                            + " Quantidade em Estoque "+ produto.getQuantidadeEstoque(),
                    HttpStatus.CONFLICT);

        produto.setQuantidadeEstoque(novaQuantidade);
        produtoRepository.save(produto);
    }

    @SneakyThrows
    @Transactional
    public MovimentoEstoqueDTO salvarMovimento(MovimentoEstoqueDTO movimentoEstoqueDTO){

        var movimentoEstoque = criaMovimentoEstoque(movimentoEstoqueDTO);
        if (movimentoEstoqueDTO.getTipoMovimentacao() == TipoMovimentacaoEnum.ENTRADA)
            entradaProduto(movimentoEstoque);
        else
            saidaProduto(movimentoEstoque);

        movimentoEstoqueRepository.save(movimentoEstoque);
        return movimentoEstoqueDTO;
    }

    private MovimentoEstoque criaMovimentoEstoque(MovimentoEstoqueDTO movimentoEstoqueDTO) {

        var produto = produtoService.get(movimentoEstoqueDTO.getCodigo());

        var total = movimentoEstoqueDTO.getValorUnitario()
                .multiply(BigDecimal.valueOf(movimentoEstoqueDTO.getQuantidadeMovimentada()));

        var lucro = movimentoEstoqueDTO.getValorUnitario()
                .subtract(produto.getValorFornecedor())
                .multiply(BigDecimal.valueOf(movimentoEstoqueDTO.getQuantidadeMovimentada()));

        return  MovimentoEstoque.builder()
                .id(movimentoEstoqueDTO.getId())
                .produto(produto)
                .dataMovimentacao(LocalDateTime.now())
                .quantidadeMovimentada(movimentoEstoqueDTO.getQuantidadeMovimentada())
                .tipoMovimentacao(movimentoEstoqueDTO.getTipoMovimentacao())
                .valorUnitario(movimentoEstoqueDTO.getValorUnitario())
                .total(total)
                .lucro(lucro)
                .build();
    }
}
