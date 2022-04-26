package com.example.desafio.rest;

import com.example.desafio.domain.model.TipoProdutoEnum;
import com.example.desafio.dto.MovimentoEstoqueDTO;
import com.example.desafio.service.MovimentoEstoqueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:4000", maxAge = 3600)
@RestController
@RequiredArgsConstructor
public class MovimentoEstoqueAPI {
    private final MovimentoEstoqueService movimentoEstoqueService;

    //realiza o movimento de estoque, entrada e saida.
    @PostMapping(value = "/api/v1/movimento", consumes = "application/json", produces = "application/json")
    public ResponseEntity<MovimentoEstoqueDTO> vendaCompra(@RequestBody @Valid MovimentoEstoqueDTO movimentoEstoqueDTO){
        return ResponseEntity.ok(movimentoEstoqueService.salvarMovimento(movimentoEstoqueDTO));
    }

    //retorna a quantidade de saida e quantidade em estoque de cada produto por Tipo de produto
    @GetMapping(value ="/api/v1/produto/estoque/{tipoProduto}", produces = "application/json")
    public ResponseEntity<?> buscarTipoProduto(@PathVariable TipoProdutoEnum tipoProduto){
        return ResponseEntity.ok(movimentoEstoqueService.quantidadeVendidaTipo(tipoProduto));
    }

    //retorna quantidade vendida por codigo de produto, e o lucro obtido
    @GetMapping(value ="/api/v1/produto/estoque/vendas/{codigo}", produces = "application/json")
    public ResponseEntity<?> totalVendas(@PathVariable String codigo){
        return ResponseEntity.ok(movimentoEstoqueService.totalVendaProduto(codigo));
    }
}
