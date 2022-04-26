package com.example.desafio.rest;

import com.example.desafio.dto.ProdutoDTO;
import com.example.desafio.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4000", maxAge = 3600)
@RestController
@RequiredArgsConstructor
public class ProdutoAPI {
    private final ProdutoService produtoService;

    //consulta todos os produtos
    @GetMapping(value = "/api/v1/produto", produces = "application/json")
    public ResponseEntity<List<ProdutoDTO>> consultarProdutos(@PageableDefault(sort = {"codigo"}) Pageable pageable) {
        return ResponseEntity.ok(produtoService.buscarTodosProdutos(pageable));
    }

    //cadastra os produtos
    @PostMapping(value = "/api/v1/produto", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> cadastrar(@RequestBody ProdutoDTO produtoDTO) throws Exception {
        return ResponseEntity.ok(produtoService.salvarProduto(produtoDTO));
    }

    // deleta produtos por codigo
    @DeleteMapping(value = "/api/v1/produto", consumes = "application/json", produces = "application/json")
    public ResponseEntity <?> delete(@PathVariable String codigo) {
        produtoService.deleteCodigo(codigo);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
