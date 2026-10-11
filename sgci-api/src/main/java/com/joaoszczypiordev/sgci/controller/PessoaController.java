package com.joaoszczypiordev.sgci.controller;

import com.joaoszczypiordev.sgci.dtos.pessoa.PessoaResponse;
import com.joaoszczypiordev.sgci.dtos.pessoa.PessoaSaveDto;
import com.joaoszczypiordev.sgci.model.Pessoa;
import com.joaoszczypiordev.sgci.services.PessoaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pessoas")
@RequiredArgsConstructor
public class PessoaController {

    private final PessoaService pessoaService;

    @PostMapping("/create")
    public ResponseEntity<Long> createPessoa(@Valid @RequestBody PessoaSaveDto pessoaSaveDto) {
        Long id = pessoaService.createPessoa(pessoaSaveDto);
        return ResponseEntity.ok(id);
    }


    @DeleteMapping(path = "{idPessoa}")
    public ResponseEntity<Void> deletePessoa(@PathVariable Long idPessoa) {
        pessoaService.deletePessoa(idPessoa);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<PessoaResponse>> findAll () {
        return ResponseEntity.ok(pessoaService.findAll());
    }

}
