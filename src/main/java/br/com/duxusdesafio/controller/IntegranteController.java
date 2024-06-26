package br.com.duxusdesafio.controller;

import br.com.duxusdesafio.dto.IntegranteDTO;
import br.com.duxusdesafio.service.IntegranteService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@AllArgsConstructor
@RestController
@RequestMapping("/integrante")
public class IntegranteController {

    @Autowired
    private IntegranteService integranteService;

    @GetMapping
    public ResponseEntity<List<IntegranteDTO>> listarTodosTimes() {
        List<IntegranteDTO> integrantes = integranteService.listarTodosIntegrantes();
        return ResponseEntity.ok(integrantes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IntegranteDTO> buscarIntegrantePorId(@PathVariable Long id) {
        IntegranteDTO integranteDTO = integranteService.buscarIntegrantePorId(id);
        if (integranteDTO != null) {
            return ResponseEntity.ok(integranteDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<IntegranteDTO> atualizarIntegrante(@PathVariable Long id, @RequestBody IntegranteDTO integranteDTO) {
        IntegranteDTO integranteAtualizado = integranteService.atualizarIntegrante(id, integranteDTO);
        if (integranteAtualizado != null) {
            return ResponseEntity.ok(integranteAtualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarIntegrante(@PathVariable Long id) {
        integranteService.deletarIntegrante(id);
        return ResponseEntity.noContent().build();
    }

    // Criação API REST
    @PostMapping
    public ResponseEntity<Object> createIntegrante(@RequestBody IntegranteDTO integranteDTO) {
        try {
            IntegranteDTO novoIntegrante = integranteService.criarIntegrante(integranteDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoIntegrante);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existe. Verifique os dados fornecidos."); // Ou outra mensagem de erro
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Ocorreu um erro inesperado. Detalhes: " + e.getMessage());
    }
}